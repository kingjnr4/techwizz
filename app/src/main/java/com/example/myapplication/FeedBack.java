package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.helpers.Helpers;
import com.example.myapplication.model.Feedback;
import com.example.myapplication.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FeedBack extends AppCompatActivity {

    private TextInputLayout inputLayout;
    private TextInputEditText editText;
    private Button save;
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        ImageView backNav = findViewById(R.id.back_nav);
        inputLayout=findViewById(R.id.email_container);
        editText=findViewById(R.id.feedback_input);
        save=findViewById(R.id.submit_feedback);
        backNav.setOnClickListener(e->{
            finish();
        });
        save.setOnClickListener(v -> {
            validateAndSave();
        });
    }

    private void validateAndSave() {
        boolean isvalid = true;
        inputLayout.setError(null);
        if(editText.getText().toString().isEmpty()){
            inputLayout.setError("Enter Text");
        isvalid=false;
        }
        if(isvalid){
            Feedback feedBack = Feedback.builder().build();
            uploadToFirebase(feedBack);
        }

    }

    private void uploadToFirebase(Feedback feedBack) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        User user = new User();
        user.setEmail(auth.getCurrentUser().getEmail());
        feedBack.setUser(user);
        db.collection("feedback").add(feedBack).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Helpers.showPopupWindow(this,"Feedback Saved",2000, Color.GREEN,Color.WHITE);

            }
        });
    }
}
