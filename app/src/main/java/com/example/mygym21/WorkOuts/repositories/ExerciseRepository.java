package com.example.mygym21.WorkOuts.repositories;

import androidx.lifecycle.MutableLiveData;


import com.example.mygym21.WorkOuts.models.Exercise;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExerciseRepository {

    private static ExerciseRepository instance;
    private ArrayList<Exercise> dataSet = new ArrayList<>();

    public static ExerciseRepository getInstance(){
        if(instance == null){
            instance = new ExerciseRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Exercise>> getExercises(){
            MutableLiveData<List<Exercise>> data = new MutableLiveData<>();
            data.setValue(dataSet);
            return data;
    }

    public void setExercises(JSONArray jsonArray){

        dataSet.clear();
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArray.getJSONObject(i);
                dataSet.add(new Exercise(jsonObject.getString("ex_title"),jsonObject.getInt("reps"),"",jsonObject.getInt("exercise_minutes"),jsonObject.getInt("exercise_seconds"),jsonObject.getInt("rest_minutes"),jsonObject.getInt("rest_seconds"),0));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
