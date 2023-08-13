package com.example.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.admin.Model.Admin;
import com.example.admin.helpers.Helpers;
import com.example.admin.helpers.SessionManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import org.signal.argon2.Argon2Exception;
import org.signal.argon2.UnknownTypeException;

public class SignIn extends AppCompatActivity {

    private TextInputLayout emailContainer, passwordContainer;
    private FirebaseFirestore db;
    private TextInputEditText usernameField, passwordField;
    private Button signInButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        db=FirebaseFirestore.getInstance();

        emailContainer = findViewById(R.id.email_container);
        passwordContainer = findViewById(R.id.password_container);
        usernameField = findViewById(R.id.username_field);
        passwordField = findViewById(R.id.password_field);
        signInButton = findViewById(R.id.sign_in_button);

        signInButton.setOnClickListener(e->{
            validateAndSignIn();
        });

    }

    private void validateAndSignIn()  {
        // Reset errors
        emailContainer.setError(null);
        passwordContainer.setError(null);

        // Validate input fields
        boolean isValid = true;

        if (usernameField.getText().toString().isEmpty()) {
            emailContainer.setError("Email address is required");
            isValid = false;
        }

        if (passwordField.getText().toString().isEmpty()) {
            passwordContainer.setError("Password is required");
            isValid = false;
        }

        if (isValid) {
            db.collection("admins").whereEqualTo("username",usernameField.getText().toString()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    QuerySnapshot snapshot = task.getResult();
                    if(!snapshot.isEmpty()){
                        Admin admin = snapshot.getDocuments().get(0).toObject(Admin.class);
                        try {
                            if (admin!=null && Helpers.verify(admin.getPassword(),passwordField.getText().toString())){
                                new SessionManager(this).createSession(admin.getId(),admin.getUsername());
                                Intent intent = new Intent(SignIn.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Helpers.showPopupWindow(this,"Invalid Credentials",2000,Color.YELLOW, Color.WHITE);
                            }
                        } catch (UnknownTypeException ignored) {

                        }
                    }
                    else {
                        Helpers.showPopupWindow(this,"Invalid Credentials",2000,Color.YELLOW, Color.WHITE);
                    }
                }
            });
            ;
        }
    }
}