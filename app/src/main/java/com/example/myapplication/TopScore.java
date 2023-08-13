package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.HomeTopScoreAdapter;
import com.example.myapplication.adapter.TopScoreAdapter;
import com.example.myapplication.model.Player;

import java.util.List;

public class TopScore extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_score);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView backNav = findViewById(R.id.back_nav);

        List<Player> playerList = Home.FixtureDataGenerator.generateSampleTopScore();

        TopScoreAdapter topScoreAdapter = new TopScoreAdapter(this, playerList);


        RecyclerView topScoreRecyclerView = findViewById(R.id.top_score_recyclerview);


        topScoreRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        topScoreRecyclerView.setAdapter(topScoreAdapter);

        backNav.setOnClickListener(e->{
            finish();
        });

    }
}
