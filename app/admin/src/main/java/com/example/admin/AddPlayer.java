package com.example.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.Spinner;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.admin.Model.Country;
import com.example.admin.Model.League;
import com.example.admin.Model.Player;
import com.example.admin.Model.Team;
import com.example.admin.adapter.TeamSpinnerAdapter;
import com.example.admin.helpers.Helpers;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddPlayer extends AppCompatActivity {
    private TextInputLayout firstNameContainer,lastNameConainer,weightContainer,ageContainer,heightContainer,goalsContainer,matchesContainer,bioContainer;
    private TextInputEditText firstNameField,lastNameField,weightField,ageField,heightField,goalsField,bioField,matchesField;
    private Button saveBtn;
    private FirebaseFirestore db;

    private static final int REQUEST_IMAGE_PICKER = 1;
    private Uri imageUri;
    private StorageReference ref;
    private ArrayList<Team> teams;
    private Spinner postionsSpinner,teamsSpinner;
    private ImageView playerPhoto;
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
        saveBtn=findViewById(R.id.save_player);
        initList();
        firstNameContainer=findViewById(R.id.player_firstname_container);
        lastNameConainer=findViewById(R.id.player_lastname_container);
        weightContainer=findViewById(R.id.player_weight_container);
        heightContainer=findViewById(R.id.player_height_container);
        ageContainer=findViewById(R.id.player_age_container);
        goalsContainer=findViewById(R.id.player_goals_container);
        matchesContainer=findViewById(R.id.player_matches_container);
        bioContainer=findViewById(R.id.player_biography_container);
        firstNameField=findViewById(R.id.player_firstname_field);
        lastNameField=findViewById(R.id.player_lastname_field);
        weightField=findViewById(R.id.player_weight);
        heightField=findViewById(R.id.player_height);
        ageField=findViewById(R.id.player_age);
        goalsField=findViewById(R.id.player_goals);
        matchesField=findViewById(R.id.player_matches);
        bioField=findViewById(R.id.player_biography);
        playerPhoto=findViewById(R.id.new_player_logo);
        playerPhoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_IMAGE_PICKER);
        });
        saveBtn.setOnClickListener(v -> {
            validateAndUpload();
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICKER && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            playerPhoto.setImageURI(selectedImageUri);
            imageUri=selectedImageUri;
        }
    }
    private void validateAndUpload() {
        boolean isvlid = true;
        firstNameContainer.setError(null);
        lastNameConainer.setError(null);
        weightContainer.setError(null);
        heightContainer.setError(null);
        ageContainer.setError(null);
        goalsContainer.setError(null);
        matchesContainer.setError(null);
        bioContainer.setError(null);
        if(firstNameField.getText().toString().isEmpty()){
            isvlid=false;
            firstNameContainer.setError("PLease Enter Firstname");
        }
        if(lastNameField.getText().toString().isEmpty()){
            isvlid=false;
            lastNameConainer.setError("Please Enter Lastname");
        }
        if(ageField.getText().toString().isEmpty()){
            isvlid=false;
            ageContainer.setError("PLease Enter Age");
        }
        if(weightField.getText().toString().isEmpty()){
            isvlid=false;
            weightContainer.setError("PLease Enter Weight");
        }
        if(heightField.getText().toString().isEmpty()){
            isvlid=false;
            heightContainer.setError("PLease Enter Height");
        }
        if(goalsField.getText().toString().isEmpty()){
            isvlid=false;
            goalsContainer.setError("PLease Enter Goals");
        }
        if(matchesField.getText().toString().isEmpty()){
            isvlid=false;
            matchesContainer.setError("PLease Enter Matches");
        }
        if(bioField.getText().toString().isEmpty()){
            isvlid=false;
            bioContainer.setError("PLease Enter Bio");
        }
        if(teamsSpinner.getSelectedItem()==null){
            isvlid=false;
            Helpers.showPopupWindow(this,"Team Required",2000, Color.YELLOW,Color.WHITE);

        }
        if(postionsSpinner.getSelectedItem()==null){
            isvlid=false;
            Helpers.showPopupWindow(this,"Position Required",2000, Color.YELLOW,Color.WHITE);

        }
        if(imageUri==null){
            isvlid=false;
            Helpers.showPopupWindow(this,"Image Required",2000, Color.YELLOW,Color.WHITE);
        }
        if(isvlid){
            Player player=  Player.builder()
                    .firstname(firstNameField.getText().toString())
                    .lastname(lastNameField.getText().toString())
                    .weight(Integer.parseInt(weightField.getText().toString()))
                    .height(Integer.parseInt(heightField.getText().toString()))
                    .age(Integer.parseInt(ageField.getText().toString()))
                    .bio(bioField.getText().toString())
                    .goals(Integer.parseInt(goalsField.getText().toString()))
                    .matches(Integer.parseInt(matchesField.getText().toString()))
                    .position((String) postionsSpinner.getSelectedItem())
                    .team((Team) teamsSpinner.getSelectedItem())
                    .build();
            uploadImageToFirebaseStorage(imageUri,player);
        }
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

    private void uploadImageToFirebaseStorage(Uri imageUri, Player player) {

        StorageReference imageRef = ref.child("images/players/" + Helpers.generateRandomImageName());
        imageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        player.setImage(uri.toString());
                        uploadPlayer(player);
                    });
                })
                .addOnFailureListener(e -> {
                    Helpers.showPopupWindow(this,"Error Uploading Image",3000, Color.YELLOW,Color.WHITE);
                });
    }

    private void uploadPlayer(Player player){
        db.collection("players").document(player.getFullName()).set(player, SetOptions.merge()).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Helpers.showPopupWindow(this,"Player Saved",2000,Color.GREEN,Color.WHITE);
                playerPhoto.setImageURI(null);
            }else {
                Helpers.showPopupWindow(this,"Error Saving League",3000, Color.YELLOW,Color.WHITE);
            }
        });
    }

}
