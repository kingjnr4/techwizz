package com.example.admin;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class TeamActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teams);

        ImageView backNav = findViewById(R.id.back_nav);

        backNav.setOnClickListener(e->{
            finish();
        });

    }
}
