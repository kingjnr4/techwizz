package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MatchFixture extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fixture);

        TextView backNav = findViewById(R.id.fix_back_label);

        backNav.setOnClickListener(e->{
                finish();
        });

    }
}
