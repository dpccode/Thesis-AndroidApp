package com.example.mygym21.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mygym21.LoginActivity;
import com.example.mygym21.MainActivity;
import com.example.mygym21.R;
import com.example.mygym21.Settings.SettingsActivity;
import com.example.mygym21.WorkOuts.HistoryActivity;
import com.example.mygym21.caloriescalc.CaloriesCalculatorActivity;
import com.example.mygym21.contactus.EmailSender;
import com.example.mygym21.notifications.NotificationsActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class HomeFragment extends Fragment {

    ImageView mImageview;
    SharedPreferences preferences = null;
    SharedPreferences.Editor editor;
    private HomeViewModel HomeViewModel;
    private Button contactUs;
    private Button notifications;
    private Button caloriesCalculator;
    private Button history;
    private Button settings;
    private int customer_id;
    private String gender,subscription_start,subscription_end;
    private int subscription_status;
    private TextView subStatus,subStart,subEnd;
    private CardView subStatusCardView;



    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.refresh).setVisible(false);
        menu.findItem(R.id.log_out).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                SharedPreferences preferences = getActivity().getSharedPreferences("customers_data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("login_status",false).commit();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().finish();
                startActivity(intent);
                return false;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out:
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        HomeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        setHasOptionsMenu(true);
        mImageview = root.findViewById(R.id.picture);
        contactUs = root.findViewById(R.id.startContactUsActivity);
        notifications = root.findViewById(R.id.startNotificationsActivity);
        caloriesCalculator = root.findViewById(R.id.startCaloriesCalculatorActivity);
        history = root.findViewById(R.id.startHistoryActivity);
        settings = root.findViewById(R.id.startSettingsActivity);
        subStatus = root.findViewById(R.id.subStatus);
        subStart = root.findViewById(R.id.subStart);
        subEnd = root.findViewById(R.id.subEnd);

        //buttonLoadPicture = root.findViewById(R.id.buttonLoadPicture);

        getCustomersData();
        subStatus.setText("Subscription: "+subscription_status+" months");
        if(subscription_status != 0){
            subscription_start = formateDateFromstring("yyyy-MM-dd", "dd/MM/yyyy", subscription_start);
            subStart.setText("Subscription start: "+subscription_start);
            subscription_end = formateDateFromstring("yyyy-MM-dd", "dd/MM/yyyy", subscription_end);
            subEnd.setText("Subscription end: "+subscription_end);
        }else{
            subStatusCardView = root.findViewById(R.id.subStatusCardView);
            subStatusCardView.setCardBackgroundColor(Color.RED);
            subStart.setText("Subscription start: "+"-");
            subscription_end = formateDateFromstring("yyyy-MM-dd", "dd/MM/yyyy", subscription_end);
            subEnd.setText("Subscription end: "+"-");
        }

        init();


            if (preferences.getString(String.valueOf(customer_id)+"image", null) != null) {

                String imageUriString = preferences.getString(String.valueOf(customer_id)+"image", null);
                Uri myUri = Uri.parse(imageUriString);

                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.add_user1)
                        .error(R.drawable.add_user1);

                Glide.with(this).load(myUri).apply(options).into(mImageview);
            } else {
                if (gender.equals("Male"))
                    Glide.with(this).load(R.drawable.user_male).into(mImageview);
                else
                    Glide.with(this).load(R.drawable.user_female).into(mImageview);

            }



        root.findViewById(R.id.picture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE);

                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    startGallery();
                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            2000);
                }
            }
        });



        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EmailSender.class);
                startActivity(intent);
            }
        });

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NotificationsActivity.class);
                startActivity(intent);
            }
        });

        caloriesCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CaloriesCalculatorActivity.class);
                startActivity(intent);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HistoryActivity.class);
                startActivity(intent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    private void startGallery() {
        Intent cameraIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        cameraIntent.setType("image/*");
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, 1000);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1000) {
                Uri returnUri = data.getData();

                editor.putString(String.valueOf(customer_id)+"image", returnUri.toString());
                editor.commit();
                editor.apply();

                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.add_user1)
                        .error(R.drawable.add_user1);
                Glide.with(this).load(returnUri).apply(options).into(mImageview);

            }
        }
    }



    private void getCustomersData(){
        SharedPreferences preferences = getActivity().getSharedPreferences("customers_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        customer_id = preferences.getInt("customer_id",0);
        gender = preferences.getString("gender","Male");
        subscription_status = preferences.getInt("subscription_status",0);
        subscription_start = preferences.getString("subscription_start","-");
        subscription_end =preferences.getString("subscription_end","-");
    }

    private void init(){
        preferences = getActivity().getSharedPreferences(String.valueOf(customer_id)+"image",Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate){

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {

        }

        return outputDate;

    }

}