package com.elton.s6android7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Profile extends AppCompatActivity {
private TextView username;
    private TextView email;
    private TextView country;
    private TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        username=findViewById(R.id.usernameP);
        email=findViewById(R.id.emailP);
        country=findViewById(R.id.countryP);
        password=findViewById(R.id.passwordP);


        Intent intent = getIntent();
        if (intent != null) {
            String username2 = intent.getStringExtra("username");
            String country2 = intent.getStringExtra("country");
            String email2 = intent.getStringExtra("email");
            String password2 = intent.getStringExtra("password");

            username.setText( username2);
            email.setText( email2);
            country.setText( country2);
            password.setText( password2);

        }
    }

}