package com.elton.s6android7;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Home extends AppCompatActivity {

    private TextView welcome;

    private TextView apidata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        welcome = findViewById(R.id.welcomeText);
apidata=findViewById(R.id.hello);

String url = "http://192.168.56.1:8080/getLocations";


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    // Iterate over each location
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject locationObj = response.getJSONObject(i);

                        int locationId = locationObj.getInt("id");
                        String locationName = locationObj.getString("name");
                        String locationCity = locationObj.getString("city");
                        String locationDescription = locationObj.getString("description");

                        JSONArray categoriesArray = locationObj.getJSONArray("categories");

                        StringBuilder categoryBuilder = new StringBuilder();
                        categoryBuilder.append("Name: ").append(locationName).append("\n");
                        categoryBuilder.append("City: ").append(locationCity).append("\n");
                        categoryBuilder.append("Description: ").append(locationDescription).append("\n");

                        // Iterate over each category of the current location
                        for (int j = 0; j < categoriesArray.length(); j++) {
                            JSONObject categoryObj = categoriesArray.getJSONObject(j);

                            int categoryId = categoryObj.getInt("id");
                            String categoryName = categoryObj.getString("name");

                            // Append the category information to the StringBuilder
                            categoryBuilder.append("Category Name: ").append(categoryName).append("\n");
                        }
                        // Display the location and category information in the TextView
                        categoryBuilder.append("\n"); // Add some spacing between locations
                        apidata.append(categoryBuilder.toString());
                        }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                apidata.setText("Error Fetching! " + error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);


        Intent intent = getIntent();
        if (intent != null) {
            String username = intent.getStringExtra("username");
            String country = intent.getStringExtra("country");
            String email = intent.getStringExtra("email");

            welcome.setText("Welcome " + username);
        }

}
public void gotoProfile(View view){
        Intent intent2 = new Intent(this,Profile.class);
    Intent intent = getIntent();

    if (intent != null) {
        String username = intent.getStringExtra("username");
        String country = intent.getStringExtra("country");
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");

        welcome.setText("Welcome " + username);
    intent2.putExtra("username", username);
    intent2.putExtra("country", country);
    intent2.putExtra("email", email);
        intent2.putExtra("password", password);

    }
    startActivity(intent2);
}

}
