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

import com.example.myapplication.adapter.TopScoreAdapter;
import com.example.myapplication.model.Player;

import java.util.ArrayList;
import java.util.List;

public class SavedPlayerTab extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.saved_player_tab, container,false);

        List<Player> playerList = new ArrayList<>();

        TopScoreAdapter topScoreAdapter = new TopScoreAdapter(view.getContext(), playerList);


        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RecyclerView topScoreRecyclerView = view.findViewById(R.id.top_score_recyclerview);


        topScoreRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));
        topScoreRecyclerView.setAdapter(topScoreAdapter);

        return view;
    }
}
