package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.HomeTopScoreAdapter;
import com.example.myapplication.adapter.TopScoreAdapter;
import com.example.myapplication.model.Player;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TopScore extends AppCompatActivity {
    FirebaseFirestore db;
    List<Player> playerList;
    TopScoreAdapter topScoreAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_score);
        db=FirebaseFirestore.getInstance();
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView backNav = findViewById(R.id.back_nav);

       playerList =new ArrayList<>();

         topScoreAdapter = new TopScoreAdapter(this, playerList);


        RecyclerView topScoreRecyclerView = findViewById(R.id.top_score_recyclerview);


        topScoreRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        topScoreRecyclerView.setAdapter(topScoreAdapter);

        backNav.setOnClickListener(e->{
            finish();
        });initList();
    }

    void initList(){
        db.collection("players").orderBy("goals", Query.Direction.ASCENDING).limit(30).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot snapshot = task.getResult();
                if (!snapshot.isEmpty()) {
                    for (QueryDocumentSnapshot documentSnapshot : snapshot) {
                        Player player = documentSnapshot.toObject(Player.class);
                        playerList.add(player);
                        topScoreAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }
}
