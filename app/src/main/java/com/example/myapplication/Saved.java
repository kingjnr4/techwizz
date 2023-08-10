package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class Saved extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.saved, container, false);


        Button signUpBtn = view.findViewById(R.id.sign_up_btn);
        Button signInBtn = view.findViewById(R.id.sign_in_btn);

        signUpBtn.setOnClickListener(v -> {

            Intent intent = new Intent(view.getContext(), SignUp.class);
            startActivity(intent);
        });

        signInBtn.setOnClickListener(v -> {

            Intent intent = new Intent(view.getContext(), SignIn.class);
            startActivity(intent);
        });

        return view;
    }
}
