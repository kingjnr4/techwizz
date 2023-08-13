package com.example.admin;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.Model.Country;
import com.example.admin.Model.League;
import com.example.admin.Model.Team;
import com.example.admin.adapter.CountryRecyclerViewAdapter;
import com.example.admin.adapter.LeagueRecyclerViewAdapter;
import com.example.admin.adapter.TeamAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CountryActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private List<Country> countryList;
    private CountryRecyclerViewAdapter adapter;
    RecyclerView countryRecyclerview;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db=FirebaseFirestore.getInstance();
        countryList = new ArrayList<>();
        initList();

        setContentView(R.layout.countries);

        adapter = new CountryRecyclerViewAdapter(this,countryList);
        countryRecyclerview = findViewById(R.id.country_recyclerview);

        countryRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        countryRecyclerview.setAdapter(adapter);


        ImageView backNav = findViewById(R.id.back_nav);

        backNav.setOnClickListener(e->{
            finish();
        });
    }

    private void initList() {
        db.collection("countries").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot snapshot = task.getResult();
                if (!snapshot.isEmpty()) {
                    for (QueryDocumentSnapshot documentSnapshot : snapshot) {
                        Country country = documentSnapshot.toObject(Country.class);
                        countryList.add(country);
                        Log.d("league",country.getName());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
