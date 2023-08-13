package com.example.admin;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class CountryActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countries);

        ImageView backNav = findViewById(R.id.back_nav);

        backNav.setOnClickListener(e->{
            finish();
        });
    }
}
