package com.example.mygym21.WorkOuts.repositories;

import androidx.lifecycle.MutableLiveData;


import com.example.mygym21.WorkOuts.models.Exercise;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeRepository {
    private ArrayList<Exercise> dataSet = new ArrayList<>();
    private static HomeRepository instance;

    public static HomeRepository getInstance(){
        if(instance == null){
            instance = new HomeRepository();
        }
        return instance;
    }

    public void setPersonalExercises(JSONArray jsonArray){

        dataSet.clear();
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArray.getJSONObject(i);
                dataSet.add(new Exercise(jsonObject.getString("ex_title"),jsonObject.getInt("reps"),"",jsonObject.getInt("exercise_minutes"),jsonObject.getInt("exercise_seconds"),jsonObject.getInt("rest_minutes"),jsonObject.getInt("rest_seconds"),jsonObject.getInt("weight")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


    //CHEST
    public void setChest_lvl_1(){
        dataSet.clear();
        dataSet.add(new Exercise("JUMPING JACKS",0,"kLmWN3Qsj0A",0,30,0,30,0));
        dataSet.add(new Exercise("INCLINE PUSH-UPS",6,"kLmWN3Qsj0A",1,0,0,30,0));
        dataSet.add(new Exercise("PUSH-UPS",4,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("WIDE ARMS PUSH-UPS",4,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("TRICEPS DIPS",4,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("WIDE ARMS PUSH-UPS",4,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("TRICEPS DIPS",6,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("KNEE PUSH-UPS",4,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("COBRA STRETCH",0,"kLmWN3Qsj0A",0,20,0,30,0));
        dataSet.add(new Exercise("CHEST STRETCH",0,"kLmWN3Qsj0A",0,20,0,30,0));
    }

    public void setChest_lvl_2(){
        dataSet.clear();
        dataSet.add(new Exercise("JUMPING JACKS",0,"kLmWN3Qsj0A",0,30,0,30,0));
        dataSet.add(new Exercise("KNEE PUSH-UPS",12,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("PUSH-UPS",12,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("WIDE ARMS PUSH-UPS",16,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("HINDU PUSH-UPS",10,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("STAGGERED PUSH-UPS",12,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("PUSH-UPS & ROTATION",10,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("KNEE PUSH-UPS",10,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("HINDU PUSH-UPS",10,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("DECLINE PUSH-UPS",10,"kLmWN3Qsj0A",0,20,0,30,0));
        dataSet.add(new Exercise("STAGGERED PUSH-UPS",10,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("SHOULDER STRETCH",0,"kLmWN3Qsj0A",0,30,0,30,0));
        dataSet.add(new Exercise("COBRA STRETCH",0,"kLmWN3Qsj0A",0,30,0,30,0));
        dataSet.add(new Exercise("CHEST STRETCH",0,"kLmWN3Qsj0A",0,30,0,30,0));
    }

    public void setChest_lvl_3(){
        dataSet.clear();
        dataSet.add(new Exercise("JUMPING JACKS",0,"kLmWN3Qsj0A",0,30,0,10,0));
        dataSet.add(new Exercise("ARM CIRCLES",20,"kLmWN3Qsj0A",0,0,0,15,0));
        dataSet.add(new Exercise("SHOULDER STRETCH",0,"kLmWN3Qsj0A",0,30,0,10,0));
        dataSet.add(new Exercise("BURPEES",10,"kLmWN3Qsj0A",0,0,0,25,0));
        dataSet.add(new Exercise("HINDU PUSH-UPS",16,"kLmWN3Qsj0A",0,0,0,25,0));
        dataSet.add(new Exercise("STAGGERED PUSH-UPS",16,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("PUSH-UPS & ROTATION",12,"kLmWN3Qsj0A",0,0,0,25,0));
        dataSet.add(new Exercise("KNEE PUSH-UPS",20,"kLmWN3Qsj0A",0,0,0,25,0));
        dataSet.add(new Exercise("HINDU PUSH-UPS",12,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("DECLINE PUSH-UPS",12,"kLmWN3Qsj0A",0,20,0,30,0));
        dataSet.add(new Exercise("STAGGERED PUSH-UPS",10,"kLmWN3Qsj0A",0,0,0,35,0));
        dataSet.add(new Exercise("BURPEES",10,"kLmWN3Qsj0A",0,0,0,35,0));
        dataSet.add(new Exercise("KNEE PUSH-UPS",10,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("SHOULDER STRETCH",0,"kLmWN3Qsj0A",0,30,0,20,0));
        dataSet.add(new Exercise("COBRA STRETCH",0,"kLmWN3Qsj0A",0,30,0,20,0));
        dataSet.add(new Exercise("CHEST STRETCH",0,"kLmWN3Qsj0A",0,30,0,30,0));
    }

    public void setAbs_lvl_1(){
        dataSet.clear();
        dataSet.add(new Exercise("JUMPING JACKS",0,"kLmWN3Qsj0A",0,20,0,10,0));
        dataSet.add(new Exercise("ABDOMINAL CRUNCHES",16,"kLmWN3Qsj0A",1,0,0,25,0));
        dataSet.add(new Exercise("RUSSIAN TWIST",20,"kLmWN3Qsj0A",0,0,0,25,0));
        dataSet.add(new Exercise("MOUNTAIN CLIMBER",16,"kLmWN3Qsj0A",0,0,0,25,0));
        dataSet.add(new Exercise("HEEL TOUCH",20,"kLmWN3Qsj0A",0,0,0,25,0));
        dataSet.add(new Exercise("LEG RAISES",16,"kLmWN3Qsj0A",0,0,0,25,0));
        dataSet.add(new Exercise("PLANK",0,"kLmWN3Qsj0A",0,30,0,25,0));
        dataSet.add(new Exercise("ABDOMINAL CRUNCHES",10,"kLmWN3Qsj0A",1,0,0,30,0));
        dataSet.add(new Exercise("HEEL TOUCH",20,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("LEG RAISES",12,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("PLANK",0,"kLmWN3Qsj0A",0,30,0,40,0));
        dataSet.add(new Exercise("COBRA STRETCH",0,"kLmWN3Qsj0A",0,25,0,30,0));
    }

    public void setAbs_lvl_2(){
        dataSet.clear();
        dataSet.add(new Exercise("JUMPING JACKS",0,"kLmWN3Qsj0A",0,30,0,10,0));
        dataSet.add(new Exercise("ABDOMINAL CRUNCHES",20,"kLmWN3Qsj0A",1,0,0,25,0));
        dataSet.add(new Exercise("CROSSOVER CRUNCH",20,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("MOUNTAIN CLIMBER",20,"kLmWN3Qsj0A",0,0,0,25,0));
        dataSet.add(new Exercise("BUTT BRIDGE",20,"kLmWN3Qsj0A",0,0,0,25,0));
        dataSet.add(new Exercise("BICYCLE CRUNCHES",10,"kLmWN3Qsj0A",1,0,0,30,0));
        dataSet.add(new Exercise("HEEL TOUCH",26,"kLmWN3Qsj0A",0,0,0,25,0));
        dataSet.add(new Exercise("LEG RAISES",16,"kLmWN3Qsj0A",0,0,0,25,0));
        dataSet.add(new Exercise("PLANK",0,"kLmWN3Qsj0A",0,40,0,25,0));
        dataSet.add(new Exercise("ABDOMINAL CRUNCHES",10,"kLmWN3Qsj0A",1,0,0,30,0));
        dataSet.add(new Exercise("HEEL TOUCH",20,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("LEG RAISES",12,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("PUSH-UPS & ROTATION",12,"kLmWN3Qsj0A",0,0,0,25,0));
        dataSet.add(new Exercise("PLANK",0,"kLmWN3Qsj0A",0,40,0,40,0));
        dataSet.add(new Exercise("HEEL TOUCH",16,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("COBRA STRETCH",0,"kLmWN3Qsj0A",0,20,0,30,0));
    }

    public void setAbs_lvl_3(){
        dataSet.clear();
        dataSet.add(new Exercise("JUMPING JACKS",0,"kLmWN3Qsj0A",0,60,0,10,0));
        dataSet.add(new Exercise("SIT-UPS",20,"kLmWN3Qsj0A",0,0,0,25,0));
        dataSet.add(new Exercise("ABDOMINAL CRUNCHES",30,"kLmWN3Qsj0A",1,0,0,30,0));
        dataSet.add(new Exercise("CROSSOVER CRUNCH",20,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("MOUNTAIN CLIMBER",20,"kLmWN3Qsj0A",0,0,0,25,0));
        dataSet.add(new Exercise("BUTT BRIDGE",20,"kLmWN3Qsj0A",0,0,0,25,0));
        dataSet.add(new Exercise("BICYCLE CRUNCHES",24,"kLmWN3Qsj0A",1,0,0,30,0));
        dataSet.add(new Exercise("PUSH-UPS & ROTATION",24,"kLmWN3Qsj0A",0,0,0,25,0));
        dataSet.add(new Exercise("RUSSIAN TWIST",50,"kLmWN3Qsj0A",0,0,0,25,0));
        dataSet.add(new Exercise("HEEL TOUCH",26,"kLmWN3Qsj0A",0,0,0,25,0));
        dataSet.add(new Exercise("LEG RAISES",16,"kLmWN3Qsj0A",0,0,0,25,0));
        dataSet.add(new Exercise("PLANK",0,"kLmWN3Qsj0A",0,0,0,25,0));
        dataSet.add(new Exercise("ABDOMINAL CRUNCHES",10,"kLmWN3Qsj0A",1,0,0,30,0));
        dataSet.add(new Exercise("HEEL TOUCH",24,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("LEG RAISES",18,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("PUSH-UPS & ROTATION",20,"kLmWN3Qsj0A",0,0,0,25,0));
        dataSet.add(new Exercise("PLANK",0,"kLmWN3Qsj0A",0,59,0,40,0));
        dataSet.add(new Exercise("HEEL TOUCH",16,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("COBRA STRETCH",0,"kLmWN3Qsj0A",0,40,0,30,0));
    }


    public void setArms_lvl_1(){
        dataSet.clear();
        dataSet.add(new Exercise("ARM RAISES",0,"kLmWN3Qsj0A",0,30,0,10,0));
        dataSet.add(new Exercise("SIDE ARM RAISES",0,"kLmWN3Qsj0A",0,30,0,25,0));
        dataSet.add(new Exercise("TRICEPS DIPS",10,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("DIAGONAL PLANK",10,"kLmWN3Qsj0A",0,0,0,25,0));
        dataSet.add(new Exercise("JUMPING JACKS",0,"kLmWN3Qsj0A",0,30,0,25,0));
        dataSet.add(new Exercise("PUNCHES",0,"kLmWN3Qsj0A",0,30,0,25,0));
        dataSet.add(new Exercise("PUSH-UPS",10,"kLmWN3Qsj0A",0,0,0,20,0));
        dataSet.add(new Exercise("WIDE ARMS PUSH-UPS",10,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("KNEE PUSH-UPS",12,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("STANDING BICEPS STRETCH",0,"kLmWN3Qsj0A",0,30,0,30,0));
        dataSet.add(new Exercise("TRICEPS STRETCH",0,"kLmWN3Qsj0A",0,30,0,30,0));
    }


    public void setArms_lvl_2(){
        dataSet.clear();
        dataSet.add(new Exercise("ARM RAISES",0,"kLmWN3Qsj0A",0,30,0,10,0));
        dataSet.add(new Exercise("SIDE ARM RAISES",0,"kLmWN3Qsj0A",0,30,0,25,0));
        dataSet.add(new Exercise("PUSH-UPS",20,"kLmWN3Qsj0A",0,0,0,20,0));
        dataSet.add(new Exercise("TRICEPS DIPS",20,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("DIAGONAL PLANK",10,"kLmWN3Qsj0A",0,0,0,25,0));
        dataSet.add(new Exercise("JUMPING JACKS",0,"kLmWN3Qsj0A",0,30,0,25,0));
        dataSet.add(new Exercise("PUNCHES",0,"kLmWN3Qsj0A",0,40,0,25,0));
        dataSet.add(new Exercise("PUSH-UPS",0,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("WIDE ARMS PUSH-UPS",16,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("KNEE PUSH-UPS",10,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("PUSH-UPS & ROTATION",8,"kLmWN3Qsj0A",0,0,0,25,0));
        dataSet.add(new Exercise("BURPEES",12,"kLmWN3Qsj0A",0,0,0,35,0));
        dataSet.add(new Exercise("STANDING BICEPS STRETCH",10,"kLmWN3Qsj0A",0,0,0,20,0));
        dataSet.add(new Exercise("TRICEPS STRETCH",10,"kLmWN3Qsj0A",0,0,0,30,0));
    }


    public void setArms_lvl_3(){
        dataSet.clear();
        dataSet.add(new Exercise("ARM RAISES",0,"kLmWN3Qsj0A",0,40,0,10,0));
        dataSet.add(new Exercise("SIDE ARM RAISES",0,"kLmWN3Qsj0A",0,40,0,20,0));
        dataSet.add(new Exercise("PUSH-UPS",20,"kLmWN3Qsj0A",0,0,0,20,0));
        dataSet.add(new Exercise("TRICEPS DIPS",20,"kLmWN3Qsj0A",0,0,0,20,0));
        dataSet.add(new Exercise("DIAGONAL PLANK",10,"kLmWN3Qsj0A",0,0,0,20,0));
        dataSet.add(new Exercise("JUMPING JACKS",0,"kLmWN3Qsj0A",0,30,0,25,0));
        dataSet.add(new Exercise("PUNCHES",0,"kLmWN3Qsj0A",0,40,0,25,0));
        dataSet.add(new Exercise("PUSH-UPS",12,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("SIDE ARM RAISES",0,"kLmWN3Qsj0A",0,40,0,20,0));
        dataSet.add(new Exercise("WIDE ARMS PUSH-UPS",16,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("KNEE PUSH-UPS",10,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("PUSH-UPS & ROTATION",8,"kLmWN3Qsj0A",0,0,0,20,0));
        dataSet.add(new Exercise("BURPEES",12,"kLmWN3Qsj0A",0,0,0,25,0));
        dataSet.add(new Exercise("JUMPING JACKS",0,"kLmWN3Qsj0A",0,30,0,25,0));
        dataSet.add(new Exercise("PUNCHES",0,"kLmWN3Qsj0A",0,40,0,25,0));
        dataSet.add(new Exercise("PUSH-UPS",12,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("STANDING BICEPS STRETCH",10,"kLmWN3Qsj0A",0,0,0,10,0));
        dataSet.add(new Exercise("TRICEPS STRETCH",10,"kLmWN3Qsj0A",0,0,0,30,0));
    }

    public void setLegs_lvl_1(){
        dataSet.clear();
        dataSet.add(new Exercise("SQUATS",12,"kLmWN3Qsj0A",0,30,0,20,0));
        dataSet.add(new Exercise("SQUATS",12,"kLmWN3Qsj0A",0,30,0,30,0));
        dataSet.add(new Exercise("JUMPING JACKS",0,"kLmWN3Qsj0A",0,30,0,30,0));
        dataSet.add(new Exercise("DONKEY KICKS RIGHT",16,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("DONKEY KICKS LEFT",16,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("JUMPING JACKS",0,"kLmWN3Qsj0A",0,30,0,25,0));
        dataSet.add(new Exercise("SQUATS",10,"kLmWN3Qsj0A",0,30,0,10,0));
        dataSet.add(new Exercise("CALF STRETCH RIGHT",0,"kLmWN3Qsj0A",0,30,0,30,0));
        dataSet.add(new Exercise("CALF STRETCH LEFT",0,"kLmWN3Qsj0A",0,30,0,30,0));
    }

    public void setLegs_lvl_2(){
        dataSet.clear();
        dataSet.add(new Exercise("SQUATS",14,"kLmWN3Qsj0A",0,30,0,20,0));
        dataSet.add(new Exercise("SQUATS",14,"kLmWN3Qsj0A",0,30,0,25,0));
        dataSet.add(new Exercise("SQUATS",14,"kLmWN3Qsj0A",0,40,0,25,0));
        dataSet.add(new Exercise("JUMPING JACKS",0,"kLmWN3Qsj0A",0,40,0,25,0));
        dataSet.add(new Exercise("DONKEY KICKS RIGHT",16,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("DONKEY KICKS LEFT",16,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("JUMPING JACKS",0,"kLmWN3Qsj0A",0,30,0,25,0));
        dataSet.add(new Exercise("LUNGES",14,"kLmWN3Qsj0A",0,30,0,35,0));
        dataSet.add(new Exercise("LUNGES",14,"kLmWN3Qsj0A",0,30,0,30,0));
        dataSet.add(new Exercise("SQUATS",8,"kLmWN3Qsj0A",0,30,0,10,0));
        dataSet.add(new Exercise("CALF STRETCH RIGHT",0,"kLmWN3Qsj0A",0,40,0,30,0));
        dataSet.add(new Exercise("CALF STRETCH LEFT",0,"kLmWN3Qsj0A",0,40,0,30,0));
    }

    public void setLegs_lvl_3(){
        dataSet.clear();
        dataSet.add(new Exercise("BURPEES",12,"kLmWN3Qsj0A",0,0,0,25,0));
        dataSet.add(new Exercise("SQUATS",15,"kLmWN3Qsj0A",0,30,0,20,0));
        dataSet.add(new Exercise("SQUATS",15,"kLmWN3Qsj0A",0,30,0,25,0));
        dataSet.add(new Exercise("SQUATS",15,"kLmWN3Qsj0A",0,40,0,25,0));
        dataSet.add(new Exercise("JUMPING JACKS",0,"kLmWN3Qsj0A",0,40,0,25,0));
        dataSet.add(new Exercise("DONKEY KICKS RIGHT",16,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("DONKEY KICKS LEFT",16,"kLmWN3Qsj0A",0,0,0,30,0));
        dataSet.add(new Exercise("JUMPING JACKS",0,"kLmWN3Qsj0A",0,30,0,25,0));
        dataSet.add(new Exercise("LUNGES",14,"kLmWN3Qsj0A",0,30,0,35,0));
        dataSet.add(new Exercise("LUNGES",14,"kLmWN3Qsj0A",0,30,0,30,0));
        dataSet.add(new Exercise("SQUATS",20,"kLmWN3Qsj0A",0,30,0,10,0));
        dataSet.add(new Exercise("SQUATS",15,"kLmWN3Qsj0A",0,30,0,25,0));
        dataSet.add(new Exercise("SQUATS",10,"kLmWN3Qsj0A",0,40,0,25,0));
        dataSet.add(new Exercise("CALF STRETCH RIGHT",0,"kLmWN3Qsj0A",0,50,0,30,0));
        dataSet.add(new Exercise("CALF STRETCH LEFT",0,"kLmWN3Qsj0A",0,50,0,30,0));
    }


    public MutableLiveData<List<Exercise>> getExercises(){
        MutableLiveData<List<Exercise>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }


}
