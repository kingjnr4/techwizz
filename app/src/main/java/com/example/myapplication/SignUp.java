package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.helpers.Helpers;
import com.example.myapplication.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.*;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    TextView backNav;
    View signInLink;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private TextInputLayout usernameContainer, emailContainer, phoneContainer, passwordContainer;
    private TextInputEditText usernameField, emailField, phoneField, passwordField;
    private Button signUpButton, googleSignInButton;
   private  Intent mainActivityIntent;


    protected void onCreate(Bundle savedInstanceState) {
        mainActivityIntent= new Intent(SignUp.this, MainActivity.class);
        mAuth = FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestIdToken(getString(R.string.default_web_client_id)).
                requestProfile().build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);

        usernameContainer = findViewById(R.id.username_container);
        emailContainer = findViewById(R.id.email_container);
        phoneContainer = findViewById(R.id.phone_container);
        passwordContainer = findViewById(R.id.password_container);
        usernameField = findViewById(R.id.username_field);
        emailField = findViewById(R.id.email_field);
        phoneField = findViewById(R.id.phone_no_field);
        passwordField = findViewById(R.id.password_field);
        signUpButton = findViewById(R.id.sign_up_btn);
        googleSignInButton = findViewById(R.id.google_sign_in_button);
        backNav = findViewById(R.id.fix_back_label);
        signInLink = findViewById(R.id.signUpLink);
        signUpButton.setOnClickListener(e -> {
            validateAndSignUp();
        });
        backNav.setOnClickListener(e -> {
            finish();
        });
        signInLink.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp.this, SignIn.class);
            startActivity(intent);
            finish();
        });
        googleSignInButton.setOnClickListener(v -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
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
            User user = User.builder().email(emailField.getText().toString())
                    .username(usernameField.getText().toString())
                    .phone(usernameField.getText().toString()).build();
          signUpWithEmailAndPassword(user,passwordField.getText().toString());
        }
    }

    private void signUpWithEmailAndPassword(User user,String password) {
         mAuth.createUserWithEmailAndPassword(user.getEmail(), password)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()){
                        FirebaseUser authCurrentUser = mAuth.getCurrentUser();
                        if (authCurrentUser != null) {
                            db.collection("users").document(user.getEmail()).set(user).addOnCompleteListener(task1 ->{
                                startActivity(mainActivityIntent);
                                finish();
                            } );
                        }
                    }
                    else {
                        // User authentication failed, handle the error
                        Exception exception = task.getException();
                        if (exception instanceof FirebaseAuthException authException) {
                            String errorCode = authException.getErrorCode();
                            if (errorCode.equals("ERROR_EMAIL_ALREADY_IN_USE")) {
                                Helpers.showPopupWindow(this,"Email Is In Use",2000,Color.rgb(255,36,0),Color.WHITE);
                            }else if (errorCode.equals("ERROR_WEAK_PASSWORD")){
                                Helpers.showPopupWindow(this,"Password Is Weak",2000,Color.rgb(255,204,0),Color.WHITE);
                            }
                        }
                    }
                });
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
            Helpers.makeShortToast(SignUp.this, "Error signing in with google");
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
                               finish();
                           } );
                        }
                    }
                });
    }
}

