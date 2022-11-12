package com.example.mygym21.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import com.example.mygym21.R;

import java.util.HashMap;
import java.util.Map;

public class ChangePassActivity extends AppCompatActivity {

    private EditText mOldPass,mNewPass,mNewPassVer;
    private String oldPass,newPass,newPassVer,email,theme;
    private int customer_id;
    private Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getCustomersData();
        getThemePref();
        if(theme.equals("Light"))
            setTheme(R.style.AppTheme);
        else
            setTheme(R.style.DarkTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        getSupportActionBar().setTitle("Change Password");
        submit = findViewById(R.id.change_pass_submitBtn);
        mOldPass = findViewById(R.id.oldPass);
        mNewPass = findViewById(R.id.newPass);
        mNewPassVer = findViewById(R.id.newPassVerification);
        mOldPass.getBackground().setColorFilter(getResources().getColor(R.color.black),
                PorterDuff.Mode.SRC_ATOP);
        mNewPass.getBackground().setColorFilter(getResources().getColor(R.color.black),
                PorterDuff.Mode.SRC_ATOP);
        mNewPassVer.getBackground().setColorFilter(getResources().getColor(R.color.black),
                PorterDuff.Mode.SRC_ATOP);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPass = mOldPass.getText().toString();
                newPass = mNewPass.getText().toString();
                newPassVer = mNewPassVer.getText().toString();
                if(newPass.equals(newPassVer)){
                    changePassReq();
                }else{
                    mNewPass.getBackground().setColorFilter(getResources().getColor(R.color.red),
                            PorterDuff.Mode.SRC_ATOP);
                    mNewPassVer.getBackground().setColorFilter(getResources().getColor(R.color.red),
                            PorterDuff.Mode.SRC_ATOP);
                    Toast.makeText(ChangePassActivity.this,"Passwords does not match",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void changePassReq(){
        RequestQueue rq;
        rq = Volley.newRequestQueue(ChangePassActivity.this);
        String url = "http://192.168.1.5:80/api/api/customer/password/update.php";



        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("Success")) {
                    mOldPass.getBackground().setColorFilter(getResources().getColor(R.color.black),
                            PorterDuff.Mode.SRC_ATOP);
                    mNewPass.getBackground().setColorFilter(getResources().getColor(R.color.black),
                            PorterDuff.Mode.SRC_ATOP);
                    mNewPassVer.getBackground().setColorFilter(getResources().getColor(R.color.black),
                            PorterDuff.Mode.SRC_ATOP);
                    Toast.makeText(ChangePassActivity.this, "Password Changed!!", Toast.LENGTH_LONG).show();
                }
                else{
                    mOldPass.getBackground().setColorFilter(getResources().getColor(R.color.red),
                            PorterDuff.Mode.SRC_ATOP);
                    Toast.makeText(ChangePassActivity.this,"Wrong Password!!",Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChangePassActivity.this,"eimai sto error"+error,Toast.LENGTH_LONG).show();

                AlertDialog dialog = new AlertDialog.Builder(ChangePassActivity.this).create();
                dialog.setTitle("Requests status");
                dialog.setMessage("Something went wrong!\nCheck your internet connection and try again later!");
                dialog.show();
                //Toast.makeText(getContext(),"No internet connection",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("customers_id",String.valueOf(customer_id));
                params.put("email",email);
                params.put("old_pass",oldPass);
                params.put("new_pass",newPass);
                return params;
            }

        };

        rq.add(stringRequest);
    }


    private void getCustomersData(){
        SharedPreferences preferences = getApplication().getSharedPreferences("customers_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        customer_id = preferences.getInt("customer_id",0);
        email = preferences.getString("email","");
        //Toast.makeText(getActivity(),"to customers_id "+customer_id,Toast.LENGTH_SHORT).show();
    }


    public  void getThemePref(){
        SharedPreferences preferences = getApplication().getSharedPreferences(String.valueOf(customer_id)+"themePref", Context.MODE_PRIVATE);
        theme = preferences.getString("theme","Light");
    }

}