package com.example.admin;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class CountryActivity extends AppCompatActivity {
    RecyclerView countryRecyclerview;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countries);

        ImageView backNav = findViewById(R.id.back_nav);
        countryRecyclerview = findViewById(R.id.country_recyclerview);

        backNav.setOnClickListener(e->{
            finish();
        });
    }
}
