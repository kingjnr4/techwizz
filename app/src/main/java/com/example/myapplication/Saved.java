package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.adapter.SaveViewPageAdapter;
import com.google.android.material.tabs.TabLayout;

public class Saved extends Fragment {

    TabLayout savedTabLayout;
    ViewPager2 savedViewPage2;
    SaveViewPageAdapter saveViewPageAdapter;
    LinearLayout notLoginContainer,loginContainer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.saved, container, false);

        savedTabLayout = view.findViewById(R.id.saved_tab_layout);
        savedViewPage2 = view.findViewById(R.id.saved_view_page);
        saveViewPageAdapter = new SaveViewPageAdapter(this);
        Button signUpBtn = view.findViewById(R.id.sign_up_btn);
        Button signInBtn = view.findViewById(R.id.sign_in_btn);
         notLoginContainer = view.findViewById(R.id.not_login_container);
         loginContainer = view.findViewById(R.id.saved_container);

        notLoginContainer.setVisibility(View.GONE);


        signUpBtn.setOnClickListener(v -> {

            Intent intent = new Intent(view.getContext(), SignUp.class);
            startActivity(intent);
        });

        signInBtn.setOnClickListener(v -> {

            Intent intent = new Intent(view.getContext(), SignIn.class);
            startActivity(intent);
        });



        savedViewPage2.setAdapter(saveViewPageAdapter);
        savedTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                savedViewPage2.setVisibility(View.VISIBLE);
                savedViewPage2.setCurrentItem(tab.getPosition());
                System.out.println(tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                savedViewPage2.setVisibility(View.GONE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                savedViewPage2.setVisibility(View.VISIBLE);
            }
        });

        savedViewPage2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                    case 1:
                        savedTabLayout.getTabAt(position).select();

                }
                super.onPageSelected(position);
            }
        });

        return view;
    }
}
