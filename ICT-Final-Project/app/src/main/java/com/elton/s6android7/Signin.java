package com.elton.s6android7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Signin extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        emailEditText = findViewById(R.id.email);
 passwordEditText=findViewById(R.id.password);
    }
    public void goToSignup(View view) {
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }

    public void loginBtn(View view) {
        final String email = emailEditText.getText().toString();
        final String password = passwordEditText.getText().toString();

        // Check for empty fields
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(Signin.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a JSON object with the login data
        JSONObject loginData = new JSONObject();
        try {
            loginData.put("email", email);
            loginData.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(Signin.this, "Error creating JSON object", Toast.LENGTH_SHORT).show();
            return;
        }

        // Make the API request using Volley
        String url = "http://192.168.56.1:8080/login"; // Replace with your server's URL
        int timeoutDuration = 30000; // 30 seconds

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, loginData,
                response -> {
                    try {
                        if (response.has("message") && response.has("user")) {
                            String message = response.getString("message");

                            Toast.makeText(Signin.this, message, Toast.LENGTH_SHORT).show();

                            // Extract the user information from the response
                            JSONObject userJson = response.getJSONObject("user");
                            String username = userJson.getString("username");
                            String country = userJson.getString("country");
                            String emailt = userJson.getString("email");
                            String passwordt = userJson.getString("password");

                            // Get other user properties as needed

                            // Proceed with the appropriate action after successful login
                            Intent intent = new Intent(Signin.this, Home.class);
                            intent.putExtra("username", username);
                            intent.putExtra("country", country);
                            intent.putExtra("email", emailt);
                            intent.putExtra("password", passwordt);

                            // Add other user properties to the intent extras

                            startActivity(intent);
                        } else {
                            Toast.makeText(Signin.this, "Invalid response format", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(Signin.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {

                    // Handle error response
                    String errorMessage = "Unknown error occurred.";
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        try {
                            String errorResponse = new String(error.networkResponse.data, "UTF-8");
                            Log.e("Error Response", errorResponse); // Add this line to log the error response
                            errorMessage = "Error response: " + errorResponse;
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    } else if (error instanceof NetworkError) {
                        errorMessage = "Network error. Please check your internet connection.";
                    } else if (error instanceof ServerError) {
                        errorMessage = "Server error. Please try again later.";
                    } else if (error instanceof AuthFailureError) {
                        errorMessage = "Authentication failure. Please check your credentials.";
                    } else if (error instanceof ParseError) {
                        errorMessage = "Error parsing response. Please try again.";
                    } else if (error instanceof NoConnectionError) {
                        errorMessage = "No internet connection. Please check your network settings.";
                    } else if (error instanceof TimeoutError) {
                        errorMessage = "Request timeout. Please try again later.";
                    }
                    Toast.makeText(Signin.this, errorMessage, Toast.LENGTH_SHORT).show();
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeoutDuration,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }}