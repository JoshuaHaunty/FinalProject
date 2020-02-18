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

import java.util.Collections;
import java.util.List;

public class UserAreaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        final TextView etName = (TextView) findViewById(R.id.etName);
        final Button bClass1 = (Button) findViewById(R.id.bClass1);
        final Button bClass2 = (Button) findViewById(R.id.bClass2);
        final Button bClass3 = (Button) findViewById(R.id.bClass3);
        final Button bViewAtt = (Button) findViewById(R.id.bViewAtt);
        final Button bReset = (Button) findViewById(R.id.bReset);

        bClass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent class1Intent = new Intent(UserAreaActivity.this, classChoiceActivity.class);
                String whatClass = "1";
                class1Intent.putExtra("whatClass", whatClass);
                UserAreaActivity.this.startActivity(class1Intent);
            }
        });

        bClass2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent class2Intent = new Intent(UserAreaActivity.this, classChoiceActivity.class);
                String whatClass = "2";
                class2Intent.putExtra("whatClass", whatClass);
                UserAreaActivity.this.startActivity(class2Intent);
            }
        });

        bClass3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent class3Intent = new Intent(UserAreaActivity.this, classChoiceActivity.class);
                String whatClass = "3";
                class3Intent.putExtra("whatClass", whatClass);
                UserAreaActivity.this.startActivity(class3Intent);
            }
        });

        bViewAtt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent2 = getIntent();
                final String name = intent2.getStringExtra("name");
                final String class1 = intent2.getStringExtra("class1");
                final String class2 = intent2.getStringExtra("class2");
                final String class3 = intent2.getStringExtra("class3");
                final String username = intent2.getStringExtra("username");
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                String att1A = jsonResponse.getString("attA1");
                                String att1P = jsonResponse.getString("attP1");
                                String att2A = jsonResponse.getString("attA2");
                                String att2P = jsonResponse.getString("attP2");
                                String att3A = jsonResponse.getString("attA3");
                                String att3P = jsonResponse.getString("attP3");

                                Intent intent1 = new Intent(UserAreaActivity.this, viewAttendenceActivity.class);
                                intent1.putExtra("name", name);
                                intent1.putExtra("class1", class1);
                                intent1.putExtra("class2", class2);
                                intent1.putExtra("class3", class3);
                                intent1.putExtra("attA1", att1A);
                                intent1.putExtra("attP1", att1P);
                                intent1.putExtra("attA2", att2A);
                                intent1.putExtra("attP2", att2P);
                                intent1.putExtra("attA3", att3A);
                                intent1.putExtra("attP3", att3P);

                                UserAreaActivity.this.startActivity(intent1);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(UserAreaActivity.this);
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
                attendenceRequest  attendenceRequest = new attendenceRequest(username, responseListener);
                RequestQueue queue = Volley.newRequestQueue(UserAreaActivity.this);
                queue.add(attendenceRequest);
            }
        });

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String class1 = intent.getStringExtra("class1");
        String class2 = intent.getStringExtra("class2");
        String class3 = intent.getStringExtra("class3");

        etName.setText(name);
        bClass1.setText(class1);
        bClass2.setText(class2);
        bClass3.setText(class3);
    }
}
