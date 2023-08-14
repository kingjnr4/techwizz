package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    Home home = new Home();
    Menu menu = new Menu();
    Fixtures fixtures = new Fixtures();

    FeebackFragment feedbacks = new FeebackFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_Nav);

        getSupportFragmentManager().beginTransaction().replace(R.id.home_container,home).commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.home){
                getSupportFragmentManager().beginTransaction().replace(R.id.home_container,home).commit();
                return true;
            }
            else if (item.getItemId() == R.id.fixtures) {
                getSupportFragmentManager().beginTransaction().replace(R.id.home_container,fixtures).commit();
                return true;
            }else if (item.getItemId() == R.id.menu) {
                getSupportFragmentManager().beginTransaction().replace(R.id.home_container,menu).commit();
                return true;
            }
            else if (item.getItemId() == R.id.feedbacks) {
                getSupportFragmentManager().beginTransaction().replace(R.id.home_container,feedbacks).commit();
                return true;
            }
            return false;
        });
    }
}