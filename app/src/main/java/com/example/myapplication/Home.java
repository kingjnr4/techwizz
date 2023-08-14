package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.FixtureAdapter;
import com.example.myapplication.adapter.FixtureAdapter2;
import com.example.myapplication.adapter.HomeTopScoreAdapter;
import com.example.myapplication.model.Club;
import com.example.myapplication.model.Fixture;
import com.example.myapplication.model.Player;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.ArrayList;

public class Home extends Fragment {

    private FirebaseFirestore db;
    private List<Fixture> fixtureList;
    List<Player> playerList;
    private FixtureAdapter adapter;

    HomeTopScoreAdapter topScoreAdapter;

    RecyclerView fixtureRecyclerView, topScoreRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home, container, false);
        TextView viewMoreToplayerLink = view.findViewById(R.id.view_all_top_score_link);

        db= FirebaseFirestore.getInstance();
        fixtureList = new ArrayList<>();
         playerList = new ArrayList<>();
         playerList = FixtureDataGenerator.generateSampleTopScore();
        initList();


        adapter = new FixtureAdapter(view.getContext(), fixtureList);
         topScoreAdapter = new HomeTopScoreAdapter(view.getContext(), playerList);


        fixtureRecyclerView = view.findViewById(R.id.todays_fixture_recyclerview);
        topScoreRecyclerView = view.findViewById(R.id.top_score_recyclerview);


        topScoreRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        topScoreRecyclerView.setAdapter(topScoreAdapter);

        fixtureRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fixtureRecyclerView.setAdapter(adapter);


        viewMoreToplayerLink.setOnClickListener(e->{
            Intent intent = new Intent(view.getContext(), TopScore.class);
            startActivity(intent);
        });



        return view;
    }



    public class FixtureDataGenerator {


        public static List<Player> generateSampleTopScore(){
            List<Player> players = new ArrayList<>();
            Player player1 = new Player("Lionel Messi","CMA");
            Player player2 = new Player("Cristiano Ronaldo","RMA");
            Player player3 = new Player("Lionel Messi","CMA");
            Player player4 = new Player("Lionel Messi","CMA");

            players.addAll(List.of(player1,player2,player3,player4,player1,player2,player3,player4,player1,player2,player3,player4,player1,player2,player3,player4));
            return players;
        }

        public static List<Club> generateSampleClub(){
            Club club1 = new Club("Chelsea FC");
            Club club2 = new Club("Manchester City");
            Club club3 = new Club("Manchester United");
            Club club4 = new Club("Real Madrid");

            List<Club> clubs = new ArrayList<>(List.of(club1,club2,club3,club4));


            return clubs;
        }

        // Rest of the class remains the same...
    }
    private void initList() {
        db.collection("fixtures").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot snapshot = task.getResult();
                if (!snapshot.isEmpty()) {
                    for (QueryDocumentSnapshot documentSnapshot : snapshot) {
                        Fixture fixture = documentSnapshot.toObject(Fixture.class);
                        fixtureList.add(fixture);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
