package com.example.mygym21.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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

public class ChangeEmailActivity extends AppCompatActivity {

    private EditText mNewEmail,mPass;
    private Button mSubmitBtn;
    private String email,newEmail,password,theme;
    private  int customer_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getCustomersData();
        getThemePref();
        if(theme.equals("Light"))
            setTheme(R.style.AppTheme);
        else
            setTheme(R.style.DarkTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);
        getSupportActionBar().setTitle("Change Email");

        mSubmitBtn = findViewById(R.id.change_email_submitBtn);
        mNewEmail = findViewById(R.id.newEmail);
        mPass = findViewById(R.id.change_email_pass);
        mPass.getBackground().setColorFilter(getResources().getColor(R.color.black),
                PorterDuff.Mode.SRC_ATOP);
        mNewEmail.getBackground().setColorFilter(getResources().getColor(R.color.black),
                PorterDuff.Mode.SRC_ATOP);


        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newEmail = mNewEmail.getText().toString();
                password = mPass.getText().toString();
                changeEmailReq();
            }
        });





    }


    public void changeEmailReq(){
        RequestQueue rq;
        rq = Volley.newRequestQueue(ChangeEmailActivity.this);
        String url = "http://192.168.1.5:80/api/api/customer/email/update.php";



        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("Success")) {
                    mPass.getBackground().setColorFilter(getResources().getColor(R.color.black),
                            PorterDuff.Mode.SRC_ATOP);
                    mNewEmail.getBackground().setColorFilter(getResources().getColor(R.color.black),
                            PorterDuff.Mode.SRC_ATOP);
                    AlertDialog dialog = new AlertDialog.Builder(ChangeEmailActivity.this).create();
                    dialog.setTitle("Email Changed to");
                    dialog.setMessage(newEmail);
                    dialog.setButton(Dialog.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    //dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.teal));
                    //Toast.makeText(ChangeEmailActivity.this, "Email Changed!!\nNew email address "+newEmail, Toast.LENGTH_LONG).show();
                }
                else{
                    mPass.getBackground().setColorFilter(getResources().getColor(R.color.red),
                            PorterDuff.Mode.SRC_ATOP);
                    mNewEmail.getBackground().setColorFilter(getResources().getColor(R.color.red),
                            PorterDuff.Mode.SRC_ATOP);
                    AlertDialog dialog = new AlertDialog.Builder(ChangeEmailActivity.this).create();
                    dialog.setTitle("Wrong Password or Email already used");
                    dialog.setButton(Dialog.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    //Toast.makeText(ChangeEmailActivity.this,"Wrong Password or Email already used",Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(ChangeEmailActivity.this,"eimai sto error"+error,Toast.LENGTH_LONG).show();

                AlertDialog dialog = new AlertDialog.Builder(ChangeEmailActivity.this).create();
                dialog.setTitle("Requests status");
                dialog.setMessage("Something went wrong!\nCheck your internet connection and try again later!");
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.teal));
                //Toast.makeText(getContext(),"No internet connection",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("customers_id",String.valueOf(customer_id));
                params.put("email",email);
                params.put("pass",password);
                params.put("new_email",newEmail);
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