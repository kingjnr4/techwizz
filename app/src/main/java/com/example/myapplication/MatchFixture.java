package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.adapter.MatchViewPageAdapter;
import com.example.myapplication.adapter.SaveViewPageAdapter;
import com.google.android.material.tabs.TabLayout;

public class MatchFixture extends AppCompatActivity {

    TabLayout matchTabLayout;
    ViewPager2 matchViewPage2;
    MatchViewPageAdapter matchViewPageAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fixture);

        TextView backNav = findViewById(R.id.fix_back_label);

        matchTabLayout = findViewById(R.id.match_details_tab_layout);
        matchViewPage2 = findViewById(R.id.match_view_page);


        matchViewPageAdapter = new MatchViewPageAdapter(this);

        backNav.setOnClickListener(e->{
                finish();
        });


        matchViewPage2.setAdapter(matchViewPageAdapter);
        matchTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                matchViewPage2.setVisibility(View.VISIBLE);
                matchViewPage2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                matchViewPage2.setVisibility(View.VISIBLE);
            }
        });

        matchViewPage2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        matchTabLayout.getTabAt(position).select();

                }
                super.onPageSelected(position);
            }
        });

    }
}
