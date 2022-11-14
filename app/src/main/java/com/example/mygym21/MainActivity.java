package com.example.mygym21;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mygym21.ui.Personal.PersonalFragment;
import com.example.mygym21.ui.dashboard.DashboardFragment;
import com.example.mygym21.ui.home.HomeFragment;
import com.example.mygym21.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItem;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    Fragment fragment1 = new HomeFragment();
    Fragment fragment2 = new DashboardFragment();
    Fragment fragment3 = new NotificationsFragment();
    Fragment fragment4 = new PersonalFragment();
    FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;
    private String name,last_name,theme;
    private int customer_id;
    boolean destroyd;
    Bundle outState;

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dots_menu, menu);

        //gia na fenontai ta eikonidia sto menu
        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.log_out) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getCustomersData();
        getThemePref();

        if(theme.equals("Light"))
            setTheme(R.style.AppTheme);
        else
            setTheme(R.style.DarkTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(name+" "+last_name);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState != null) {

            fragment1 = fm.getFragment(savedInstanceState, "HomeFragment");
            fragment2 = fm.getFragment(savedInstanceState, "DashboardFragment");
            fragment3 = fm.getFragment(savedInstanceState, "Notificationsfragment");
            fragment4 = fm.getFragment(savedInstanceState, "Personalfragment");

        }


        fm.beginTransaction().add(R.id.container, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.container, fragment1, "1").commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fm.beginTransaction().hide(active).show(fragment1).commit();
                    getSupportActionBar().setTitle(name+" "+last_name);

                    active = fragment1;
                    return true;

                case R.id.navigation_dashboard:
                    fm.beginTransaction().hide(active).show(fragment2).commit();

                    getSupportActionBar().setTitle("Groups");
                    active = fragment2;
                    return true;

                case R.id.navigation_notifications:
                    fm.beginTransaction().hide(active).show(fragment3).commit();

                    getSupportActionBar().setTitle("Home Workouts");
                    active = fragment3;
                    return true;
                case R.id.navigation_personal:
                    fm.beginTransaction().hide(active).show(fragment4).commit();

                    getSupportActionBar().setTitle("Personal");
                    active = fragment4;
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        fm.putFragment(outState,"HomeFragment",fragment1);
        fm.putFragment(outState,"DashboardFragment",fragment2);
        fm.putFragment(outState,"Notificationsfragment",fragment3);
        fm.putFragment(outState,"PersonalFragment",fragment4);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {

            fragment1 = fm.getFragment(savedInstanceState, "HomeFragment");
            fragment2 = fm.getFragment(savedInstanceState, "DashboardFragment");
            fragment3 = fm.getFragment(savedInstanceState, "Notificationsfragment");
            fragment4 = fm.getFragment(savedInstanceState, "Personalfragment");

        }
    }


    private void getCustomersData(){

        SharedPreferences preferences = getApplication().getSharedPreferences("customers_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        name = preferences.getString("name","");
        last_name = preferences.getString("last_name","");
        customer_id = preferences.getInt("customer_id",0);

    }

    public  void getThemePref(){
            SharedPreferences preferences = getApplication().getSharedPreferences(String.valueOf(customer_id)+"themePref", Context.MODE_PRIVATE);
            theme = preferences.getString("theme","Light");
    }


    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
