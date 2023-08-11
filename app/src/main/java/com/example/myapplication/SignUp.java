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

public class SignUp extends AppCompatActivity {

    private TextInputLayout usernameContainer, emailContainer, phoneContainer, passwordContainer;
    private TextInputEditText usernameField, emailField, phoneField, passwordField;
    private Button signUpButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        usernameContainer = findViewById(R.id.username_container);
        emailContainer = findViewById(R.id.email_container);
        phoneContainer = findViewById(R.id.phone_container);
        passwordContainer = findViewById(R.id.password_container);
        usernameField = findViewById(R.id.username_field);
        emailField = findViewById(R.id.email_field);
        phoneField = findViewById(R.id.phone_no_field);
        passwordField = findViewById(R.id.password_field);
        signUpButton = findViewById(R.id.sign_up_btn);


        signUpButton.setOnClickListener(e-> {
            validateAndSignUp();
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView backNav = findViewById(R.id.fix_back_label);

        backNav.setOnClickListener(e->{
            finish();
        });

        View signInLink = findViewById(R.id.signUpLink);
        signInLink.setOnClickListener(v -> {

            Intent intent = new Intent(SignUp.this, SignIn.class);
            startActivity(intent);
        });
    }

    private void validateAndSignUp() {
        // Reset errors
        usernameContainer.setError(null);
        emailContainer.setError(null);
        phoneContainer.setError(null);
        passwordContainer.setError(null);

        // Validate input fields
        boolean isValid = true;

        String email = emailField.getText().toString().trim();

        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        if (usernameField.getText().toString().isEmpty()) {
            usernameContainer.setError("Username is required");
            isValid = false;
        }

        if (email.isEmpty()) {
            emailContainer.setError("Email address is required");
            isValid = false;
        } else if (!matcher.matches()) {
            emailContainer.setError("Invalid email address");
            isValid = false;
        }

        if (phoneField.getText().toString().isEmpty()) {
            phoneContainer.setError("Mobile number is required");
            isValid = false;
        }

        if (passwordField.getText().toString().isEmpty()) {
            passwordContainer.setError("Password is required");
            isValid = false;
        }

        if (isValid) {
            // Perform sign-up logic

            Intent intent = new Intent(SignUp.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
