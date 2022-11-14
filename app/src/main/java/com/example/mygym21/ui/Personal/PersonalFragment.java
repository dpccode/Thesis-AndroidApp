package com.example.mygym21.ui.Personal;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mygym21.R;
import com.example.mygym21.WorkOuts.WorkOutActivity;
import com.example.mygym21.ui.dashboard.DashboardFragment;
import com.example.mygym21.ui.notifications.NotificationsViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class PersonalFragment extends Fragment {

    private LinearLayout layoutList;
    private JSONArray mondayJsonArray = new JSONArray();
    private JSONArray tuesdayJsonArray = new JSONArray();
    private JSONArray wednesdayJsonArray = new JSONArray();
    private JSONArray thursdayJsonArray = new JSONArray();
    private JSONArray fridayJsonArray = new JSONArray();
    private JSONArray saturdayJsonArray = new JSONArray();
    private JSONArray sundayJsonArray = new JSONArray();
    private TextView mondayWorkOutTitle,mondayWorkOutTotExercises;
    private TextView tuesdayWorkOutTitle,tuesdayWorkOutTotExercises;
    private TextView wednesdayWorkOutTitle,wednesdayWorkOutTotExercises;
    private TextView thursdayWorkOutTitle,thursdayWorkOutTotExercises;
    private TextView fridayWorkOutTitle,fridayWorkOutTotExercises;
    private TextView saturdayWorkOutTitle,saturdayWorkOutTotExercises;
    private TextView sundayWorkOutTitle,sundayWorkOutTotExercises;
    private TextView locked_text;
    private CardView mondayCardView,tuesdayCardView,wednesdayCardView,thursdayCardView,fridayCardView,saturdayCardView,sundayCardView;
    private Button mondayBtn,tuesdayBtn,wednesdayBtn,thursdayBtn,fridayBtn,saturdayBtn,sundayBtn;
    private ImageView mondayImage,tuesdayImage,wednesdayImage,thursdayImage,fridayImage,saturdayImage,sundayImage;
    private int customer_id;
    private String subscription_type,gender;
    private Boolean refreshBtnClicked = false;


    public void onPrepareOptionsMenu(Menu menu) {
        if(subscription_type.equals("BASIC") || subscription_type.equals("MID")) {
            menu.findItem(R.id.refresh).setVisible(false);
        }
        menu.findItem(R.id.log_out).setVisible(false);
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                refreshBtnClicked = true;
                getWorkoutPrograms();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        getCustomersData();
        refreshBtnClicked = false;
        if(subscription_type.equals("BASIC") || subscription_type.equals("MID")){
            super.onCreate(savedInstanceState);
            View root = inflater.inflate(R.layout.locked_layout, container, false);
            locked_text = root.findViewById(R.id.locked_text);
            locked_text.setText("In order to get access here buy  <<Premium>>  package");
            setHasOptionsMenu(true);
            return root;
        }else {

            View root = inflater.inflate(R.layout.fragment_personal, container, false);
            setHasOptionsMenu(true);
            super.onCreate(savedInstanceState);


            getWorkoutPrograms();
            setViews(root);
            mondayBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), WorkOutActivity.class);
                    try {
                        intent.putExtra("program_title", "Personal: " + mondayJsonArray.getJSONObject(1).getString("p_title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    intent.putExtra("program_exercises", String.valueOf(mondayJsonArray));
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
            tuesdayBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), WorkOutActivity.class);
                    try {
                        intent.putExtra("program_title", "Personal: " + tuesdayJsonArray.getJSONObject(1).getString("p_title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    intent.putExtra("program_exercises", String.valueOf(tuesdayJsonArray));
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
            });
            wednesdayBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), WorkOutActivity.class);
                    try {
                        intent.putExtra("program_title", "Personal: " + wednesdayJsonArray.getJSONObject(1).getString("p_title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    intent.putExtra("program_exercises", String.valueOf(wednesdayJsonArray));
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
            thursdayBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), WorkOutActivity.class);
                    try {
                        intent.putExtra("program_title", "Personal: " + thursdayJsonArray.getJSONObject(1).getString("p_title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    intent.putExtra("program_exercises", String.valueOf(thursdayJsonArray));
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
            fridayBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), WorkOutActivity.class);
                    try {
                        intent.putExtra("program_title", "Personal: " + fridayJsonArray.getJSONObject(1).getString("p_title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    intent.putExtra("program_exercises", String.valueOf(fridayJsonArray));
                    startActivity(intent);
                }
            });
            saturdayBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), WorkOutActivity.class);
                    try {
                        intent.putExtra("program_title", "Personal: " + saturdayJsonArray.getJSONObject(1).getString("p_title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    intent.putExtra("program_exercises", String.valueOf(saturdayJsonArray));
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
            sundayBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), WorkOutActivity.class);
                    try {
                        intent.putExtra("program_title", "Personal: " + sundayJsonArray.getJSONObject(1).getString("p_title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    intent.putExtra("program_exercises", String.valueOf(sundayJsonArray));
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });

            return root;
        }
    }



    public void getWorkoutPrograms(){
        RequestQueue rq;
        rq = Volley.newRequestQueue(getActivity());
        String url = "http://192.168.1.5:80/api/api/workouts/read.php?customers_id="+customer_id;

        ProgressDialog dialog = null;
        if(refreshBtnClicked){
            dialog = ProgressDialog.show(getActivity(), "",
                    "Loading. Please wait...", true);
        }


        final ProgressDialog finalDialog = dialog;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray= new JSONArray(response);
                    if(refreshBtnClicked) {
                        finalDialog.dismiss();
                    }
                    SharedPreferences preferences = getActivity().getSharedPreferences("customers_data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("personal_exercises",response).commit();

                    setPrograms(jsonArray);
                } catch (JSONException e) {
                    Toast.makeText(getContext(),"mpainei sto catch "+e,Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(refreshBtnClicked) {
                    finalDialog.dismiss();
                    Toast.makeText(getContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                }

                SharedPreferences preferences = getActivity().getSharedPreferences("customers_data", Context.MODE_PRIVATE);
                String p_exercises = preferences.getString("personal_exercises",null);
                if(p_exercises != null){
                    try {
                        Toast.makeText(getContext(),"mpainei edw",Toast.LENGTH_LONG).show();
                        JSONArray jsonArray= new JSONArray(p_exercises);
                        setPrograms(jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        rq.add(stringRequest);

    }

    private void setPrograms(JSONArray jsonArray) throws JSONException {

        mondayJsonArray = new JSONArray();
        tuesdayJsonArray = new JSONArray();
        wednesdayJsonArray = new JSONArray();
        thursdayJsonArray = new JSONArray();
        fridayJsonArray = new JSONArray();
        saturdayJsonArray = new JSONArray();
        sundayJsonArray = new JSONArray();


        for(int i = 0;i<jsonArray.length();i++) {
            try {
                JSONObject programs_jsonObject = jsonArray.getJSONObject(i);

                switch (programs_jsonObject.getString("day")) {
                    case "Monday":
                        mondayJsonArray.put(mondayJsonArray.length(),programs_jsonObject);
                        break;
                    case "Tuesday":
                        tuesdayJsonArray.put(tuesdayJsonArray.length(),programs_jsonObject);
                        break;
                    case "Wednesday":
                        wednesdayJsonArray.put(wednesdayJsonArray.length(),programs_jsonObject);
                        break;
                    case "Thursday":
                        thursdayJsonArray.put(thursdayJsonArray.length(),programs_jsonObject);
                        break;
                    case "Friday":
                        fridayJsonArray.put(fridayJsonArray.length(),programs_jsonObject);
                        break;
                    case "Saturday":
                        saturdayJsonArray.put(saturdayJsonArray.length(),programs_jsonObject);
                        break;
                    case "Sunday":
                        sundayJsonArray.put(sundayJsonArray.length(),programs_jsonObject);
                        break;
                }
            } catch (JSONException e) {
                Toast.makeText(getContext(), "Error "+e, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }


        if(mondayJsonArray.length() > 0){

            mondayWorkOutTitle.setText(mondayJsonArray.getJSONObject(1).getString("p_title"));
            mondayWorkOutTotExercises.setText(String.valueOf(mondayJsonArray.length())+" workouts");
            mondayCardView.setVisibility(View.VISIBLE);
            chooseImageByGenderAndType(mondayImage,mondayJsonArray.getJSONObject(1).getString("category"));

        }else{
            mondayCardView.setVisibility(View.GONE);
        }

        if(tuesdayJsonArray.length() > 0){

            tuesdayWorkOutTitle.setText(tuesdayJsonArray.getJSONObject(1).getString("p_title"));
            tuesdayWorkOutTotExercises.setText(String.valueOf(tuesdayJsonArray.length())+" workouts");
            tuesdayCardView.setVisibility(View.VISIBLE);
            chooseImageByGenderAndType(tuesdayImage,tuesdayJsonArray.getJSONObject(1).getString("category"));

        }else{
            tuesdayCardView.setVisibility(View.GONE);
        }

        if(wednesdayJsonArray.length() > 0){

            wednesdayWorkOutTitle.setText(wednesdayJsonArray.getJSONObject(1).getString("p_title"));
            wednesdayWorkOutTotExercises.setText(String.valueOf(wednesdayJsonArray.length())+" workouts");
            wednesdayCardView.setVisibility(View.VISIBLE);
            chooseImageByGenderAndType(wednesdayImage,wednesdayJsonArray.getJSONObject(1).getString("category"));

        }else{
            wednesdayCardView.setVisibility(View.GONE);
        }

        if(thursdayJsonArray.length() > 0){

            thursdayWorkOutTitle.setText(thursdayJsonArray.getJSONObject(1).getString("p_title"));
            thursdayWorkOutTotExercises.setText(String.valueOf(thursdayJsonArray.length())+" workouts");
            thursdayCardView.setVisibility(View.VISIBLE);
            chooseImageByGenderAndType(thursdayImage,thursdayJsonArray.getJSONObject(1).getString("category"));

        }else{
            thursdayCardView.setVisibility(View.GONE);
        }

        if(fridayJsonArray.length() > 0){

            fridayWorkOutTitle.setText(fridayJsonArray.getJSONObject(1).getString("p_title"));
            fridayWorkOutTotExercises.setText(String.valueOf(fridayJsonArray.length())+" workouts");
            fridayCardView.setVisibility(View.VISIBLE);
            chooseImageByGenderAndType(fridayImage,fridayJsonArray.getJSONObject(1).getString("category"));

        }else{
            fridayCardView.setVisibility(View.GONE);
        }

        if(saturdayJsonArray.length() > 0){

            saturdayWorkOutTitle.setText(saturdayJsonArray.getJSONObject(1).getString("p_title"));
            saturdayWorkOutTotExercises.setText(String.valueOf(saturdayJsonArray.length())+" workouts");
            saturdayCardView.setVisibility(View.VISIBLE);
            chooseImageByGenderAndType(saturdayImage,saturdayJsonArray.getJSONObject(1).getString("category"));

        }else{
            saturdayCardView.setVisibility(View.GONE);
        }

        if(sundayJsonArray.length() > 0){

            sundayWorkOutTitle.setText(sundayJsonArray.getJSONObject(1).getString("p_title"));
            sundayWorkOutTotExercises.setText(String.valueOf(sundayJsonArray.length())+" workouts");
            sundayCardView.setVisibility(View.VISIBLE);
            chooseImageByGenderAndType(sundayImage,sundayJsonArray.getJSONObject(1).getString("category"));

        }else{
            sundayCardView.setVisibility(View.GONE);
        }

    }

    private void setViews(View root){
        mondayWorkOutTitle = root.findViewById(R.id.mondays_personal_workout_title);
        mondayWorkOutTotExercises = root.findViewById(R.id.mondays_personal_workout_totworkouts);
        mondayCardView = root.findViewById(R.id.mondays_personal_workout);
        mondayBtn = root.findViewById(R.id.mondays_personal_workout_btn);
        mondayImage = root.findViewById(R.id.mondays_personal_workout_image);

        tuesdayWorkOutTitle = root.findViewById(R.id.tuesdays_personal_workout_title);
        tuesdayWorkOutTotExercises = root.findViewById(R.id.tuesdays_personal_workout_totworkouts);
        tuesdayCardView = root.findViewById(R.id.tuesdays_personal_workout);
        tuesdayBtn = root.findViewById(R.id.tuesdays_personal_workout_btn);
        tuesdayImage = root.findViewById(R.id.tuesdays_personal_workout_image);

        wednesdayWorkOutTitle = root.findViewById(R.id.wednesdays_personal_workout_title);
        wednesdayWorkOutTotExercises = root.findViewById(R.id.wednesdays_personal_workout_totworkouts);
        wednesdayCardView = root.findViewById(R.id.wednesdays_personal_workout);
        wednesdayBtn = root.findViewById(R.id.wednesdays_personal_workout_btn);
        wednesdayImage = root.findViewById(R.id.wednesdays_personal_workout_image);

        thursdayWorkOutTitle = root.findViewById(R.id.thursdays_personal_workout_title);
        thursdayWorkOutTotExercises = root.findViewById(R.id.thursdays_personal_workout_totworkouts);
        thursdayCardView = root.findViewById(R.id.thursdays_personal_workout);
        thursdayBtn = root.findViewById(R.id.thursdays_personal_workout_btn);
        thursdayImage = root.findViewById(R.id.thursdays_personal_workout_image);

        fridayWorkOutTitle = root.findViewById(R.id.fridays_personal_workout_title);
        fridayWorkOutTotExercises = root.findViewById(R.id.fridays_personal_workout_totworkouts);
        fridayCardView = root.findViewById(R.id.fridays_personal_workout);
        fridayBtn = root.findViewById(R.id.fridays_personal_workout_btn);
        fridayImage = root.findViewById(R.id.fridays_personal_workout_image);

        saturdayWorkOutTitle = root.findViewById(R.id.saturdays_personal_workout_title);
        saturdayWorkOutTotExercises = root.findViewById(R.id.saturdays_personal_workout_totworkouts);
        saturdayCardView = root.findViewById(R.id.saturdays_personal_workout);
        saturdayBtn = root.findViewById(R.id.saturdays_personal_workout_btn);
        saturdayImage = root.findViewById(R.id.saturdays_personal_workout_image);

        sundayWorkOutTitle = root.findViewById(R.id.sundays_personal_workout_title);
        sundayWorkOutTotExercises = root.findViewById(R.id.sundays_personal_workout_totworkouts);
        sundayCardView = root.findViewById(R.id.sundays_personal_workout);
        sundayBtn = root.findViewById(R.id.sundays_personal_workout_btn);
        sundayImage = root.findViewById(R.id.sundays_personal_workout_image);
    }



    public void chooseImageByGenderAndType(ImageView imageView,String program_type) {

        if (gender.equals("Male")) {
            switch (program_type) {
                case "Full Body":
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.male_full_body));
                    break;
                case "Legs":
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.legs3));
                    break;
                case "Chest":
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.male_chest_advanced));
                    break;
                case "Arms":
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.arms3));
                    break;
                case "Abs":
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.abs));
                    break;
                case "Shoulders":
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.male_chest_intermediate));
                    break;
                case "Back":
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.male_chest_beginner));
                    break;
            }
        } else {
            switch (program_type) {
                case "Full Body":
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.female_full_body));
                    break;
                case "Legs":
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.female_legs_advanced));
                    break;
                case "Chest":
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.female_chest_advanced));
                    break;
                case "Arms":
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.female_arms_intermediate));
                    break;
                case "Abs":
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.female_abs_advanced));
                    break;
                case "Shoulders":
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.female_chest_advanced));
                    break;
                case "Back":
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.female_chest_intermediate));
                    break;
            }
        }

    }


    private void getCustomersData(){

        SharedPreferences preferences = getActivity().getSharedPreferences("customers_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        customer_id = preferences.getInt("customer_id",0);
        gender = preferences.getString("gender","Male");
        subscription_type = preferences.getString("subscription_type","BASIC");

    }

}
