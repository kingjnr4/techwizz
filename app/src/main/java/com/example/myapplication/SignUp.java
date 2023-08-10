package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        View signInLink = findViewById(R.id.signUpLink);
        signInLink.setOnClickListener(v -> {

            Intent intent = new Intent(SignUp.this, SignIn.class);
            startActivity(intent);
        });
    }
}
