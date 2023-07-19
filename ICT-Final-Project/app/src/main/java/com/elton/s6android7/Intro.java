package com.elton.s6android7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);


    }
    public void gotoHome(View view){
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }
}
