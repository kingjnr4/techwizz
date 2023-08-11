package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignIn extends AppCompatActivity {

    private TextInputLayout emailContainer, passwordContainer;
    private TextInputEditText emailField, passwordField;
    private Button signInButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        View signInLink = findViewById(R.id.signUpLink);

        emailContainer = findViewById(R.id.email_container);
        passwordContainer = findViewById(R.id.password_container);
        emailField = findViewById(R.id.email_field);
        passwordField = findViewById(R.id.password_fields); // Note the corrected ID here
        signInButton = findViewById(R.id.sign_in_btn);

        signInButton.setOnClickListener(e-> {
            validateAndSignIn();
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView backNav = findViewById(R.id.fix_back_label);

        backNav.setOnClickListener(e->{
            finish();
        });

        signInLink.setOnClickListener(v -> {

            Intent intent = new Intent(SignIn.this, SignUp.class);
            startActivity(intent);
        });
    }

    private void validateAndSignIn() {
        emailContainer.setError(null);
        passwordContainer.setError(null);

        boolean isValid = true;

        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        if (email.isEmpty()) {
            emailContainer.setError("Email address is required");
            isValid = false;
        } else if (!matcher.matches()) {
            emailContainer.setError("Invalid email address");
            isValid = false;
        }

        if (password.isEmpty()) {
            passwordContainer.setError("Password is required");
            isValid = false;
        }

        if (isValid) {
            // Perform sign-in logic

            Intent intent = new Intent(SignIn.this, MainActivity.class);
            startActivity(intent);

        }
    }


};
