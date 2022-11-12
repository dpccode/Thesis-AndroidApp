package com.example.mygym21.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mygym21.MainActivity;
import com.example.mygym21.R;

import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {
    private int customer_id;
    private String theme;
    private Button changeTheme,changeEmail,changePassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getCustomersData();
        getThemePref();
        if(theme.equals("Light"))
            setTheme(R.style.AppTheme);
        else
            setTheme(R.style.DarkTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setTitle("Settings");
        changeTheme = findViewById(R.id.changeTheme);
        changePassword = findViewById(R.id.changePassword);
        changeEmail = findViewById(R.id.changeEmail);


        getCustomersData();
        final SharedPreferences preferences = getApplication().getSharedPreferences(String.valueOf(customer_id)+"themePref", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        if(preferences.getString("theme","Light").equals("Light")){
            changeTheme.setText("Change theme to Dark");
        } else{
            changeTheme.setText("Change theme to Light");
        }
        changeTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(preferences.getString("theme","Light").equals("Light")){
                    editor.putString("theme","Dark").commit();
                } else{
                    editor.putString("theme","Light").commit();
                }

                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ChangePassActivity.class);
                startActivity(intent);
            }
        });

        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ChangeEmailActivity.class);
                startActivity(intent);
            }
        });

    }




    private void getCustomersData(){
        SharedPreferences preferences = getApplication().getSharedPreferences("customers_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        customer_id = preferences.getInt("customer_id",0);
    }

    public  void getThemePref(){
        SharedPreferences preferences = getApplication().getSharedPreferences(String.valueOf(customer_id)+"themePref", Context.MODE_PRIVATE);
        theme = preferences.getString("theme","Light");
    }





}