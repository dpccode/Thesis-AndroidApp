package com.example.mygym21.WorkOuts.models;

public class Exercise {
    private String title;
    private int reps;
    private String VideoUrl;
    private int exerciseMinutes;
    private int exerciseSeconds;
    private int restMinutes;
    private int restSeconds;
    private int weight;

    public Exercise(String title, int reps, String videoUrl, int exerciseMinutes, int exerciseSeconds, int restMinutes, int restSeconds,int weight){
        this.title = title;
        this.reps = reps;
        this.VideoUrl = videoUrl;
        this.exerciseMinutes = exerciseMinutes;
        this.exerciseSeconds = exerciseSeconds;
        this.restMinutes = restMinutes;
        this.restSeconds = restSeconds;
        this.weight = weight;
    }

    public String getTitle(){return title;}

    public int getReps(){return reps;}

    public String getVideoUrl(){return VideoUrl;}

    public int getExerciseMinutes() { return exerciseMinutes; }

    public int getExerciseSeconds() { return exerciseSeconds; }

    public int getRestMinutes() { return restMinutes; }

    public int getRestSeconds() { return restSeconds; }

    public int getWeight() { return weight; }

    public void setTitle(String title){this.title = title;}

    public void setReps(int reps){this.reps = reps;}

    public void setVideoUrl(String videoUrl){this.VideoUrl = videoUrl;}

    public void setExerciseMinutes(int exerciseMinutes) { this.exerciseMinutes = exerciseMinutes; }

    public void setExerciseSeconds(int exerciseSeconds) { this.exerciseSeconds = exerciseSeconds; }

    public void setRestMinutes(int restMinutes) { this.restMinutes = restMinutes; }

    public void setRestSeconds(int restSeconds) { this.restSeconds = restSeconds; }

    public void setWeight(int weight) { this.weight = weight; }
}


