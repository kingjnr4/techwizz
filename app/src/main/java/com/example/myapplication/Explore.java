package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class Explore extends Fragment {

    RecyclerView clubSearchRecyclerview, playerSearchRecyclerview, leagueSearchRecyclerview, countrySearchRecyclerview;
    LinearLayout searchContainer, exploreContainer, clubSearchContainer, leagueSearchContainer, countrySearchContainer, playerSearchContainer;
    SearchView searchView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.explore, container, false);


        searchView = view.findViewById(R.id.searchView);

         clubSearchRecyclerview = view.findViewById(R.id.club_search_recyclerview);
         playerSearchRecyclerview = view.findViewById(R.id.players_search_recyclerview);
         leagueSearchRecyclerview = view.findViewById(R.id.club_search_recyclerview);
         countrySearchRecyclerview = view.findViewById(R.id.country_search_recyclerview);

         searchContainer = view.findViewById(R.id.search_container);
         exploreContainer = view.findViewById(R.id.explore_container);
        clubSearchContainer = view.findViewById(R.id.club_search_recyclerview_container);
        leagueSearchContainer = view.findViewById(R.id.league_search_recyclerview_container);
        countrySearchContainer = view.findViewById(R.id.country_search_recyclerview_container);
        playerSearchContainer = view.findViewById(R.id.players_search_recyclerview_container);



        searchContainer.setVisibility(View.GONE);


       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {

               if(!newText.isEmpty()){
                   exploreContainer.setVisibility(View.GONE);
                   searchContainer.setVisibility(View.VISIBLE);
               }else{
                   exploreContainer.setVisibility(View.VISIBLE);
                   searchContainer.setVisibility(View.GONE);
               }
               return true;
           }
       });

        return view;
    }
}
