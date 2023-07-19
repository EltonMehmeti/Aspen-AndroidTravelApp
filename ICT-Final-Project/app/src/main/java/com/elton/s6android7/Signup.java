package com.elton.s6android7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
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

public class Signup extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText countryEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        // Initialize EditText views
        usernameEditText = findViewById(R.id.username);
        countryEditText = findViewById(R.id.country);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);

    }
    public void signup(View view) {
        final String username = usernameEditText.getText().toString();
        final String country = countryEditText.getText().toString();
        final String email = emailEditText.getText().toString();
        final String password = passwordEditText.getText().toString();

        // Perform signup logic here
        // You can make the API request to your backend or perform any validation

        // Example: Log the signup details
        String signupDetails = "Username: " + username +
                "\nCountry: " + country +
                "\nEmail: " + email +
                "\nPassword: " + password;
        System.out.println(signupDetails);

        // Create a JSON object with the signup data
        JSONObject signupData = new JSONObject();
        try {
            signupData.put("username", username);
            signupData.put("country", country);
            signupData.put("email", email);
            signupData.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Make the API request using Volley
        String url = "http://192.168.56.1:8080/signup";
        // Replace with your API endpoint
// Specify the timeout duration in milliseconds
        int timeoutDuration = 30000; // 30 seconds

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, signupData,
                response -> {
                    if(response.has("success")){

                        try {
                            String message = response.getString("message");
                        Toast.makeText(Signup.this, message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Signup.this, Signin.class);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }


                },
                error -> {
                    // Handle error response
                    String errorMessage;
                    if (error instanceof NetworkError) {
                        errorMessage = "Network error. Please check your internet connection.";
                    } else if (error instanceof ServerError) {
                        errorMessage = "Server error. Please try again later.";
                    } else if (error instanceof AuthFailureError) {
                        errorMessage = "Authentication failure. Please check your credentials.";
                    } else if (error instanceof ParseError) {
                        errorMessage = "Error parsing response. Please try again.";
                    } else if (error instanceof TimeoutError) {
                        errorMessage = "Request timeout. Please try again later.";
                    } else {
                        errorMessage = "Unknown error occurred.";
                    }

                    // Show the error message
                    Toast.makeText(Signup.this, errorMessage, Toast.LENGTH_SHORT).show();

                    // Retrieve the response data if available
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        try {
                            String responseString = new String(error.networkResponse.data, "UTF-8");
                            // Display the response in the error message
                            Toast.makeText(Signup.this, responseString, Toast.LENGTH_SHORT).show();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                });

// Set the timeout duration for the request
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeoutDuration,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

// Add the request to the request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

    }
    public void gotoSignin (View view){
        Intent intent= new Intent(this,Signin.class);
        startActivity(intent);
    }
}