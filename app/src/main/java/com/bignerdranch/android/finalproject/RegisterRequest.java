package com.bignerdranch.android.finalproject;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joshua on 4/4/2017.
 */

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://jkh874440project.000webhostapp.com/register2.php";
    private Map<String, String> params;

    public RegisterRequest(String name, String username, String password, String class1, String class2, String class3, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("username", username);
        params.put("password", password);
        params.put("class1", class1);
        params.put("class2", class2);
        params.put("class3", class3);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
