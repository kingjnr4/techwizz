package com.example.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class Home extends Fragment {

    LinearLayout addLeagueCard,addCountryCard,addTeamCard,addPlayerCard, playerCard,teamCard,countryCard,leagueCard;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home, container, false);

         addLeagueCard = view.findViewById(R.id.add_league_card);
         addCountryCard = view.findViewById(R.id.add_country_card);
         addTeamCard = view.findViewById(R.id.add_team_card);
         addPlayerCard = view.findViewById(R.id.add_player_card);
         playerCard = view.findViewById(R.id.players_card);
        teamCard = view.findViewById(R.id.team_card);
        countryCard = view.findViewById(R.id.country_card);
        leagueCard = view.findViewById(R.id.league_card);


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

        playerCard.setOnClickListener(v -> {

            Intent intent = new Intent(view.getContext(), PlayersActivity.class);
            startActivity(intent);
        });

        teamCard.setOnClickListener(v -> {

            Intent intent = new Intent(view.getContext(), TeamActivity.class);
            startActivity(intent);
        });

        countryCard.setOnClickListener(v -> {

            Intent intent = new Intent(view.getContext(), CountryActivity.class);
            startActivity(intent);
        });

        leagueCard.setOnClickListener(v -> {

            Intent intent = new Intent(view.getContext(), LeagueActivity.class);
            startActivity(intent);
        });


        return view;
    }
}
