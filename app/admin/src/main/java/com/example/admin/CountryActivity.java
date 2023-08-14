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
    private SearchView searchView;
    private TextView statusMessage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.countries);
        statusMessage = findViewById(R.id.displayed_message);
        searchView = findViewById(R.id.searchView);

        db=FirebaseFirestore.getInstance();
        countryList = new ArrayList<>();
        initList();



        adapter = new CountryRecyclerViewAdapter(this,countryList);
        countryRecyclerview = findViewById(R.id.country_recyclerview);

        countryRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        countryRecyclerview.setAdapter(adapter);


        ImageView backNav = findViewById(R.id.back_nav);

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
                filterCountries(newText);
                return true;
            }
        });
    }

    private void filterCountries(String searchText) {
        ArrayList<Country> filteredCountries = new ArrayList<>();

        for (Country country : countryList) {
            if (country.getName().toLowerCase().contains(searchText.toLowerCase())) {
                statusMessage.setVisibility(View.GONE);
                filteredCountries.add(country);
            }
        }

        if (filteredCountries.isEmpty()) {
            statusMessage.setVisibility(View.VISIBLE);
            statusMessage.setText("No match found");
        } else {
            statusMessage.setVisibility(View.GONE);
        }


        adapter.filterList(filteredCountries);
    }


    private void initList() {
        db.collection("countries").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot snapshot = task.getResult();
                if (!snapshot.isEmpty()) {
                    statusMessage.setVisibility(View.GONE);
                    for (QueryDocumentSnapshot documentSnapshot : snapshot) {
                        Country country = documentSnapshot.toObject(Country.class);
                        countryList.add(country);
                        Log.d("league",country.getName());
                        adapter.notifyDataSetChanged();
                    }
                }else {
                    statusMessage.setText("No country available");
                }
            }
        });
    }
}
