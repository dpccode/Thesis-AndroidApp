package com.example.mygym21.WorkOuts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mygym21.MainActivity;
import com.example.mygym21.R;
import com.example.mygym21.WorkOuts.adapters.RecyclerAdapter;
import com.example.mygym21.WorkOuts.models.Exercise;
import com.example.mygym21.WorkOuts.viewmodels.ExerciseViewModel;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.json.JSONException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.xml.transform.Result;

public class ExerciseActivity extends AppCompatActivity {
    private long START_TIME_IN_MILLIS = 5000;
    private int progr = 0;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis;
    private TextView mTextViewCountDown,exercise_weight;
    private CountDownTimer mCountDownTimer;
    private String timeLeftFormatted,theme;
    private int customer_id;
    int i = 0;
    private int exercisesComplitedCount = 0;
    private Button mPausebtn;
    private Button mSkipbtn;
    private String program_title;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private ExerciseViewModel mExerciseViewModel;
    private int totalExercises;
    private List<Exercise> mExercises = new ArrayList<>();
    private YouTubePlayerView youTubePlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getCustomersData();
        getThemePref();
        if(theme.equals("Light"))
            setTheme(R.style.AppTheme);
        else
            setTheme(R.style.DarkTheme);

        super.onCreate(savedInstanceState);
        getCustomersData();
        mExerciseViewModel = ViewModelProviders.of(ExerciseActivity.this).get(ExerciseViewModel.class);
        mExerciseViewModel.init();
        Intent intent = getIntent();
        TextView exerciseTitle;

        totalExercises = intent.getIntExtra("total_exercises", 0);
        exercisesComplitedCount = intent.getIntExtra("exercises_complited_count",0);
        program_title = intent.getStringExtra("program_title");
        getSupportActionBar().setTitle(program_title);


        if(mExerciseViewModel.getHomeExercises().getValue().get(exercisesComplitedCount).getReps() == 0){
            setContentView(R.layout.work_out_with_coutdown );
            exerciseTitle = findViewById(R.id.exercise_title_work_out_with_coutdown);
            exerciseTitle.setText(mExerciseViewModel.getHomeExercises().getValue().get(exercisesComplitedCount).getTitle().toUpperCase());

            START_TIME_IN_MILLIS = ((mExerciseViewModel.getHomeExercises().getValue().get(exercisesComplitedCount).getExerciseMinutes() * 60) + mExerciseViewModel.getHomeExercises().getValue().get(exercisesComplitedCount).getExerciseSeconds()) * 1000;
            mTimeLeftInMillis = START_TIME_IN_MILLIS;

            startTimer();

            mPausebtn = findViewById(R.id.Pause);
            mPausebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTimerRunning)
                        pauseTimer();
                    else
                        continueTimer();
                }
            });

            mSkipbtn = findViewById(R.id.skipbtn_work_out_with_coutdown);
            mSkipbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pauseTimer();
                    exercisesComplitedCount++;
                    if (exercisesComplitedCount < totalExercises) {
                        Intent intent = new Intent(ExerciseActivity.this, ExerciseActivity.class);
                        intent.putExtra("total_exercises",totalExercises);
                        intent.putExtra("exercises_complited_count",exercisesComplitedCount);
                        intent.putExtra("program_title",program_title);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                    }
                    else{
                        workOutFinished();
                        Intent intent = new Intent(ExerciseActivity.this, MainActivity.class);
                        intent.putExtra("program_title",program_title);
                        finish();
                    }
                }
            });
        }
        else{
            setContentView(R.layout.work_out_with_reps);
            TextView mReps;
            mReps = findViewById(R.id.exercise_reps);
            mReps.setText(String.valueOf("x"+mExerciseViewModel.getHomeExercises().getValue().get(exercisesComplitedCount).getReps()));


            exerciseTitle = findViewById(R.id.exercise_title_work_out_with_reps);
            exerciseTitle.setText(mExerciseViewModel.getHomeExercises().getValue().get(exercisesComplitedCount).getTitle().toUpperCase());


            /*An pathsw skip paw se next activity pou einai Rest
             *an exw allh askshsh alliws oloklhrw8hke to work out kai epistrefw
             */
            mSkipbtn = findViewById(R.id.donebtn_work_out_with_reps);
            mSkipbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    exercisesComplitedCount++;
                    if (exercisesComplitedCount < totalExercises) {


                        Intent intent = new Intent(ExerciseActivity.this, RestActivity.class);
                        //metaferw twn ari8mo twn sunolikwn askhsewn pou prepei na ektelestoun kai
                        //ton ari8mo oswn exoun ektelestei mexri twra
                        intent.putExtra("total_exercises", totalExercises);
                        intent.putExtra("exercises_complited_count", exercisesComplitedCount);
                        intent.putExtra("program_title",program_title);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                    }
                    else{

                        workOutFinished();
                        Intent intent = new Intent(ExerciseActivity.this, MainActivity.class);
                        intent.putExtra("program_title",program_title);
                        intent.putExtra("work_out_finished",true);
                        finish();

                    }
                }
            });
        }

        if(program_title.contains("Personal")){

            exercise_weight = findViewById(R.id.exercise_weight);
            if(mExerciseViewModel.getHomeExercises().getValue().get(exercisesComplitedCount).getWeight() > 0) {
                exercise_weight.setVisibility(View.VISIBLE);
                exercise_weight.setText(String.valueOf(mExerciseViewModel.getHomeExercises().getValue().get(exercisesComplitedCount).getWeight()) + " Kg");
            }
        }
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
                totalExercises = intent.getIntExtra("total_exercises",0);
                program_title = intent.getStringExtra("program_title");
                exercisesComplitedCount++;

                if (exercisesComplitedCount < totalExercises) {

                    intent = new Intent(ExerciseActivity.this, RestActivity.class);
                    intent.putExtra("total_exercises",totalExercises);
                    intent.putExtra("exercises_complited_count",exercisesComplitedCount);
                    intent.putExtra("program_title",program_title);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                }
                else{
                    workOutFinished();
                    intent = new Intent(ExerciseActivity.this, MainActivity.class);
                    intent.putExtra("program_title",program_title);
                    finish();
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
        pb.setProgress(100 - progr);

    }


    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown = findViewById(R.id.coutDown);
        mTextViewCountDown.setText(timeLeftFormatted);

    }

    private void workOutFinished() {
        SharedPreferences preferences = getApplication().getSharedPreferences(String.valueOf(customer_id)+"history", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        int totalWorkOutsFinished = preferences.getInt("total_workOuts_finished", 0);
        totalWorkOutsFinished++;
        editor.putInt("total_workOuts_finished", totalWorkOutsFinished).commit();
        editor.putString("workOut_title" + String.valueOf(totalWorkOutsFinished), program_title).commit();


        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        editor.putString("workOut_date" + String.valueOf(totalWorkOutsFinished), date).commit();
        editor.apply();

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

    @Override
    public void onBackPressed() {
        AlertDialog dialog = new AlertDialog.Builder(ExerciseActivity.this).create();
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