package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    Home home = new Home();
    Menu menu = new Menu();
    Fixtures fixtures = new Fixtures();
    Saved saved = new Saved();

    Explore explore = new Explore();

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
            else if (item.getItemId() == R.id.saved) {
                getSupportFragmentManager().beginTransaction().replace(R.id.home_container,saved).commit();
                return true;
            }
            else if (item.getItemId() == R.id.fixtures) {
                getSupportFragmentManager().beginTransaction().replace(R.id.home_container,fixtures).commit();
                return true;
            }else if (item.getItemId() == R.id.menu) {
                getSupportFragmentManager().beginTransaction().replace(R.id.home_container,menu).commit();
                return true;
            }
            else if (item.getItemId() == R.id.explore) {
                getSupportFragmentManager().beginTransaction().replace(R.id.home_container,explore).commit();
                return true;
            }
            return false;
        });

    }
}