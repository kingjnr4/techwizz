package com.example.admin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class AdminActivity extends AppCompatActivity {

    RecyclerView adminRecyclerview;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView backNav = findViewById(R.id.back_nav);

        adminRecyclerview = findViewById(R.id.admin_recyclerview);

        backNav.setOnClickListener(e->{
            finish();
        });
    }
}