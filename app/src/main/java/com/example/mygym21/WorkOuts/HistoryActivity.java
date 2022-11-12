package com.example.mygym21.WorkOuts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mygym21.R;

import java.util.ArrayList;
import java.util.Collections;

public class HistoryActivity extends AppCompatActivity {

    LinearLayout layoutList;
    private boolean workOutFinished = false;
    Button removeAll;
    private int customer_id;
    private String theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getCustomersData();
        getThemePref();
        if(theme.equals("Light"))
            setTheme(R.style.AppTheme);
        else
            setTheme(R.style.DarkTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setTitle("History");
        layoutList = findViewById(R.id.history_resView);
        removeAll = findViewById(R.id.history_removeAll_Btn);
        getCustomersData();



        SharedPreferences preferences = getApplication().getSharedPreferences(String.valueOf(customer_id)+"history", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        removeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(HistoryActivity.this).create();
                dialog.setTitle("Are you sure?");
                dialog.setMessage("Do you want to clear your history");

                dialog.setButton(Dialog.BUTTON_POSITIVE,"Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.clear().commit();
                        layoutList.removeAllViews();
                    }
                });
                dialog.setButton(Dialog.BUTTON_NEGATIVE,"No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.red));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.teal));
            }
        });

        int total_workOuts_finished = preferences.getInt("total_workOuts_finished",0);
        //Toast.makeText(getApplicationContext(),"to totalworkouts  einai "+total_workOuts_finished,Toast.LENGTH_LONG).show();
        for(int i = 1; i <= total_workOuts_finished ;i++){
            final View resView = getLayoutInflater().inflate(R.layout.activity_history_item, null, false);
            TextView title = resView.findViewById(R.id.history_workout_title);
            TextView date = resView.findViewById(R.id.history_workout_date);
            ImageButton delete = resView.findViewById(R.id.delete_item_from_history);
            //title.setText(String.valueOf(i));
            title.setText(preferences.getString("workOut_title"+String.valueOf(i)," "));
            date.setText(preferences.getString("workOut_date"+String.valueOf(i)," "));
            final int finalI = i;
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layoutList.removeView(resView);
                    editor.remove("workOut_title"+String.valueOf(finalI)).commit();
                    editor.remove("workOut_date"+String.valueOf(finalI)).commit();
                    editor.putBoolean("workOut_deleted_from_histroy"+String.valueOf(finalI),true).commit();
                }
            });

            //date.setText("24 aprili");
            if(preferences.getBoolean("workOut_deleted_from_histroy"+String.valueOf(finalI),false) != true){
                layoutList.addView(resView,0);
                //Collections.reverse(layoutList); // ADD THIS LINE TO REVERSE ORDER!
                //layoutList.notifyDataSetChanged;
                //layoutList.

            }


        }



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