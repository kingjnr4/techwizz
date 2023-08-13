package com.example.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.admin.Model.Country;
import com.example.admin.helpers.Helpers;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddCountry extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICKER = 1;
    private Uri imageUri;
    private StorageReference storageReference;
    private FirebaseFirestore db;
    private Button addCountryBtn;
    private ImageView countryFlagImageView;
    private TextInputEditText countryField;
    private TextInputLayout countryContainer;
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_country);
        storageReference = FirebaseStorage.getInstance().getReference();
        db=FirebaseFirestore.getInstance();
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView backNav = findViewById(R.id.back_nav);
        backNav.setOnClickListener(e->{
            finish();
        });
        countryFlagImageView=findViewById(R.id.new_country_logo);
        addCountryBtn=findViewById(R.id.ad_country_btn);
        countryContainer=findViewById(R.id.country_container);
        countryField=findViewById(R.id.country_field);
        addCountryBtn.setOnClickListener(click ->{
            validateAndUpload();
        });
        countryFlagImageView.setOnClickListener(click -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_IMAGE_PICKER);
        });
    }

    private void validateAndUpload() {
        countryContainer.setError(null);

        boolean isValid = true;

        if (countryField.getText().toString().isEmpty()) {
            countryContainer.setError("Country name is required");
            isValid = false;
        }

        if(imageUri==null){
            Helpers.showPopupWindow(this,"Image Required",2000,Color.YELLOW,Color.WHITE);
            isValid=false;
        }
        if(isValid){
            Country country = Country.builder().name(countryField.getText().toString()).build();
            uploadImageToFirebaseStorage(imageUri,country);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICKER && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            countryFlagImageView.setImageURI(selectedImageUri);
            imageUri=selectedImageUri;
        }
    }
    private void uploadImageToFirebaseStorage(Uri imageUri, Country country) {

        StorageReference imageRef = storageReference.child("images/countries/" + Helpers.generateRandomImageName());
        imageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                       country.setImage(uri.toString());
                       uploadCountry(country);
                    });
                })
                .addOnFailureListener(e -> {
                    Helpers.showPopupWindow(this,"Error Uploading Image",3000, Color.YELLOW,Color.WHITE);
                });
    }

    private void uploadCountry(Country country){
        db.collection("countries").document(country.getName()).set(country).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Helpers.showPopupWindow(this,"Country Saved",2000,Color.GREEN,Color.WHITE);
                countryField.setText("");
                countryFlagImageView.setImageURI(null);
            }else {
                Helpers.showPopupWindow(this,"Error Saving Country",3000, Color.YELLOW,Color.WHITE);

            }
        });
    }
}
