package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PlayerActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView backNav = findViewById(R.id.fix_back_label);

        backNav.setOnClickListener(e->{
            finish();
        });

    }
}
