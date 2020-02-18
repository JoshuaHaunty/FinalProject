package com.bignerdranch.android.finalproject;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joshua on 5/1/2017.
 */

public class sendAttendenceRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://jkh874440project.000webhostapp.com/takeAttendence.php";
    private Map<String, String> params;

    public sendAttendenceRequest(String name, String whatClass2, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("class", whatClass2);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}