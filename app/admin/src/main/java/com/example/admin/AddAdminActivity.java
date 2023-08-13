package com.example.admin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AddAdminActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_admin);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView backNav = findViewById(R.id.back_nav);

        backNav.setOnClickListener(e->{
            finish();
        });
    }
}