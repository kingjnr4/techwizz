package com.example.admin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.admin.Model.Admin;
import com.example.admin.Model.Feedback;
import com.example.admin.adapter.AdminAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    RecyclerView adminRecyclerview;
    FirebaseFirestore db;
    private List<Admin> mAdminList;
    private AdminAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
        mAdminList=new ArrayList<>();
        db=FirebaseFirestore.getInstance();
        initList();
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView backNav = findViewById(R.id.back_nav);
        adminRecyclerview = findViewById(R.id.admin_recyclerview);
        adapter=new AdminAdapter(this,mAdminList);
        adminRecyclerview.setAdapter(adapter);
        adminRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        backNav.setOnClickListener(e->{
            finish();
        });
    }
    private void initList() {
        db.collection("admins").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot snapshot = task.getResult();
                if (!snapshot.isEmpty()) {
                    for (QueryDocumentSnapshot documentSnapshot : snapshot) {
                        Admin admin = documentSnapshot.toObject(Admin.class);
                        mAdminList.add(admin);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

}