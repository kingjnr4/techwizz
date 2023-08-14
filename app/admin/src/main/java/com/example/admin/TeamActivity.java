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

import com.example.admin.Model.Team;
import com.example.admin.adapter.TeamAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TeamActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private List<Team> teamList;
    private TeamAdapter adapter;

    private SearchView searchView;

    private TextView statusMessage;
    RecyclerView teamRecyclerview;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.teams);
        statusMessage = findViewById(R.id.displayed_message);


        db=FirebaseFirestore.getInstance();
        teamList = new ArrayList<>();
        initList();

        ImageView backNav = findViewById(R.id.back_nav);

        teamRecyclerview = findViewById(R.id.team_recyclerview);
        searchView = findViewById(R.id.searchView);

        adapter = new TeamAdapter(this,teamList);
        teamRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        teamRecyclerview.setAdapter(adapter);

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
                filterTeams(newText);
                return true;
            }
        });

    }

    private void filterTeams(String searchText) {
        ArrayList<Team> filteredTeams = new ArrayList<>();

        for (Team team : teamList) {
            if (team.getName().toLowerCase().contains(searchText.toLowerCase())) {
                statusMessage.setVisibility(View.GONE);
                filteredTeams.add(team);
            }
        }

        if (filteredTeams.isEmpty()) {
            statusMessage.setVisibility(View.VISIBLE);
            statusMessage.setText("No match found");
        } else {
            statusMessage.setVisibility(View.GONE);
        }


        adapter.filterList(filteredTeams);
    }


    private void initList() {
        db.collection("teams").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot snapshot = task.getResult();
                if (!snapshot.isEmpty()) {
                    statusMessage.setVisibility(View.GONE);
                    for (QueryDocumentSnapshot documentSnapshot : snapshot) {
                        Team team = documentSnapshot.toObject(Team.class);
                        teamList.add(team);
                        Log.d("team",team.getName());
                        adapter.notifyDataSetChanged();
                    }
                }else {
                    statusMessage.setText("No team available");
                }
            }
        });
    }

}
