package com.example.admin;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import com.example.admin.Model.Country;
import com.example.admin.Model.Team;
import com.example.admin.adapter.TeamSpinnerAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddPlayer extends AppCompatActivity {
    private TextInputLayout firstNameContainer,lastNameConainer,weightContainer,ageContainer,heightContainer,goalsContainer,bioContainer;
    private TextInputEditText firstNameField,lastNameField,weightField,ageField,heightField,goalsField,bioField;
    private Button saveBtn;
    private FirebaseFirestore db;

    private static final int REQUEST_IMAGE_PICKER = 1;
    private Uri imageUri;
    private StorageReference ref;
    private ArrayList<Team> teams;
    private Spinner postionsSpinner,teamsSpinner;
    private List<String> positions;
    private TeamSpinnerAdapter spinnerAdapter;
    private ArrayAdapter<String> positionsAdapter;

    protected void onCreate( Bundle savedInstanceState) {
        initPositionsList();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_player);
        db=FirebaseFirestore.getInstance();
        ref= FirebaseStorage.getInstance().getReference();
        ImageView backNav = findViewById(R.id.back_nav);
        backNav.setOnClickListener(e->{
            finish();
        });
        teams=new ArrayList<>();
        postionsSpinner=findViewById(R.id.spinner_position);
        positionsAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,positions);
        postionsSpinner.setAdapter(positionsAdapter);
        teamsSpinner=findViewById(R.id.spinner_teams);
        spinnerAdapter=new TeamSpinnerAdapter(this,teams);
        teamsSpinner.setAdapter(spinnerAdapter);
        initList();
    }

    void initPositionsList(){
        String[] all = new String[]{"GK","CB","RB","LB","DM","CM","CAM","LM","RM","LW","RW","ST","CF"};
        positions=new ArrayList<>();
        positions.addAll(Arrays.asList(all));
    }

    private void initList() {
        db.collection("teams").get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                QuerySnapshot snapshot=task.getResult();
                if(!snapshot.isEmpty()){
                    for (QueryDocumentSnapshot documentSnapshot : snapshot) {
                        Team team = documentSnapshot.toObject(Team.class);
                        teams.add(team);
                        spinnerAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

}
