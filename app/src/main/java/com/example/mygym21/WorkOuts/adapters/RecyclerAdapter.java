package com.example.mygym21.WorkOuts.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mygym21.R;
import com.example.mygym21.WorkOuts.models.Exercise;

import java.util.ArrayList;
import java.util.List;

//import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.menu.defaultMenu.MenuAdapter;
//import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.menu.defaultMenu.MenuAdapter;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Exercise> mExercises = new ArrayList<>();
    private Context mContext;

    public RecyclerAdapter(Context context, List<Exercise> exerc){
        mExercises = exerc;
        mContext = context;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_work_out_item,parent,false);
        //RecyclerView.ViewHolder holder = new RecyclerView.ViewHolder(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        //Exercise currentExercise = mExercises.get(i);
        ((ViewHolder) viewHolder).title.setText(String.valueOf(i + 1) + "/" + String.valueOf(getItemCount()) + ": " + mExercises.get(viewHolder.getAdapterPosition()).getTitle().toUpperCase());
        if(mExercises.get(viewHolder.getAdapterPosition()).getReps() == 0){
            if(mExercises.get(viewHolder.getAdapterPosition()).getExerciseMinutes() > 0) {
                ((ViewHolder) viewHolder).duration_or_reps.setText(mExercises.get(viewHolder.getAdapterPosition()).getExerciseMinutes() + "m"
                        + mExercises.get(viewHolder.getAdapterPosition()).getExerciseSeconds()+"s");
            }
            else{
                ((ViewHolder) viewHolder).duration_or_reps.setText(mExercises.get(viewHolder.getAdapterPosition()).getExerciseSeconds()+"s");
            }
        }else{
            ((ViewHolder) viewHolder).duration_or_reps.setText("x"+mExercises.get(viewHolder.getAdapterPosition()).getReps());
        }

        ((ViewHolder)viewHolder).rest.setText("Rest: "+String.valueOf(mExercises.get(viewHolder.getAdapterPosition()).getRestSeconds())+"s");
        //((ViewHolder)viewHolder).reps.setText(mExercises.get(i).getReps());



    }

    @Override
    public int getItemCount() {
        return mExercises.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView rest;
        TextView duration_or_reps;
        RecyclerView parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.exercise_title);
            rest = itemView.findViewById(R.id.rest_time);
            duration_or_reps = itemView.findViewById(R.id.exercise_duration_or_reps);
            //parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

}
