package com.example.mygym21.ui.dashboard;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mygym21.LoginActivity;
import com.example.mygym21.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DashboardFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private DashboardViewModel dashboardViewModel;
    private View resView ;
    private View root;
    //Button button;
    private LinearLayout layoutList;
    private LinearLayout monday_ll;
    private LinearLayout tuesday_ll;
    private LinearLayout wednesday_ll;
    private LinearLayout thursday_ll;
    private LinearLayout friday_ll;
    private LinearLayout saturday_ll;
    private LinearLayout sunday_ll;
    private int customer_id;
    private String subscription_type,theme;
    private TextView locked_text;
    private Boolean refreshBtnClicked = false;
    JSONArray mResponse = new JSONArray();

    //in order to hide menu
    public void onPrepareOptionsMenu(Menu menu) {
        if(subscription_type.equals("BASIC")) {
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
                getOpenGroups();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        getCustomersData();
        getThemePref();
        refreshBtnClicked = false;
        if(subscription_type.equals("BASIC")){
            super.onCreate(savedInstanceState);
            View root = inflater.inflate(R.layout.locked_layout, container, false);
            locked_text = root.findViewById(R.id.locked_text);
            locked_text.setText("In order to get access here buy  <<Medium>>  or  <<Premium>>  package");
            setHasOptionsMenu(true);
            return root;
        }else {

            View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

            setHasOptionsMenu(true);
            layoutList = root.findViewById(R.id.reservation_layout);

            Spinner spinner = root.findViewById(R.id.days_spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.days, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(DashboardFragment.this);

            monday_ll = new LinearLayout(getActivity());
            monday_ll.setOrientation(LinearLayout.VERTICAL);

            tuesday_ll = new LinearLayout(getActivity());
            tuesday_ll.setOrientation(LinearLayout.VERTICAL);

            wednesday_ll = new LinearLayout(getActivity());
            wednesday_ll.setOrientation(LinearLayout.VERTICAL);

            thursday_ll = new LinearLayout(getActivity());
            thursday_ll.setOrientation(LinearLayout.VERTICAL);

            friday_ll = new LinearLayout(getActivity());
            friday_ll.setOrientation(LinearLayout.VERTICAL);

            saturday_ll = new LinearLayout(getActivity());
            saturday_ll.setOrientation(LinearLayout.VERTICAL);

            sunday_ll = new LinearLayout(getActivity());
            sunday_ll.setOrientation(LinearLayout.VERTICAL);

            getCustomersData();
            getOpenGroups();

            return root;
        }


    }


    public void getOpenGroups(){
        RequestQueue rq;
        rq = Volley.newRequestQueue(getActivity());
        String url = "http://192.168.1.5:80/api/api/groups/read.php?customers_id="+customer_id;

        ProgressDialog dialog = null;
        if(refreshBtnClicked){
           dialog = ProgressDialog.show(getActivity(), "","Loading. Please wait...", true);
        }

        final ProgressDialog finalDialog = dialog;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray= new JSONArray(response);
                    if(refreshBtnClicked)
                        finalDialog.dismiss();
                    createResViews(jsonArray);

                } catch (JSONException e) {
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
            }
        });

        rq.add(stringRequest);
    }


    public void makeReservation(final int res_id, final int cust_id){
        RequestQueue rq;
        rq = Volley.newRequestQueue(getActivity());
        String url = "http://192.168.1.5:80/api/api/reservation/create.php";

        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                    if(response.equals(""))
                        Toast.makeText(getActivity(),"This Group is full !!"+response,Toast.LENGTH_LONG).show();
                    else if(response.equals("You are already in this group"))
                        Toast.makeText(getActivity(),"You are already in this group",Toast.LENGTH_LONG).show();

                    getOpenGroups();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // na valw dialog
                AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
                dialog.setTitle("Requests status");
                dialog.setMessage("Something went wrong!\nCheck your internet connection and try again later!");
                dialog.setButton(Dialog.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.teal));
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("reservations_id",String.valueOf(res_id));
                params.put("customers_id",String.valueOf(cust_id));
                return params;
            }

        };

        rq.add(stringRequest);
    }


    public void cancelReservation(final int res_id, final int cust_id){
        RequestQueue rq;
        rq = Volley.newRequestQueue(getActivity());
        String url = "http://192.168.1.5:80/api/api/reservation/delete.php";

        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getOpenGroups();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
                dialog.setTitle("Requests status");
                dialog.setMessage("Something went wrong!\nCheck your internet connection and try again later!");
                dialog.setButton(Dialog.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.teal));
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("reservations_id",String.valueOf(res_id));
                params.put("customers_id",String.valueOf(cust_id));
                return params;
            }
        };

        rq.add(stringRequest);
    }


    private void createResViews(JSONArray response){

        monday_ll.removeAllViews();
        tuesday_ll.removeAllViews();
        wednesday_ll.removeAllViews();
        thursday_ll.removeAllViews();
        friday_ll.removeAllViews();
        saturday_ll.removeAllViews();
        sunday_ll.removeAllViews();

        for(int i = 0;i<response.length();i++){
            try {
                JSONObject reservations_jsonObject = response.getJSONObject(i);
                resView = getLayoutInflater().inflate(R.layout.krathsh,null,false);
                int res_customers_id = reservations_jsonObject.getInt("customers_id");

                TextView title = resView.findViewById(R.id.res_title);
                TextView total_customers = resView.findViewById(R.id.res_people);
                TextView res_dateView = resView.findViewById(R.id.res_date);
                final ImageButton makeResBtn = resView.findViewById(R.id.make_resBtn);
                ImageButton cancelResBtn = resView.findViewById(R.id.cancel_resBtn);
                final int finalId = reservations_jsonObject.getInt("id");

                if(res_customers_id == customer_id){
                    makeResBtn.setClickable(false);
                    cancelResBtn.setClickable(true);
                    makeResBtn.setImageResource(R.drawable.check11);
                    cancelResBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
                            dialog.setTitle("Are you sure?");
                            dialog.setMessage("Do you want to cancel this reservation?");

                            dialog.setButton(Dialog.BUTTON_POSITIVE,"Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    cancelReservation(finalId,customer_id);
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

                        }
                    });

                }else{


                    makeResBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            makeReservation(finalId,customer_id);
                        }
                    });
                }

                title.setText(reservations_jsonObject.getString("title"));
                total_customers.setText(reservations_jsonObject.getString("current_persons")+"/"+reservations_jsonObject.getString("max_persons"));

                switch(reservations_jsonObject.getInt("day")) {
                    case 0:
                        res_dateView.setText("Monday"+" "+reservations_jsonObject.get("time").toString());
                        monday_ll.addView(resView);
                        break;
                    case 1:
                        res_dateView.setText("Tuesday"+" "+reservations_jsonObject.get("time").toString());
                        tuesday_ll.addView(resView);
                        break;
                    case 2:
                        res_dateView.setText("Wednesday"+" "+reservations_jsonObject.get("time").toString());
                        wednesday_ll.addView(resView);
                        break;
                    case 3:
                        res_dateView.setText("Thursday"+" "+reservations_jsonObject.get("time").toString());
                        thursday_ll.addView(resView);
                        break;
                    case 4:
                        res_dateView.setText("Friday"+" "+reservations_jsonObject.get("time").toString());
                        friday_ll.addView(resView);
                        break;
                    case 5:
                        res_dateView.setText("Saturday"+" "+reservations_jsonObject.get("time").toString());
                        saturday_ll.addView(resView);
                        break;
                    case 6:
                        res_dateView.setText("Sunday"+" "+reservations_jsonObject.get("time").toString());
                        sunday_ll.addView(resView);
                        break;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(theme.equals("Light"))
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
        else
            ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);

        layoutList.removeAllViews();

        switch(position) {
            case 0:
                if(monday_ll.getChildCount() > 0){
                    layoutList.addView(monday_ll);
                }

                break;
            case 1:
                if(tuesday_ll.getChildCount() > 0) {
                    layoutList.addView(tuesday_ll);
                }
                break;
            case 2:
                if(wednesday_ll.getChildCount() > 0){
                    layoutList.addView(wednesday_ll);
                }

                break;
            case 3:
                if(thursday_ll.getChildCount() > 0){
                    //layoutList.removeAllViews();
                    layoutList.addView(thursday_ll);
                }

                break;
            case 4:
                if(friday_ll.getChildCount() > 0){
                    layoutList.addView(friday_ll);
                }
                break;
            case 5:
                if(saturday_ll.getChildCount() > 0) {
                    layoutList.addView(saturday_ll);
                }
                break;
            case 6:
                if(sunday_ll.getChildCount() > 0){
                    layoutList.addView(sunday_ll);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getCustomersData(){
        SharedPreferences preferences = getContext().getSharedPreferences("customers_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        customer_id = preferences.getInt("customer_id",0);
        subscription_type = preferences.getString("subscription_type","BASIC");
    }

    public  void getThemePref(){
        SharedPreferences preferences = getContext().getSharedPreferences(String.valueOf(customer_id)+"themePref", Context.MODE_PRIVATE);
        theme = preferences.getString("theme","Light");
    }

}
