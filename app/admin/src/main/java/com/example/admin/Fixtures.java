package com.example.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class Fixtures extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fixtures, container, false);
        Button addFixtureBtn = view.findViewById(R.id.add_fixture_btn);

        addFixtureBtn.setOnClickListener(e->{
            Intent intent = new Intent(view.getContext(), AddFixtureActivity.class);
            startActivity(intent);
        });


        return view;
    }
}
