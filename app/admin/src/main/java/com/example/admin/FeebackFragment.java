package com.example.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.admin.Model.Feedback;
import com.example.admin.Model.League;
import com.example.admin.adapter.FeedbackAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FeebackFragment extends Fragment {
    private RecyclerView feedbackRecyclerView;
    private FirebaseFirestore db;
    private List<Feedback> feedbackList;
    private    FeedbackAdapter adapter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db=FirebaseFirestore.getInstance();
        feedbackList = new ArrayList<>();
        initList();
        View view = inflater.inflate(R.layout.feedbacks, container, false);
        adapter = new FeedbackAdapter(getContext(),feedbackList);
        feedbackRecyclerView =view.findViewById(R.id.feedback_recyclerview);
        feedbackRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        feedbackRecyclerView.setAdapter(adapter);

        return view;
    }

    private void initList() {
        db.collection("feedback").get().addOnCompleteListener(task -> {
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