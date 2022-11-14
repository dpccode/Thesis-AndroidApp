package com.example.mygym21.caloriescalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.mygym21.R;

import java.text.DecimalFormat;

public class CaloriesCalculatorActivity extends AppCompatActivity {

    private double height = 0;
    private int weight = 0;
    private int age = 0;
    private double protein;
    private double carbs;
    private double fat;
    private double BMI;
    private double BMR; //Basal Metabolic Rate
    private double calories;
    private double maintainWeightCalories;
    private double mildWeightLossCalories;
    private double weightLossCalories;
    private double extremeWeightLossCalories;
    private double proteinCalories;
    private double carbsCalories;
    private double fatCalories;
    private String gender,theme;
    private int customer_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getCustomersData();
        getThemePref();
        if(theme.equals("Light"))
            setTheme(R.style.AppTheme);
        else
            setTheme(R.style.DarkTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories_calculator);
        getSupportActionBar().setTitle("Calories Calculator");


        final TextView heightTextView = findViewById(R.id.heightTextView);
        final TextView weightTextView = findViewById(R.id.weightTextView);
        final TextView ageTextView = findViewById(R.id.ageTextView);
        final TextView proteinTextView = findViewById(R.id.proteinTextView);
        final TextView carbsTextView = findViewById(R.id.carbsTextView);
        final TextView fatTextView = findViewById(R.id.fatTextView);
        final TextView maintainWeightTextView = findViewById(R.id.maintainWeightTextView);
        final TextView mildWeightLossTextView = findViewById(R.id.mildWeightLossTextView);
        final TextView weightLossTextView = findViewById(R.id.weightLossTextView);
        final TextView extremeWeightLossTextView = findViewById(R.id.extremeWeightLossTextView);

        final RadioButton noExercise = findViewById(R.id.radioButton1);
        final RadioButton ligthExercise = findViewById(R.id.radioButton2);
        final RadioButton moderateExercise = findViewById(R.id.radioButton3);
        final RadioButton heavyExercise = findViewById(R.id.radioButton4);
        final RadioButton veryHeavyExercise = findViewById(R.id.radioButton5);

        Button calculateBtn = findViewById(R.id.calculateBtn);


        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!heightTextView.getText().toString().equals("")){
                    height = Integer.parseInt(heightTextView.getText().toString());
                }

                if(!weightTextView.getText().toString().equals("")){
                    weight = Integer.parseInt(weightTextView.getText().toString());
                }

                if(!ageTextView.getText().toString().equals("")){
                    age    = Integer.parseInt(ageTextView.getText().toString());
                }

                BMR = (10 * weight) + (6.25 * height) - (5 * age);

                if(gender.equals("Male")){
                    BMR -= 5;
                }
                else{
                    BMR -= 161;
                }

                if(noExercise.isChecked()){ calories = 1.2 * BMR; }

                if(ligthExercise.isChecked()){ calories = 1.37 * BMR; }

                if(moderateExercise.isChecked()){ calories = 1.55 * BMR; }

                if(heavyExercise.isChecked()){ calories = 1.75 * BMR; }

                if(veryHeavyExercise.isChecked()){ calories = 1.9 * BMR; }

                proteinCalories = calories * 0.42;
                carbsCalories = calories * 0.38;
                fatCalories = calories * 0.2;

                protein = proteinCalories / 4; //protein in grams
                carbs = carbsCalories / 4;
                fat = fatCalories / 9;

                maintainWeightCalories = calories;
                mildWeightLossCalories = calories * 0.91;
                weightLossCalories = calories * 0.81;
                extremeWeightLossCalories = calories * 0.63;

                maintainWeightTextView.setText("Maintain weight: " + String.valueOf(new DecimalFormat("##").format(calories))+"cal/day");
                mildWeightLossTextView.setText("Mild weight loss(0.25kg/week): " + String.valueOf(new DecimalFormat("##").format(mildWeightLossCalories))+"cal/day");
                weightLossTextView.setText("Weight loss(0.5kg/week): " + String.valueOf(new DecimalFormat("##").format(weightLossCalories))+"cal/day");
                extremeWeightLossTextView.setText("Extreme weight loss(1kg/week): " + String.valueOf(new DecimalFormat("##").format(extremeWeightLossCalories))+"cal/day");

                proteinTextView.setText(String.valueOf(new DecimalFormat("##.##").format(protein))+"g");
                carbsTextView.setText(String.valueOf(new DecimalFormat("##.##").format(carbs))+"g");
                fatTextView.setText(String.valueOf(new DecimalFormat("##.##").format(fat))+"g");
            }
        });

    }

    private void getCustomersData(){
        SharedPreferences preferences = getApplication().getSharedPreferences("customers_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        gender = preferences.getString("gender","Male");
        customer_id = preferences.getInt("customer_id",0);
    }

    public  void getThemePref(){
        SharedPreferences preferences = getApplication().getSharedPreferences(String.valueOf(customer_id)+"themePref", Context.MODE_PRIVATE);
        theme = preferences.getString("theme","Light");
    }
}