package com.example.admin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.admin.Model.CountryItem;
import com.example.admin.adapter.CountryAdapter;

import java.util.ArrayList;

public class AddLeague extends AppCompatActivity {

    private ArrayList<CountryItem> mCountryList;
    private CountryAdapter mAdapter;

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_league);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView backNav = findViewById(R.id.back_nav);

        backNav.setOnClickListener(e->{
            finish();
        });

        initList();

        Spinner spinnerCountries = findViewById(R.id.spinner_countries);

        mAdapter = new CountryAdapter(this, mCountryList);
        spinnerCountries.setAdapter(mAdapter);

        spinnerCountries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CountryItem clickedItem = (CountryItem) parent.getItemAtPosition(position);
                String clickedCountryName = clickedItem.getCountryName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initList() {
        mCountryList = new ArrayList<>();
        mCountryList.add(new CountryItem("India", R.drawable.spain_flag));
        mCountryList.add(new CountryItem("China", R.drawable.spain_flag));
        mCountryList.add(new CountryItem("USA", R.drawable.spain_flag));
        mCountryList.add(new CountryItem("Germany", R.drawable.spain_flag));
    }
}
