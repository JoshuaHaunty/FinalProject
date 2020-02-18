package com.bignerdranch.android.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Joshua on 4/30/2017.
 */

public class viewAttendenceActivity extends AppCompatActivity {

    static List<String> totalAbsentStudents = CardAdapter.totalAbsentStudents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_attendence);
        final Button bReset = (Button) findViewById(R.id.bReset);


        bReset.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                finish();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(viewAttendenceActivity.this);
                                builder.setMessage("Check Internet Connection")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                for (int i = 0; i < totalAbsentStudents.size(); i++) {
                    sendAttendenceRequest sendAttendenceRequest = new sendAttendenceRequest(totalAbsentStudents.get(i), "reset", responseListener);
                    RequestQueue queue = Volley.newRequestQueue(viewAttendenceActivity.this);
                    queue.add(sendAttendenceRequest);
                }
                totalAbsentStudents.clear();
                finish();
            }
        });

        Intent intent = getIntent();

        TextView attA1Text = (TextView) findViewById(R.id.att1A);
        TextView attP1Text = (TextView) findViewById(R.id.att1P);
        TextView attA2Text = (TextView) findViewById(R.id.att2A);
        TextView attP2Text = (TextView) findViewById(R.id.att2P);
        TextView attA3Text = (TextView) findViewById(R.id.att3A);
        TextView attP3Text = (TextView) findViewById(R.id.att3P);
        TextView tclass1 = (TextView) findViewById(R.id.tClass1);
        TextView tclass2 = (TextView) findViewById(R.id.tClass2);
        TextView tclass3 = (TextView) findViewById(R.id.tClass3);

        String attA1 = intent.getStringExtra("attA1");
        String attP1 = intent.getStringExtra("attP1");
        String attA2 = intent.getStringExtra("attA2");
        String attP2 = intent.getStringExtra("attP2");
        String attA3 = intent.getStringExtra("attA3");
        String attP3 = intent.getStringExtra("attP3");
        String class1 = intent.getStringExtra("class1");
        String class2 = intent.getStringExtra("class2");
        String class3 = intent.getStringExtra("class3");


        attA1Text.setText(attA1);
        attP1Text.setText(attP1);
        attA2Text.setText(attA2);
        attP2Text.setText(attP2);
        attA3Text.setText(attA3);
        attP3Text.setText(attP3);
        tclass1.setText(attP3);
        tclass2.setText(attP3);
        tclass3.setText(attP3);
        tclass1.setText(class1);
        tclass2.setText(class2);
        tclass3.setText(class3);

    }
}
