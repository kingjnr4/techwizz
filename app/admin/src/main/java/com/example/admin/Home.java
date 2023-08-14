package com.example.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.admin.Model.Feedback;
import com.example.admin.adapter.FeedbackAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {
    private FirebaseFirestore db;
    private RecyclerView feedbackRecyclerView;
    private List<Feedback> feedbackList;
    private FeedbackAdapter adapter;
    LinearLayout addLeagueCard,addCountryCard,addTeamCard,addPlayerCard, playerCard,teamCard,countryCard,leagueCard;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db= FirebaseFirestore.getInstance();
        feedbackList=new ArrayList<>();
        initList();
        View view = inflater.inflate(R.layout.home, container, false);
        adapter=new FeedbackAdapter(getContext(),feedbackList);
         addLeagueCard = view.findViewById(R.id.add_league_card);
         addCountryCard = view.findViewById(R.id.add_country_card);
         addTeamCard = view.findViewById(R.id.add_team_card);
         addPlayerCard = view.findViewById(R.id.add_player_card);
         playerCard = view.findViewById(R.id.players_card);
        teamCard = view.findViewById(R.id.team_card);
        countryCard = view.findViewById(R.id.country_card);
        leagueCard = view.findViewById(R.id.league_card);
        feedbackRecyclerView =view.findViewById(R.id.feedback_recyclerview);
        feedbackRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        feedbackRecyclerView.setAdapter(adapter);

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

    private void initList() {
        db.collection("feedback").limit(5).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot snapshot = task.getResult();
                if (!snapshot.isEmpty()) {
                    for (QueryDocumentSnapshot documentSnapshot : snapshot) {
                        Feedback feedback = documentSnapshot.toObject(Feedback.class);
                        feedbackList.add(feedback);
                        Log.d("feedback",feedback.getMessage());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
