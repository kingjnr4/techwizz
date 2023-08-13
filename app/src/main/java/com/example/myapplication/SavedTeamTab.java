package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.TeamAdapter;
import com.example.myapplication.adapter.TopScoreAdapter;
import com.example.myapplication.model.Club;
import com.example.myapplication.model.Player;

import java.util.List;

public class SavedTeamTab extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.saved_team_tab, container,false);

        List<Club> clubList = Home.FixtureDataGenerator.generateSampleClub();

        TeamAdapter teamAdapter = new TeamAdapter(view.getContext(), clubList);


        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RecyclerView savedTeamRecyclerView = view.findViewById(R.id.saved_team_recyclerview);


        savedTeamRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));
        savedTeamRecyclerView.setAdapter(teamAdapter);

        return view;
    }
}
