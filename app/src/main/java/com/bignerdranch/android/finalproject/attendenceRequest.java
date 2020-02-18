package com.bignerdranch.android.finalproject;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joshua on 4/29/2017.
 */

public class attendenceRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://jkh874440project.000webhostapp.com/attendence.php";
    private Map<String, String> params;
    int count = 0;

    public attendenceRequest(String username, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
