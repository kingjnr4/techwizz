package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class Menu extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.menu, container, false);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        LinearLayout settingsRouteLink = view.findViewById(R.id.settings_route);
        LinearLayout feedbackRouteLink = view.findViewById(R.id.feedback_route);
        LinearLayout contactusRouteLink = view.findViewById(R.id.contact_us_route);

       settingsRouteLink.setOnClickListener(e->{
           Intent intent = new Intent(view.getContext(), Settings.class);
           startActivity(intent);
       });

        feedbackRouteLink.setOnClickListener(e->{
            Intent intent = new Intent(view.getContext(), FeedBack.class);
            startActivity(intent);
        });

        contactusRouteLink.setOnClickListener(e->{
            Intent intent = new Intent(view.getContext(), Contactus.class);
            startActivity(intent);
        });


        return view;
    }
}
