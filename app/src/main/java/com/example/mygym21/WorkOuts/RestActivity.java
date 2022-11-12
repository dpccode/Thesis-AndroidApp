package com.example.mygym21.WorkOuts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mygym21.R;
import com.example.mygym21.WorkOuts.adapters.RecyclerAdapter;
import com.example.mygym21.WorkOuts.viewmodels.ExerciseViewModel;

import java.util.Locale;

public class RestActivity extends AppCompatActivity {
    private long START_TIME_IN_MILLIS = 5000;
    private int progr = 0;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private TextView mTextViewCountDown;
    private CountDownTimer mCountDownTimer;
    private String timeLeftFormatted,program_title,theme,gender;
    private int step;
    private int step2 = 333;
    int i = 0;
    private Button mPausebtn;
    private Button mSkipbtn;
    private int totalExercises;
    private int exercisesComplitedCount,customer_id;
    private TextView nextExercise;
    ImageView restImage;

    //MyTimer mTimer = new MyTimer();

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private ExerciseViewModel mExerciseViewModel;
    private ExerciseActivity mExerciseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getCustomersData();
        getThemePref();
        if(theme.equals("Light"))
            setTheme(R.style.AppTheme);
        else
            setTheme(R.style.DarkTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);

        Intent intent = getIntent();
        totalExercises = intent.getIntExtra("total_exercises",0);
        exercisesComplitedCount = intent.getIntExtra("exercises_complited_count",0);
        program_title = intent.getStringExtra("program_title");
        getSupportActionBar().setTitle(program_title);

        mExerciseViewModel = ViewModelProviders.of(RestActivity.this).get(ExerciseViewModel.class);
        mExerciseViewModel.init();

        START_TIME_IN_MILLIS = ((mExerciseViewModel.getHomeExercises().getValue().get(exercisesComplitedCount - 1).getRestMinutes() * 60) + mExerciseViewModel.getHomeExercises().getValue().get(exercisesComplitedCount - 1).getRestSeconds()) * 1000;
        //Toast.makeText(RestActivity.this,"8esh "+exercisesComplitedCount+"seconds "+mExerciseViewModel.getBeginnersExercises().getValue().get(exercisesComplitedCount).getRestSeconds(),Toast.LENGTH_LONG).show();
        mTimeLeftInMillis = START_TIME_IN_MILLIS;

        //Next view
        nextExercise = findViewById(R.id.next_exercise);
        restImage = findViewById(R.id.restImageView);
        chooseImageByGender(restImage);
        if(exercisesComplitedCount < totalExercises) {
            nextExercise.setText(mExerciseViewModel.getHomeExercises().getValue().get(exercisesComplitedCount).getTitle().toUpperCase());
        }
        else{
            //finish();
            nextExercise.setText("Its the last exercise!!");
        }


        startTimer();
        mPausebtn = findViewById(R.id.Pause_rest);
        mPausebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning)
                    pauseTimer();
                else
                    continueTimer();
            }
        });

        mSkipbtn = findViewById(R.id.Skip_rest);
        mSkipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();

                Intent intent = new Intent(RestActivity.this, ExerciseActivity.class);
                intent.putExtra("total_exercises",totalExercises);
                intent.putExtra("exercises_complited_count",exercisesComplitedCount);
                intent.putExtra("program_title",program_title);
                finish();
                startActivity(intent);
            }
        });


        // initRecyclerView();
    }


    public void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                i++;

                progr = (int) ((int) i * 100 / (START_TIME_IN_MILLIS / 1000));

                updateCountDownText();
                updateProgressBar(progr);
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;

                Intent intent = getIntent();
                exercisesComplitedCount = intent.getIntExtra("exercises_complited_count",0);
                //Toast.makeText(RestActivity.this,"mege8os listas "+exercisesComplitedCount,Toast.LENGTH_SHORT).show();
                totalExercises = intent.getIntExtra("total_exercises",0);
                program_title = intent.getStringExtra("program_title");
                if (exercisesComplitedCount < totalExercises) {
                    intent = new Intent(RestActivity.this, ExerciseActivity.class);
                    intent.putExtra("total_exercises",totalExercises);
                    intent.putExtra("exercises_complited_count",exercisesComplitedCount);
                    intent.putExtra("program_title",program_title);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //finishAffinity();
                    finish();
                    startActivity(intent);
                }

            }
        }.start();
        mTimerRunning = true;
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mPausebtn.setText("Continue");

    }

    private void continueTimer() {
        i--;
        startTimer();
        mTimerRunning = true;
        mPausebtn.setText("PAUSE");
    }


    private void updateProgressBar(int progr) {
        ProgressBar pb = findViewById(R.id.progressBar2);
        step2 = 3;

        pb.setProgress(100 - progr);

    }


    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown = findViewById(R.id.coutDown);
        mTextViewCountDown.setText(timeLeftFormatted);

    }

    private void getCustomersData(){
        SharedPreferences preferences = getApplication().getSharedPreferences("customers_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        customer_id = preferences.getInt("customer_id",0);
        gender = preferences.getString("gender","Male");

    }

    public  void getThemePref(){
        SharedPreferences preferences = getApplication().getSharedPreferences(String.valueOf(customer_id)+"themePref", Context.MODE_PRIVATE);
        theme = preferences.getString("theme","Light");
    }

    public void chooseImageByGender(ImageView imageView) {
        if(gender.equals("Male")){
            imageView.setBackground(getResources().getDrawable(R.drawable.male_rest2));
        }else {
            //imageView.setImageDrawable(getResources().getDrawable(R.drawable.woman_rest));
            imageView.setBackground(getResources().getDrawable(R.drawable.woman_rest));
        }

    }


    @Override
    public void onBackPressed() {
        AlertDialog dialog = new AlertDialog.Builder(RestActivity.this).create();
        dialog.setTitle("Are you sure?");
        dialog.setMessage("Do you want to to exit workout?");

        dialog.setButton(Dialog.BUTTON_POSITIVE,"Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mTimerRunning)
                    pauseTimer();
                finish();
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
        return;

    }
}