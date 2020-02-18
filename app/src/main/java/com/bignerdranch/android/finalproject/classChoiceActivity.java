package com.bignerdranch.android.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.M)
public class classChoiceActivity extends AppCompatActivity {

    private List<DataModel> listDataModels;
    private Context context;

    static List<String> absentStudents = CardAdapter.absentStudents;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RequestQueue requestQueue;
    public Button bSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);

        String whatClass = getIntent().getStringExtra("whatClass");


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        bSubmit = (Button) findViewById(R.id.bSubmit);

        //Initializing our superheroes list
        listDataModels = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);

        //Calling method to get data to fetch data
        getData(whatClass);

        //initializing our adapter
        adapter = new CardAdapter(listDataModels, this);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);

        bSubmit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                final String whatClass2 = intent.getStringExtra("whatClass");
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                //Toast.makeText(context, "One moment please", Toast.LENGTH_SHORT).show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(classChoiceActivity.this);
                                builder.setMessage("Check internet connection")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                for (int i = 0; i < absentStudents.size(); i++) {
                    sendAttendenceRequest sendAttendenceRequest = new sendAttendenceRequest(absentStudents.get(i), whatClass2, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(classChoiceActivity.this);
                    queue.add(sendAttendenceRequest);
                }
                absentStudents.clear();
                finish();
            }
        });
    }


    private JsonArrayRequest getDataFromServer(String whatClass) {
        //Initializing ProgressBar
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        //Displaying Progressbar
        progressBar.setVisibility(View.VISIBLE);
        setSupportProgressBarVisibility(true);

        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.DATA_URL + whatClass + ".php",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method parseData to parse the json response
                        parseData(response);
                        //Hiding the progressbar
                        progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        //If an error occurs that means end of the list has reached
                        Toast.makeText(classChoiceActivity.this, "No More Items Available", Toast.LENGTH_SHORT).show();
                    }
                });

        //Returning the request
        return jsonArrayRequest;
    }

    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            //Creating the superhero object
            DataModel dataModel = new DataModel();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the superhero object
                dataModel.setName(json.getString(Config.TAG_NAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the superhero object to the list
            listDataModels.add(dataModel);
        }

        //Notifying the adapter that data has been added or changed
        adapter.notifyDataSetChanged();
    }


    private void getData(String whatClass) {
        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getDataFromServer(whatClass));
    }
}