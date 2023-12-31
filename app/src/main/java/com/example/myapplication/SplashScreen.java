package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        mAuth=FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (isLoggedIn()){
                    intent= new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
                intent= new Intent(SplashScreen.this,SignUp.class);
                startActivity(intent);
                finish();
            }},2000);


    }

    public boolean isLoggedIn(){
        FirebaseUser user = mAuth.getCurrentUser();
        return user != null;
    }
}