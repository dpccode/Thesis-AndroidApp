package com.example.mygym21.contactus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mygym21.R;

public class EmailSender extends AppCompatActivity {
    private String emailAddressTrainer;
    private String emailAddressFitnessCenter = "dchristodoulopou@isc.tuc.gr";
    private String emailToAddress,theme;
    private int customer_id;
    private Button sentEmailToTrainer;
    private Button sentEmailToFitnessCenter;



    private String sEmail;
    private String sPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getCustomersData();
        getThemePref();
        if(theme.equals("Light"))
            setTheme(R.style.AppTheme);
        else
            setTheme(R.style.DarkTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_activity);
        getSupportActionBar().setTitle("Contact us");
        sentEmailToTrainer = findViewById(R.id.sendEmailToTrainer);
        sentEmailToFitnessCenter = findViewById(R.id.sendEmailToFitnessCenter);


        sentEmailToTrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailToAddress = emailAddressTrainer;
                sendEmail(emailToAddress);
            }
        });

        sentEmailToFitnessCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailToAddress = emailAddressFitnessCenter;
                sendEmail(emailToAddress);
            }
        });


    }

    private void sendEmail(String emailToAddress) {

        Intent intent= new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL,emailToAddress.split(","));

        startActivity(Intent.createChooser(intent,"Choose an email client"));

    }


    private void getCustomersData(){
        SharedPreferences preferences = getApplication().getSharedPreferences("customers_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        customer_id = preferences.getInt("customer_id",0);
        emailAddressTrainer = preferences.getString("trainers_email","");
    }

    public  void getThemePref(){
        SharedPreferences preferences = getApplication().getSharedPreferences(String.valueOf(customer_id)+"themePref", Context.MODE_PRIVATE);
        theme = preferences.getString("theme","Light");
    }

}