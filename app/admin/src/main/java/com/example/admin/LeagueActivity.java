package com.example.admin;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class LeagueActivity extends AppCompatActivity {

    RecyclerView leagueRecyclerview;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.league);

        ImageView backNav = findViewById(R.id.back_nav);

        leagueRecyclerview = findViewById(R.id.league_recyclerview);

        backNav.setOnClickListener(e->{
            finish();
        });
    }
}
