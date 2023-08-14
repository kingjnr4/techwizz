package com.example.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.admin.Model.League;
import com.example.admin.Model.Team;
import com.example.admin.adapter.LeagueAdapter;
import com.example.admin.helpers.Helpers;
import com.example.admin.helpers.InputFilterMinMax;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AddTeam extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICKER = 1;
    private FirebaseFirestore db;
    private Uri imageUri;
    private StorageReference storageReference;
    private ArrayList<League> mLeagueList;
    private LeagueAdapter mAdapter;
    private Spinner spinnerLeagues;
    private League league;
    private TextInputEditText teamNameField;
    private TextInputLayout teamNameContainer;
    private TextInputEditText teamShortNameField;
    private TextInputLayout teamShortNameContainer;
    private TextInputEditText teamManagerNameField;
    private TextInputLayout teamManagerNameContainer;

    private TextInputEditText teamRatingField;
    private TextInputLayout teamRatingContainer;
    private Button addTeamBtn;
    private ImageView teamLogo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_team);
        storageReference = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView backNav = findViewById(R.id.back_nav);
        backNav.setOnClickListener(e -> {
            finish();
        });
        initList();
        spinnerLeagues = findViewById(R.id.spinner_leagues);
        mAdapter = new LeagueAdapter(this, mLeagueList);
        spinnerLeagues.setAdapter(mAdapter);
        spinnerLeagues.setOnItemSelectedListener((new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                league = (League) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        }));
        teamLogo = findViewById(R.id.new_team_logo);
        teamShortNameContainer = findViewById(R.id.team_short_name_container);
        teamShortNameField = findViewById(R.id.team_short_name_field);
        teamNameContainer = findViewById(R.id.team_name_container);
        teamNameField = findViewById(R.id.team_name_field);
        teamManagerNameContainer = findViewById(R.id.team_manger_name_container);
        teamManagerNameField = findViewById(R.id.team_manager_name_field);
        teamRatingContainer=findViewById(R.id.team_rating_container);
        teamRatingField=findViewById(R.id.team_rating_field);
        addTeamBtn = findViewById(R.id.add_team_btn);
        teamRatingField.setFilters(new InputFilter[]{new InputFilterMinMax(0,10)});
        teamLogo.setOnClickListener(click -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_IMAGE_PICKER);
        });

        addTeamBtn.setOnClickListener(click -> {
            validateAndUpload();
        });
    }

    private void validateAndUpload() {
        teamManagerNameContainer.setError(null);
        teamNameContainer.setError(null);
        teamShortNameContainer.setError(null);
        boolean isValid = true;

        if (teamShortNameField.getText().toString().isEmpty()) {
            teamShortNameContainer.setError("Team Short name is required");
            isValid = false;
        }
        if (teamNameField.getText().toString().isEmpty()) {
            teamNameContainer.setError("Team name is required");
            isValid = false;
        }
        if (teamManagerNameField.getText().toString().isEmpty()) {
            teamManagerNameContainer.setError("Manager name is required");
            isValid = false;
        }
        if (teamRatingField.getText().toString().isEmpty()) {
            teamRatingContainer.setError("Valid rating from 0 to 10 is required");
            isValid = false;
        }
        if (imageUri == null) {
            Helpers.showPopupWindow(this, "Image Required", 2000, Color.YELLOW, Color.WHITE);
            isValid = false;
        }
        if (league == null) {
            Helpers.showPopupWindow(this, "League Required", 2000, Color.YELLOW, Color.WHITE);
            isValid = false;
        }
        if (isValid) {
            ArrayList<League> leagues = new ArrayList<League>();
            leagues.add(league);
            Team team = Team.builder().name(teamNameField.getText().toString())
                    .managerName(teamManagerNameField.getText().toString())
                    .shortName(teamShortNameField.getText().toString())
                    .rating(Integer.parseInt(teamRatingField.getText().toString()))
                    .leagues(leagues)
                    .build();
            uploadImageToFirebaseStorage(imageUri, team);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICKER && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            teamLogo.setImageURI(selectedImageUri);
            imageUri = selectedImageUri;
        }
    }

    private void initList() {
        mLeagueList = new ArrayList<>();
        db.collection("leagues").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot snapshot = task.getResult();
                if (!snapshot.isEmpty()) {
                    for (QueryDocumentSnapshot documentSnapshot : snapshot) {
                        League league = documentSnapshot.toObject(League.class);
                        mLeagueList.add(league);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void uploadImageToFirebaseStorage(Uri imageUri, Team team) {

        StorageReference imageRef = storageReference.child("images/teams/" + Helpers.generateRandomImageName());
        imageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        team.setImage(uri.toString());
                        uploadTeam(team);
                    });
                })
                .addOnFailureListener(e -> {
                    Helpers.showPopupWindow(this, "Error Uploading Image", 3000, Color.YELLOW, Color.WHITE);
                });
    }

    private void uploadTeam(Team team) {
       db.collection("teams").document(team.getShortName()).get().addOnCompleteListener(task -> {
           if(task.isSuccessful()){
                DocumentSnapshot snapshot =task.getResult();
                if(snapshot.exists()){
                    Team exist = snapshot.toObject(Team.class);
                    ArrayList<League> leagues = exist.getLeagues();
                    for(League e: team.getLeagues()){
                        if(!leagues.contains(e))
                            leagues.add(e);
                    }
                    team.setLeagues(leagues);
                    db.collection("teams").document(team.getShortName()).set(team, SetOptions.merge()).addOnCompleteListener(task1 -> {
                        if (task.isSuccessful()) {
                            Helpers.showPopupWindow(this, "Team Saved", 2000, Color.GREEN, Color.WHITE);
                            teamNameField.setText("");
                            teamManagerNameField.setText("");
                            teamShortNameField.setText("");
                            teamLogo.setImageURI(null);
                            teamRatingField.setText(null);
                        } else {
                            Helpers.showPopupWindow(this, "Error Saving League", 3000, Color.YELLOW, Color.WHITE);
                        }
                    });
                }else{
                    db.collection("teams").document(team.getShortName()).set(team, SetOptions.merge()).addOnCompleteListener(task1 -> {
                        if (task.isSuccessful()) {
                            Helpers.showPopupWindow(this, "Team Saved", 2000, Color.GREEN, Color.WHITE);
                            teamNameField.setText("");
                            teamManagerNameField.setText("");
                            teamShortNameField.setText("");
                            teamLogo.setImageURI(null);
                            teamRatingField.setText(null);
                        } else {
                            Helpers.showPopupWindow(this, "Error Saving League", 3000, Color.YELLOW, Color.WHITE);
                        }
                    });
                }
           }
       });
    }
}
