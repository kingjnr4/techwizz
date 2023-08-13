package com.example.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.admin.Model.Country;
import com.example.admin.Model.League;
import com.example.admin.adapter.CountryAdapter;
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

public class AddLeague extends AppCompatActivity {
    private FirebaseFirestore db;
    private Uri imageUri;
    private StorageReference storageReference;
    private ArrayList<Country> mCountryList;
    private CountryAdapter mAdapter;
    private Country country;
    private Button addLeagueBtn;
    private ImageView leagueLogo;
    private TextInputEditText leagueField;
    private TextInputLayout leagueContainer;
    private  Spinner spinnerCountries;
    private static final int REQUEST_IMAGE_PICKER = 1;

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_league);
        storageReference= FirebaseStorage.getInstance().getReference();
        db= FirebaseFirestore.getInstance();
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView backNav = findViewById(R.id.back_nav);
        backNav.setOnClickListener(e->{
            finish();
        });
        leagueLogo =findViewById(R.id.new_league_logo);
        addLeagueBtn =findViewById(R.id.add_league_btn);
        leagueContainer =findViewById(R.id.league_name_container);
        leagueField =findViewById(R.id.league_name_field);
        addLeagueBtn.setOnClickListener(click->{
            validateAndUpload();
        });
        leagueLogo.setOnClickListener(click->{
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_IMAGE_PICKER);
        });
        initList();
         spinnerCountries = findViewById(R.id.spinner_countries);
        mAdapter = new CountryAdapter(this, mCountryList);
        spinnerCountries.setAdapter(mAdapter);
        spinnerCountries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    country=null;
                    return;
                }
                country = (Country) parent.getItemAtPosition(position-1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void validateAndUpload() {
        leagueContainer.setError(null);

        boolean isValid = true;

        if (leagueField.getText().toString().isEmpty()) {
            leagueContainer.setError("League name is required");
            isValid = false;
        }

        if(imageUri==null){
            Helpers.showPopupWindow(this,"Image Required",2000, Color.YELLOW,Color.WHITE);
            isValid=false;
        }
//        if(country==null){
//            Helpers.showPopupWindow(this,"Country Required",2000, Color.YELLOW,Color.WHITE);
//            isValid=false;
//        }
        if(isValid){
            League league = League.builder().name(leagueField.getText().toString()).country(country).build();
            uploadImageToFirebaseStorage(imageUri,league);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICKER && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            leagueLogo.setImageURI(selectedImageUri);
            imageUri=selectedImageUri;
        }
    }
    private void uploadImageToFirebaseStorage(Uri imageUri, League league) {

        StorageReference imageRef = storageReference.child("images/leagues/" + Helpers.generateRandomImageName());
        imageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        league.setImage(uri.toString());
                        uploadLeague(league);
                    });
                })
                .addOnFailureListener(e -> {
                    Helpers.showPopupWindow(this,"Error Uploading Image",3000, Color.YELLOW,Color.WHITE);
                });
    }

    private void uploadLeague(League league){
        db.collection("leagues").document(league.getName()).set(league, SetOptions.merge()).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Helpers.showPopupWindow(this,"League Saved",2000,Color.GREEN,Color.WHITE);
                leagueField.setText("");
                leagueLogo.setImageURI(null);
            }else {
                Helpers.showPopupWindow(this,"Error Saving League",3000, Color.YELLOW,Color.WHITE);
            }
        });
    }

    private void initList() {
        mCountryList = new ArrayList<>();
        db.collection("countries").get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                QuerySnapshot snapshot=task.getResult();
                if(!snapshot.isEmpty()){
                    for (QueryDocumentSnapshot documentSnapshot : snapshot) {
                        Country country = documentSnapshot.toObject(Country.class);
                        mCountryList.add(country);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
