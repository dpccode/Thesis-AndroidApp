package com.example.mygym21;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

public class LauncherActivity extends Activity {

    Boolean login_status = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCustomersData();
        if (login_status) {
            Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
            finish();
            startActivity(intent);
        } else {
            Intent intent = new Intent(LauncherActivity.this, LoginActivity.class);
            finish();
            startActivity(intent);
        }
    }

    private void getCustomersData(){
        SharedPreferences preferences = getApplication().getSharedPreferences("customers_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        login_status = preferences.getBoolean("login_status",false);
    }

}