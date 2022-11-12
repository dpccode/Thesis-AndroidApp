package com.example.mygym21;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mygym21.WorkOuts.ExerciseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private String email,pass,name,lastName,phone,mobile_phone,gender,subscription_start,subscription_end,subscription_type;
    public int customers_id,trainers_id,subscription_status;
    private EditText emailEditText,passwordEditText;
    private Button loginBtn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");


        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String pass = passwordEditText.getText().toString();
                getCustomersData(email,pass);
            }
        });


        //setContentView(R.layout.activity_login);
    }


    public void getCustomersData(final String mEmail, final String mPass){
        RequestQueue rq;
        rq = Volley.newRequestQueue(LoginActivity.this);
        String url = "http://192.168.1.5:80/api/api/customer/login.php?email="+mEmail+"&password="+mPass;




        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if(!response.contains("Login failed...!")){
                    saveCustomersData(response);
                    Intent intent =  new Intent(LoginActivity.this,MainActivity.class);
                    finish();
                    startActivity(intent);
                }else{

                   AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this).create();
                    dialog.setTitle("Login failed!");
                    dialog.setMessage("Wrong email or password...");
                    dialog.setButton(Dialog.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.teal));


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this).create();
                dialog.setTitle("Login failed!");
                dialog.setMessage("Check your internet connection");
                dialog.setButton(Dialog.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.teal));
            }
        });


        rq.add(stringRequest);

    }

    private void saveCustomersData(String res){
        SharedPreferences preferences = getApplication().getSharedPreferences("customers_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();


        try {
            editor.clear().commit();
            JSONArray jsonArray= new JSONArray(res);
            JSONObject jsonObject = new JSONObject();
            jsonObject = jsonArray.getJSONObject(0);
            editor.clear();
            editor.putInt("customer_id",jsonObject.getInt("id")).commit();
            editor.putString("email",jsonObject.getString("email")).commit();
            editor.putString("name",jsonObject.getString("name")).commit();
            editor.putString("last_name",jsonObject.getString("last_name")).commit();
            editor.putInt("trainers_id",jsonObject.getInt("trainers_id")).commit();
            editor.putInt("subscription_status",jsonObject.getInt("subscription_status")).commit();
            editor.putString("gender",jsonObject.getString("gender")).commit();
            editor.putString("subscription_start",jsonObject.getString("subscription_start")).commit();
            editor.putString("subscription_end",jsonObject.getString("subscription_end")).commit();
            editor.putString("subscription_type",jsonObject.getString("subscription_type")).commit();
            editor.putString("trainers_email",jsonObject.getString("trainers_email")).commit();
            editor.putBoolean("login_status",true).commit();
            editor.apply();

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}