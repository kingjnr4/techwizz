package com.example.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.Model.Country;
import com.example.admin.Model.League;
import com.example.admin.Model.Team;
import com.example.admin.adapter.LeagueRecyclerViewAdapter;
import com.example.admin.adapter.TeamAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LeagueActivity extends AppCompatActivity {

    RecyclerView leagueRecyclerview;

    private FirebaseFirestore db;
    private List<League> leagueList;
    private SearchView searchView;
    private TextView statusMessage;

    private LeagueRecyclerViewAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.league);
        statusMessage = findViewById(R.id.displayed_message);
        searchView = findViewById(R.id.searchView);

        db=FirebaseFirestore.getInstance();
        leagueList = new ArrayList<>();
        initList();

        ImageView backNav = findViewById(R.id.back_nav);


        adapter = new LeagueRecyclerViewAdapter(this,leagueList);
        leagueRecyclerview = findViewById(R.id.league_recyclerview);

        leagueRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        leagueRecyclerview.setAdapter(adapter);

        backNav.setOnClickListener(e->{
            finish();
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterLeagues(newText);
                return true;
            }
        });
    }

    private void filterLeagues(String searchText) {
        ArrayList<League> filteredLeagues = new ArrayList<>();

        for (League league : leagueList) {
            if (league.getName().toLowerCase().contains(searchText.toLowerCase())) {
                statusMessage.setVisibility(View.GONE);
                filteredLeagues.add(league);
            }
        }

        if (filteredLeagues.isEmpty()) {
            statusMessage.setVisibility(View.VISIBLE);
            statusMessage.setText("No match found");
        } else {
            statusMessage.setVisibility(View.GONE);
        }


        adapter.filterList(filteredLeagues);
    }

    private void initList() {
        db.collection("leagues").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot snapshot = task.getResult();
                if (!snapshot.isEmpty()) {
                    statusMessage.setVisibility(View.GONE);
                    for (QueryDocumentSnapshot documentSnapshot : snapshot) {
                        League league = documentSnapshot.toObject(League.class);
                        leagueList.add(league);
                        Log.d("league",league.getName());
                        adapter.notifyDataSetChanged();
                    }
                }else{
                    statusMessage.setText("No league available");
                }
            }
        });
    }
}
