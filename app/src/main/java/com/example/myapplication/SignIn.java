package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.helpers.Helpers;
import com.example.myapplication.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.*;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignIn extends AppCompatActivity {

    private TextInputLayout emailContainer, passwordContainer;
    private  Intent mainActivityIntent;
    private static final int RC_SIGN_IN = 123;
    private TextInputEditText emailField, passwordField;
    private Button signInButton,googleSignInButton;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        mainActivityIntent= new Intent(SignIn.this, MainActivity.class);
        mAuth = FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestIdToken(getString(R.string.default_web_client_id)).
                requestEmail().build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);
        View signInLink = findViewById(R.id.signUpLink);
        emailContainer = findViewById(R.id.email_container);
        passwordContainer = findViewById(R.id.password_container);
        emailField = findViewById(R.id.email_field);
        passwordField = findViewById(R.id.password_fields); // Note the corrected ID here
        signInButton = findViewById(R.id.sign_in_btn);
        googleSignInButton= findViewById(R.id.continue_google);
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

        googleSignInButton.setOnClickListener(v -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
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
            signInWithEmailAndPassword(email,password);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                signInWithFirebase(credential);
            }
        } catch (ApiException e) {
            Helpers.makeShortToast(SignIn.this, "Error signing in with google");
            Log.d("GoogleSignInError", e.getMessage());
        }
    }

    private void signInWithFirebase(AuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser authCurrentUser = mAuth.getCurrentUser();
                        if (authCurrentUser != null) {
                            User user = User.builder().username(authCurrentUser.getDisplayName())
                                    .email(authCurrentUser.getEmail())
                                    .phone(authCurrentUser.getPhoneNumber())
                                    .picture(authCurrentUser.getPhotoUrl().toString())
                                    .uid(authCurrentUser.getUid()).build();
                            db.collection("users").document(user.getEmail()).set(user).addOnCompleteListener(task1 ->{
                                startActivity(mainActivityIntent);
                            } );
                        }
                    }
                });
    }

    private void signInWithEmailAndPassword(String email,String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
           startActivity(mainActivityIntent);
            }
            else{
                Exception exception = task.getException();
                if (exception instanceof FirebaseAuthException authException) {
                    String errorCode = authException.getErrorCode();
                    if (errorCode.equals("ERROR_USER_NOT_FOUND") || errorCode.equals("ERROR_WRONG_PASSWORD")) {
                     Helpers.showPopupWindow(this,"Invalid Credentials",2000,Color.rgb(255,36,0),Color.WHITE);
                    }  else {
                        System.err.println("Authentication error: " + errorCode);
                    }
                }
            }
        });
    }
}
