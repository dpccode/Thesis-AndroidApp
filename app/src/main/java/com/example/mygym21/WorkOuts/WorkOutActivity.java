package com.example.mygym21.WorkOuts;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mygym21.MainActivity;
import com.example.mygym21.R;
import com.example.mygym21.WorkOuts.adapters.RecyclerAdapter;
import com.example.mygym21.WorkOuts.models.Exercise;
import com.example.mygym21.WorkOuts.repositories.ExerciseRepository;
import com.example.mygym21.WorkOuts.repositories.HomeRepository;
import com.example.mygym21.WorkOuts.viewmodels.ExerciseViewModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class WorkOutActivity extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 45000;
    private int progr = 0;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private TextView mTextViewCountDown,totalExercisesNum;
    private CountDownTimer mCountDownTimer;
    private String timeLeftFormatted;
    private int step;
    private int step2 = 333;
    int i = 0;
    Button mPausebtn;
    Button mStartbtn;
    private int exercisesCompletedCount;
    private String program_title,theme;
    private int customer_id;
    //MyTimer mTimer = new MyTimer();

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private ExerciseViewModel mExerciseViewModel;
    private ExerciseActivity mExerciseActivity;

    private boolean workOutFinished = false;
    private int totalWorkOutsFinished;

    private HomeRepository mHomeRepository;
    private ExerciseRepository mPersonalRepository;
    JSONArray personalJsonArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getCustomersData();
        getThemePref();
        if(theme.equals("Light"))
            setTheme(R.style.AppTheme);
        else
            setTheme(R.style.DarkTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_out);

        getSupportActionBar().setTitle("Workout List");
        mRecyclerView = findViewById(R.id.recycler_view);
        //mRecyclerView.setHasFixedSize(true);
        mStartbtn = findViewById(R.id.start_workout_btn);
        totalExercisesNum = findViewById(R.id.total_exercises);

        Intent intent = getIntent();
        //workOutFinished = intent.getBooleanExtra("work_out_finished", false);
        program_title = intent.getStringExtra("program_title");
        //Toast.makeText(WorkOutActivity.this,"program type einai sthn arxh tou work out "+program_type,Toast.LENGTH_LONG).show();




        //Toast.makeText(getApplicationContext(),"mege8os listas "+mAdapter.getItemCount(),Toast.LENGTH_SHORT).show();

        mExerciseViewModel = ViewModelProviders.of(WorkOutActivity.this).get(ExerciseViewModel.class);
        mExerciseViewModel.init();


        if(program_title.contains("Personal")){
            String program_exercices = intent.getStringExtra("program_exercises");
            try {
                personalJsonArray = new JSONArray(program_exercices);
                mHomeRepository.getInstance().setPersonalExercises(personalJsonArray);
                mExerciseViewModel.getExercises().observe(this, new Observer<List<Exercise>>() {
                    @Override
                    public void onChanged(List<Exercise> exercises) {
                        mAdapter.notifyDataSetChanged();
                    }
                });

                initHomeRecyclerView();
                totalExercisesNum.setText(String.valueOf(mAdapter.getItemCount())+" workouts");
                //Toast.makeText(getApplicationContext(),"comple "+mAdapter.getItemCount(),Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(),"mpainei sto error "+e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

        }else{
            switch (program_title) {
                case "Home: Beginners legs":
                    mHomeRepository.getInstance().setLegs_lvl_1();
                    break;
                case "Home: Intermediate legs":
                    mHomeRepository.getInstance().setLegs_lvl_2();
                    break;
                case "Home: Advanced legs":
                    mHomeRepository.getInstance().setLegs_lvl_3();
                    break;
                case "Home: Beginners Arms":
                    mHomeRepository.getInstance().setArms_lvl_1();
                    break;
                case "Home: Intermediate Arms":
                    mHomeRepository.getInstance().setArms_lvl_2();
                    break;
                case "Home: Advanced Arms":
                    mHomeRepository.getInstance().setArms_lvl_3();
                    break;
                case "Home: Beginners Chest":
                    mHomeRepository.getInstance().setChest_lvl_1();
                    break;
                case "Home: Intermediate Chest":
                    mHomeRepository.getInstance().setChest_lvl_2();
                    break;
                case "Home: Advanced Chest":
                    mHomeRepository.getInstance().setChest_lvl_3();
                    break;
                case "Home: Beginners Abs":
                    mHomeRepository.getInstance().setAbs_lvl_1();
                    break;
                case "Home: Intermediate Abs":
                    mHomeRepository.getInstance().setAbs_lvl_2();
                    break;
                case "Home: Advanced Abs":
                    mHomeRepository.getInstance().setAbs_lvl_3();
                    break;

            }
            //mHomeRepository.getInstance().setChest_lvl_2();
            mExerciseViewModel.getHomeExercises().observe(this, new Observer<List<Exercise>>() {
                @Override
                public void onChanged(List<Exercise> exercises) {
                    mAdapter.notifyDataSetChanged();
                }
            });
            initHomeRecyclerView();
            totalExercisesNum.setText(String.valueOf(mAdapter.getItemCount())+" workouts");
        }




        mStartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkOutActivity.this, ExerciseActivity.class);
                intent.putExtra("total_exercises", mAdapter.getItemCount());
                intent.putExtra("program_title",program_title);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivityForResult(intent,Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                //finishAffinity();
                startActivity(intent);
                //setContentView(R.layout.work_out_with_coutdown);
            }
        });

        //Toast.makeText(getApplicationContext(),"mege8os listas "+mAdapter.getItemCount(),Toast.LENGTH_SHORT).show();


    }


    private void initHomeRecyclerView() {
        mAdapter = new RecyclerAdapter(this, mExerciseViewModel.getHomeExercises().getValue());
        //androidx.recyclerview.widget.LinearLayoutManager linearLayoutManager= new androidx.recyclerview.widget.LinearLayoutManager(this);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);;

        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initPersonalRecyclerView() {
        mAdapter = new RecyclerAdapter(this, mExerciseViewModel.getExercises().getValue());

        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
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
        finish();

    }
}


