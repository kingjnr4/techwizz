package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.FixtureAdapter;
import com.example.myapplication.adapter.FixtureAdapter2;
import com.example.myapplication.model.Fixture;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Fixtures extends Fragment {

    private FirebaseFirestore db;
    private List<Fixture> fixtureList;
    private FixtureAdapter2 adapter;

    RecyclerView fixtureRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         View view = inflater.inflate(R.layout.fixtures, container, false);

        db= FirebaseFirestore.getInstance();
        fixtureList=new ArrayList<>();
        initList();

         adapter = new FixtureAdapter2(view.getContext(), fixtureList);


        fixtureRecyclerView = view.findViewById(R.id.fixture_recyclerview);
        fixtureRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        fixtureRecyclerView.setAdapter(adapter);

         return view;
    }

    private void initList() {
        db.collection("fixtures").limit(5).get().addOnCompleteListener(task -> {
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
