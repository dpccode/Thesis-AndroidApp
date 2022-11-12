package com.example.mygym21.notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mygym21.R;

import java.util.Calendar;

public class NotificationsActivity extends AppCompatActivity {

    //public int mYear;
    //public int mMonth;
    //public int mDay;
    private PendingIntent pendingIntent;
    //private long notificationTime;
    private long workOutNotTime;
    private long preWorkOutNotTime;
    private long lastPreWorkOutMealNotTime;
    private long postWorkOutNotTime;
    private String workOutCheckBoxStatus;
    private String preWorkOutCheckBoxStatus;
    private String lastPreWorkOutMealCheckBoxStatus;
    private String postWorkOutCheckBoxStatus;
    private  String everyDayNotificationsSwitchStatus,theme;
    private int customer_id;


    SharedPreferences preferences;
    SharedPreferences.Editor editor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getCustomersData();
        getThemePref();
        if(theme.equals("Light"))
            setTheme(R.style.AppTheme);
        else
            setTheme(R.style.DarkTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_avtivity);
        getSupportActionBar().setTitle("Notifications");

        //Calendar notificationCalendar = Calendar.getInstance();

        final Calendar calendar2 = Calendar.getInstance();


        Button mConfirmButton = findViewById(R.id.confirm_button);
        Button mDateButton = findViewById(R.id.date_button);
        Button mTimeButton = findViewById(R.id.time_button);
        final CheckBox workOutCheckBox = findViewById(R.id.checkBox1);
        final CheckBox preWorkOutCheckBox = findViewById(R.id.checkBox2);
        final CheckBox lastPreWorkOutMealCheckBox = findViewById(R.id.checkBox3);
        final CheckBox postWorkOutCheckBox = findViewById(R.id.checkBox4);
        final Switch everyDayNotificationsSwitch = findViewById(R.id.everyDayNotificationsSwitch);
        final CardView setDateCardView = findViewById(R.id.setDateCardView);
        CardView setTimeCardview = findViewById(R.id.setTimeCardView);
        CardView confirmCardView = findViewById(R.id.confirmCardView);

        /*
        kanonika eprepe na ta kanw me saveinstance state kai na swzw ta status sthn onDestroy()
        an exw xrono na to ftiaksw
         */

        init();

        workOutCheckBoxStatus = preferences.getString("workOutCheckBoxStatus",null);
        preWorkOutCheckBoxStatus = preferences.getString("preWorkOutCheckBoxStatus",null);
        lastPreWorkOutMealCheckBoxStatus = preferences.getString("lastPreWorkOutCheckBoxStatus",null);
        postWorkOutCheckBoxStatus = preferences.getString("postWorkOutCheckBoxStatus",null);
        everyDayNotificationsSwitchStatus = preferences.getString("everyDayNotificationsSwitchStatus",null);



        if(workOutCheckBoxStatus != null) {

            if (workOutCheckBoxStatus.equals("true"))
                workOutCheckBox.setChecked(true);
            else
                workOutCheckBox.setChecked(false);
        }

        if(preWorkOutCheckBoxStatus != null) {
            if (preWorkOutCheckBoxStatus.equals("true"))
                preWorkOutCheckBox.setChecked(true);
            else
                preWorkOutCheckBox.setChecked(false);
        }

        if(lastPreWorkOutMealCheckBoxStatus != null) {
            if (lastPreWorkOutMealCheckBoxStatus.equals("true"))
                lastPreWorkOutMealCheckBox.setChecked(true);
            else
                lastPreWorkOutMealCheckBox.setChecked(false);
        }

        if(postWorkOutCheckBoxStatus != null) {
            if (postWorkOutCheckBoxStatus.equals("true"))
                postWorkOutCheckBox.setChecked(true);
            else
                postWorkOutCheckBox.setChecked(false);
        }

        if(everyDayNotificationsSwitchStatus != null) {
            if (everyDayNotificationsSwitchStatus.equals("true"))
                everyDayNotificationsSwitch.setChecked(true);
            else
                everyDayNotificationsSwitch.setChecked(false);
        }


        everyDayNotificationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true) {
                    //Toast.makeText(getApplicationContext(), "eimai edw swsta1", Toast.LENGTH_SHORT).show();
                    editor.putString("everyDayNotificationsSwitchStatus","true");
                    //sampleUi.set(everyDayNotificationsSwitchStatusPosition,"true");
                    everyDayNotificationsSwitch.setText("Send me notifications every day");
                    setDateCardView.setVisibility(View.GONE);
                }
                else{
                    //sampleUi.set(everyDayNotificationsSwitchStatusPosition,"false");
                    editor.putString("everyDayNotificationsSwitchStatus","false");
                    everyDayNotificationsSwitch.setText("Send me notifications one time");
                    setDateCardView.setVisibility(View.VISIBLE);
                }
            }
        });


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("my not","my not", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }



        final Calendar newDate = Calendar.getInstance();
        final Calendar c = Calendar.getInstance();

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, notificationCalendar.get(Calendar.HOUR_OF_DAY)+" "+notificationCalendar.get(Calendar.MINUTE),Toast.LENGTH_LONG).show();

                final int year = c.get(Calendar.YEAR);
                final int month = c.get(Calendar.MONTH);
                final int day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePicker =
                        new DatePickerDialog(NotificationsActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(final DatePicker view, final int year, final int month,
                                                  final int dayOfMonth) {


                                newDate.set(year, month, dayOfMonth);
                                c.set(year, month, dayOfMonth);

                            }
                        }, year, month, day); // set date picker to current date

                datePicker.show();


                datePicker.getDatePicker().init(newDate.get(Calendar.YEAR), newDate.get(Calendar.MONTH), newDate.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int i, int i2, int i3) {
                        newDate.set(i, i2, i3);
                    }

                });


                datePicker.getDatePicker().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });





                datePicker.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(final DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });



            }

        });

        //Calendar newTime = Calendar.getInstance();

        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
                final int minute = c.get(Calendar.MINUTE);


                TimePickerDialog timePickerDialog = new TimePickerDialog(NotificationsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        c.set(Calendar.MINUTE,minute);

                        newDate.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        newDate.set(Calendar.MINUTE,minute);

                        //view.setOnTimeChangedListener((TimePicker.OnTimeChangedListener) view);
                    }



                },hourOfDay,minute,true);

                timePickerDialog.show();
                //timePickerDialog.dismiss();



                //timePickerDialog.ge

                TimePicker timePicker = new TimePicker(NotificationsActivity.this);
                timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                        newDate.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        newDate.set(Calendar.MINUTE,minute);
                    }
                });
                //.onTimeChanged(timePickerDialogsz,hourOfDay,minute);

                //timePickerDialog.setContentView(timePicker);
                timePickerDialog.setView(timePicker);

                timePickerDialog.updateTime(newDate.get(Calendar.HOUR_OF_DAY),newDate.get(Calendar.MINUTE));

                timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });
            }
        });



        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar rightNow = Calendar.getInstance();

                Toast.makeText(NotificationsActivity.this,"Changes Confirmed",Toast.LENGTH_SHORT).show();

                //int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);
                //int currentMinute = rightNow.get(Calendar.MINUTE);
                long currentTime = rightNow.getTimeInMillis();

                Intent intent = new Intent(getApplicationContext(), Notifications_receiver.class);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                if(workOutCheckBox.isChecked()){
                    //sampleUi.set(workOutCheckBoxStatusPosition,"true");
                    editor.putString("workOutCheckBoxStatus","true").commit();
                    //editor.commit();
                    //editor.apply();

                    Calendar workOutCalendar =  Calendar.getInstance();
                    intent.putExtra("Notification Content","WorkOutTime");
                    intent.putExtra("Notification title","Its Work out time!!!");

                    workOutCalendar.setTime(newDate.getTime());

                    workOutNotTime = workOutCalendar.getTimeInMillis();
                    pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    if(workOutNotTime > currentTime){
                        if(everyDayNotificationsSwitch.isChecked()){
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,workOutNotTime,AlarmManager.INTERVAL_DAY, pendingIntent);
                            //Toast.makeText(getApplicationContext(),"eimai sto every day",Toast.LENGTH_LONG).show();
                        } else{
                            //Toast.makeText(getApplicationContext(),"eimai edw",Toast.LENGTH_LONG).show();
                            alarmManager.set(AlarmManager.RTC_WAKEUP, workOutNotTime, pendingIntent);
                        }

                    }
                }
                else{
                    //sampleUi.set(workOutCheckBoxStatusPosition,"false");
                    editor.putString("workOutCheckBoxStatus","false").commit();
                    //editor.commit();
                    //editor.apply();
                }

                if(preWorkOutCheckBox.isChecked()){
                    ///sampleUi.set(preWorkOutCheckBoxStatusPosition,"true");
                    editor.putString("preWorkOutCheckBoxStatus","true").commit();
                    //editor2.commit();
                    //editor2.apply();

                    Calendar preWorkOutCalendar =  Calendar.getInstance();
                    intent.putExtra("Notification Content","PreWorkOut");
                    intent.putExtra("Notification title","Its time for your pre work out");

                    preWorkOutCalendar.setTime(newDate.getTime());
                    preWorkOutCalendar.set(Calendar.HOUR_OF_DAY,preWorkOutCalendar.get(Calendar.HOUR_OF_DAY) - 1);

                    preWorkOutNotTime = preWorkOutCalendar.getTimeInMillis();
                    pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 101, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    if(preWorkOutNotTime > currentTime){
                        if(everyDayNotificationsSwitch.isChecked()){
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,preWorkOutNotTime,AlarmManager.INTERVAL_DAY, pendingIntent);
                        } else{
                            alarmManager.set(AlarmManager.RTC_WAKEUP, preWorkOutNotTime, pendingIntent);
                        }
                    }
                }
                else{
                    //sampleUi.set(preWorkOutCheckBoxStatusPosition,"false");
                    editor.putString("preWorkOutCheckBoxStatus","false").commit();
                    //editor2.commit();
                    //editor2.apply();
                }

                if(lastPreWorkOutMealCheckBox.isChecked()){
                    //sampleUi.set(lastPreWorkOutMealCheckBoxStatusPosition,"true");
                    editor.putString("lastPreWorkOutCheckBoxStatus","true").commit();
                    //editor3.commit();
                    //editor3.apply();

                    Calendar lastPreWorkOutMealCalendar =  Calendar.getInstance();
                    intent.putExtra("Notification Content","LastPreWorkOutMeal");
                    intent.putExtra("Notification title","Its time for your last meal");

                    lastPreWorkOutMealCalendar.setTime(newDate.getTime());
                    lastPreWorkOutMealCalendar.set(Calendar.HOUR_OF_DAY,lastPreWorkOutMealCalendar.get(Calendar.HOUR_OF_DAY) - 3);

                    lastPreWorkOutMealNotTime = lastPreWorkOutMealCalendar.getTimeInMillis();
                    pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 102, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    if(lastPreWorkOutMealNotTime > currentTime){
                        if(everyDayNotificationsSwitch.isChecked()){
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,lastPreWorkOutMealNotTime,AlarmManager.INTERVAL_DAY, pendingIntent);
                        } else{
                            alarmManager.set(AlarmManager.RTC_WAKEUP, lastPreWorkOutMealNotTime, pendingIntent);
                        }
                    }
                }else {
                    //sampleUi.set(lastPreWorkOutMealCheckBoxStatusPosition,"false");
                    editor.putString("lastPreWorkOutCheckBoxStatus","false").commit();
                    //editor3.commit();
                    //editor.apply();
                }

                if(postWorkOutCheckBox.isChecked()){
                    //sampleUi.set(postWorkOutCheckBoxStatusPosition,"true");
                    editor.putString("postWorkOutCheckBoxStatus","true").commit();
                    //editor4.commit();
                    //editor4.apply();

                    Calendar postWorkOutCalendar = Calendar.getInstance();
                    intent.putExtra("Notification Content","PostWorkOut");
                    intent.putExtra("Notification title","Its time for your post work out");

                    postWorkOutCalendar.setTime(newDate.getTime());
                    postWorkOutCalendar.set(Calendar.HOUR_OF_DAY,postWorkOutCalendar.get(Calendar.HOUR_OF_DAY) + 1);

                    postWorkOutNotTime = postWorkOutCalendar.getTimeInMillis();
                    pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 103, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    if(postWorkOutNotTime > currentTime){
                        if(everyDayNotificationsSwitch.isChecked()){
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,postWorkOutNotTime,AlarmManager.INTERVAL_DAY, pendingIntent);
                        } else{
                            alarmManager.set(AlarmManager.RTC_WAKEUP, postWorkOutNotTime, pendingIntent);
                        }
                    }
                }else{
                    //sampleUi.set(postWorkOutCheckBoxStatusPosition,"false");
                    editor.putString("postWorkOutCheckBoxStatus","false").commit();
                    //editor4.commit();
                    //editor4.apply();
                }
                //sampleUi.set(newDatePosition,newDate.toString());
                //savedUiPreferencesSet.addAll(sampleUi);
                //editor.putStringSet("savedUiPreferencesSet",sampleUi).commit();
                editor.apply();
                //Toast.makeText(getApplicationContext(),"to date se string einai "+ sampleUi.toString(),Toast.LENGTH_LONG).show();

            }
        });
    }

    private void init(){
        preferences = NotificationsActivity.this.getSharedPreferences("savedUiPreferences", Context.MODE_PRIVATE);
        editor = preferences.edit();
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