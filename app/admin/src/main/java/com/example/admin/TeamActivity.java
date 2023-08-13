package com.example.admin;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.Model.Feedback;
import com.example.admin.Model.Team;
import com.example.admin.adapter.FeedbackAdapter;
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
    RecyclerView teamRecyclerview;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db=FirebaseFirestore.getInstance();
        teamList = new ArrayList<>();
        initList();

        setContentView(R.layout.teams);

        ImageView backNav = findViewById(R.id.back_nav);

        teamRecyclerview = findViewById(R.id.team_recyclerview);

        adapter = new TeamAdapter(this,teamList);
        teamRecyclerview =findViewById(R.id.team_recyclerview);
        teamRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        teamRecyclerview.setAdapter(adapter);

        backNav.setOnClickListener(e->{
            finish();
        });
    }

    private void initList() {
        db.collection("teams").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot snapshot = task.getResult();
                if (!snapshot.isEmpty()) {
                    for (QueryDocumentSnapshot documentSnapshot : snapshot) {
                        Team team = documentSnapshot.toObject(Team.class);
                        teamList.add(team);
                        Log.d("team",team.getName());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

}
