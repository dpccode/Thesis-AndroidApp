package com.example.mygym21.WorkOuts.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.mygym21.WorkOuts.models.Exercise;

import com.example.mygym21.WorkOuts.repositories.ExerciseRepository;
import com.example.mygym21.WorkOuts.repositories.HomeRepository;

import java.util.List;

public class ExerciseViewModel extends ViewModel {

    public MutableLiveData<List<Exercise>> mExercises;
    public MutableLiveData<List<Exercise>> mHomeExercises;
    private ExerciseRepository mRepository;
    private HomeRepository mHomeRepository;

    private int totalExercises;
    //private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();

    public void init(){
            if(mExercises != null){
                return;
            }
            //copy the list of exercises here to mExercises
            mRepository = ExerciseRepository.getInstance();
            mExercises = mRepository.getExercises();

            mHomeRepository = HomeRepository.getInstance();
            mHomeExercises =  mHomeRepository.getExercises();


    }


/*
    public void addNewValue(final Exercise exercise){
        List<Exercise> currentExercises = mExercises.getValue();
        currentExercises.add(exercise);
        mExercises.postValue(currentExercises);
    }

 */

    public LiveData<List<Exercise>> getExercises(){
        return mExercises;
    }

    public LiveData<List<Exercise>> getHomeExercises(){
        return mHomeExercises;
    }

}
