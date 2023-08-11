package com.example.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class Home extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home, container, false);

        LinearLayout addLeagueCard = view.findViewById(R.id.add_league_card);
        LinearLayout addCountryCard = view.findViewById(R.id.add_country_card);
        LinearLayout addTeamCard = view.findViewById(R.id.add_team_card);
        LinearLayout addPlayerCard = view.findViewById(R.id.add_player_card);

        addLeagueCard.setOnClickListener(v -> {

            Intent intent = new Intent(view.getContext(), AddLeague.class);
            startActivity(intent);
        });

        addCountryCard.setOnClickListener(v -> {

            Intent intent = new Intent(view.getContext(), AddCountry.class);
            startActivity(intent);
        });

        addTeamCard.setOnClickListener(v -> {

            Intent intent = new Intent(view.getContext(), AddTeam.class);
            startActivity(intent);
        });

        addPlayerCard.setOnClickListener(v -> {

            Intent intent = new Intent(view.getContext(), AddPlayer.class);
            startActivity(intent);
        });


        return view;
    }
}
