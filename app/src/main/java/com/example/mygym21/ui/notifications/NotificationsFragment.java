package com.example.mygym21.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mygym21.R;
import com.example.mygym21.WorkOuts.WorkOutActivity;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private int customer_id;
    private String gender;
    private ImageView legsBeginnerImage,legsIntermediateImage,legsAdvancedImage;
    private ImageView armsBeginnerImage,armsIntermediateImage,armsAdvancedImage;
    private ImageView chestBeginnerImage,chestIntermediateImage,chestAdvancedImage;
    private ImageView absBeginnerImage,absIntermediateImage,absAdvancedImage;
    private Button legsBeginnerBtn,legsIntermediateBtn,legsAdvancedBtn;
    private Button armsBeginnerBtn,armsIntermediateBtn,armsAdvancedBtn;
    private Button chestBeginnerBtn,chestIntermediateBtn,chestAdvancedBtn;
    private Button absBeginnerBtn,absIntermediateBtn,absAdvancedBtn;

    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.log_out).setVisible(false);
        menu.findItem(R.id.refresh).setVisible(false);
        //menu.clear();
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);


        getCustomersData();
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        setViews(root);
        chooseImagesByGender();
        setButtonListeners(root);

        return root;
    }

    private void getCustomersData(){
        SharedPreferences preferences = getContext().getSharedPreferences("customers_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        customer_id = preferences.getInt("customer_id",0);
        gender = preferences.getString("gender","Male");
        //Toast.makeText(getActivity(),"to customers_id "+customer_id,Toast.LENGTH_SHORT).show();
    }


    public void setViews(View root){

        legsBeginnerImage = root.findViewById(R.id.beginner_legs_image);
        legsIntermediateImage = root.findViewById(R.id.intermediate_legs_image);
        legsAdvancedImage = root.findViewById(R.id.advanced_legs_image);
        legsBeginnerBtn = root.findViewById(R.id.beginner_legs_btn);
        legsIntermediateBtn = root.findViewById(R.id.intermediate_legs_btn);
        legsAdvancedBtn = root.findViewById(R.id.advanced_legs_btn);

        armsBeginnerImage = root.findViewById(R.id.beginner_arms_image);
        armsIntermediateImage = root.findViewById(R.id.intermediate_arms_image);
        armsAdvancedImage = root.findViewById(R.id.advanced_arms_image);
        armsBeginnerBtn = root.findViewById(R.id.beginner_arms_btn);
        armsIntermediateBtn = root.findViewById(R.id.intermediate_arms_btn);
        armsAdvancedBtn = root.findViewById(R.id.advanced_arms_btn);

        chestBeginnerImage = root.findViewById(R.id.beginner_chest_image);
        chestIntermediateImage = root.findViewById(R.id.intermediate_chest_image);
        chestAdvancedImage = root.findViewById(R.id.advanced_chest_image);
        chestBeginnerBtn = root.findViewById(R.id.beginner_chest_btn);
        chestIntermediateBtn = root.findViewById(R.id.intermediate_chest_btn);
        chestAdvancedBtn = root.findViewById(R.id.advanced_chest_btn);


        absBeginnerImage = root.findViewById(R.id.beginner_abs_image);
        absIntermediateImage = root.findViewById(R.id.intermediate_abs_image);
        absAdvancedImage = root.findViewById(R.id.advanced_abs_image);
        absBeginnerBtn = root.findViewById(R.id.beginner_abs_btn);
        absIntermediateBtn = root.findViewById(R.id.intermediate_abs_btn);
        absAdvancedBtn = root.findViewById(R.id.advanced_abs_btn);

    }


    public void chooseImagesByGender(){
        if(gender.equals("Female")){

            legsBeginnerImage.setImageDrawable(getResources().getDrawable(R.drawable.female_legs_beginner));
            legsIntermediateImage.setImageDrawable(getResources().getDrawable(R.drawable.female_legs_intermediate));
            legsAdvancedImage.setImageDrawable(getResources().getDrawable(R.drawable.female_legs_advanced));

            armsBeginnerImage.setImageDrawable(getResources().getDrawable(R.drawable.female_arms_beginner));
            armsIntermediateImage.setImageDrawable(getResources().getDrawable(R.drawable.female_arms_intermediate));
            armsAdvancedImage.setImageDrawable(getResources().getDrawable(R.drawable.female_arms_advanced));

            chestBeginnerImage.setImageDrawable(getResources().getDrawable(R.drawable.female_chest_beginner));
            chestIntermediateImage.setImageDrawable(getResources().getDrawable(R.drawable.female_chest_intermediate));
            chestAdvancedImage.setImageDrawable(getResources().getDrawable(R.drawable.female_chest_advanced));

            absBeginnerImage.setImageDrawable(getResources().getDrawable(R.drawable.female_abs_beginner));
            absIntermediateImage.setImageDrawable(getResources().getDrawable(R.drawable.female_abs_intermediate));
            absAdvancedImage.setImageDrawable(getResources().getDrawable(R.drawable.female_abs_advanced));

        }

    }


    public void setButtonListeners(View root){
        legsBeginnerBtn = root.findViewById(R.id.beginner_legs_btn);
        legsIntermediateBtn = root.findViewById(R.id.intermediate_legs_btn);
        legsAdvancedBtn = root.findViewById(R.id.advanced_legs_btn);
        armsBeginnerBtn = root.findViewById(R.id.beginner_arms_btn);
        armsIntermediateBtn = root.findViewById(R.id.intermediate_arms_btn);
        armsAdvancedBtn = root.findViewById(R.id.advanced_arms_btn);
        chestBeginnerBtn = root.findViewById(R.id.beginner_chest_btn);
        chestIntermediateBtn = root.findViewById(R.id.intermediate_chest_btn);
        chestAdvancedBtn = root.findViewById(R.id.advanced_chest_btn);
        absBeginnerBtn = root.findViewById(R.id.beginner_abs_btn);
        absIntermediateBtn = root.findViewById(R.id.intermediate_abs_btn);
        absAdvancedBtn = root.findViewById(R.id.advanced_abs_btn);

        final Intent intent = new Intent(getActivity(), WorkOutActivity.class);

        legsBeginnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"mphka edw sta legs")
                intent.putExtra("program_title","Home: "+"Beginners legs");
                startActivity(intent);
            }
        });

        legsIntermediateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("program_title","Home: "+"Intermediate legs");
                startActivity(intent);
            }
        });

        legsAdvancedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("program_title","Home: "+"Advanced legs");
                startActivity(intent);
            }
        });


        armsBeginnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("program_title","Home: "+"Beginners Arms");
                startActivity(intent);
            }
        });

        armsIntermediateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("program_title","Home: "+"Intermediate Arms");
                startActivity(intent);
            }
        });

        armsAdvancedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("program_title","Home: "+"Advanced Arms");
                startActivity(intent);
            }
        });

        chestAdvancedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("program_title","Home: "+"Advanced Arms");
                startActivity(intent);
            }
        });

        chestBeginnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("program_title","Home: "+"Beginners Chest");
                startActivity(intent);
            }
        });

        chestIntermediateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("program_title","Home: "+"Intermediate Chest");
                startActivity(intent);
            }
        });

        chestAdvancedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("program_title","Home: "+"Advanced Chest");
                startActivity(intent);
            }
        });

        absBeginnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("program_title","Home: "+"Beginners Abs");
                startActivity(intent);
            }
        });

        absIntermediateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("program_title","Home: "+"Intermediate Abs");
                startActivity(intent);
            }
        });

        absAdvancedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("program_title","Home: "+"Advanced Abs");
                startActivity(intent);
            }
        });

    }

}
