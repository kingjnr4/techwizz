package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

public class Menu extends Fragment {
    private FirebaseAuth auth;
    private TextView userName,userEmail;
    private ImageView userPfp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.menu, container, false);
        auth=FirebaseAuth.getInstance();
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
        userEmail=view.findViewById(R.id.user_email);
        userName=view.findViewById(R.id.user_name);
        userPfp=view.findViewById(R.id.user_pfp);
        if(auth.getCurrentUser()!=null){
            userEmail.setText(auth.getCurrentUser().getEmail());
            userName.setText(auth.getCurrentUser().getDisplayName());
            if(!auth.getCurrentUser().getPhotoUrl().toString().isEmpty()){
                Glide.with(view.getContext()).load(auth.getCurrentUser().getPhotoUrl()).into(userPfp);
            }
        }
        return view;
    }
}
