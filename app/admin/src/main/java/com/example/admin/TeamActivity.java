package com.example.admin;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class TeamActivity extends AppCompatActivity {

    RecyclerView teamRecyclerview;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teams);

        ImageView backNav = findViewById(R.id.back_nav);

        teamRecyclerview = findViewById(R.id.team_recyclerview);

        backNav.setOnClickListener(e->{
            finish();
        });

    }
}
