package com.example.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignIn extends AppCompatActivity {

    private TextInputLayout emailContainer, passwordContainer;
    private TextInputEditText usernameField, passwordField;
    private Button signInButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);


        emailContainer = findViewById(R.id.email_container);
        passwordContainer = findViewById(R.id.password_container);
        usernameField = findViewById(R.id.username_field);
        passwordField = findViewById(R.id.password_field);
        signInButton = findViewById(R.id.sign_in_button);

        signInButton.setOnClickListener(e->{
            validateAndSignIn();
        });

    }

    private void validateAndSignIn() {
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
            // Perform sign-in logic

            Intent intent = new Intent(SignIn.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
