package com.example.admin;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.admin.Model.Admin;
import com.example.admin.enums.Role;
import com.example.admin.helpers.Helpers;
import com.example.admin.helpers.SessionManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import org.signal.argon2.Argon2Exception;

public class AddAdminActivity extends AppCompatActivity {
    private TextInputLayout usernameContainer,passwordContainer;
    private TextInputEditText usernameFiled,passwordField;
    private Button createAdminBtn;
    private FirebaseFirestore db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_admin);
        db=FirebaseFirestore.getInstance();
        usernameContainer=findViewById(R.id.username_container);
        usernameFiled=findViewById(R.id.username_field);
        passwordContainer=findViewById(R.id.password_container);
        passwordField=findViewById(R.id.password_field);
        createAdminBtn=findViewById(R.id.sign_in_button);
        ImageView backNav = findViewById(R.id.back_nav);
        SessionManager sessionManager = new SessionManager(this);
        if (sessionManager.getRole()!= Role.SUPER_ADMIN){
            finish();
        }
        backNav.setOnClickListener(e->{
            finish();
        });
        createAdminBtn.setOnClickListener(click->{
            validateAndSubmit();
        });
    }

    private void validateAndSubmit() {
        usernameContainer.setError(null);
        passwordContainer.setError(null);
        boolean isvalid = true;
        if(usernameFiled.getText().toString().isEmpty()){
            usernameContainer.setError("Username is required");
            isvalid=false;
        }
        if(passwordField.getText().toString().isEmpty()){
            passwordContainer.setError("Username is required");
            isvalid=false;
        }
        if(isvalid){
            createAdmin(usernameFiled.getText().toString(),passwordField.getText().toString());
        }
    }

    private void createAdmin(String username,String password) {
        CollectionReference adminRef = db.collection("admins");
    adminRef.whereEqualTo("username",username).get().addOnCompleteListener(task -> {
       if (task.isSuccessful())
           if(task.getResult().isEmpty()){
               try {
                   Admin admin = Admin.builder()
                           .username(username)
                           .password(Helpers.encrypt(password))
                           .role(Role.ADMIN)
                           .build();
                 adminRef.add(admin).addOnCompleteListener(task1 -> {
                     if (task1.isSuccessful()){
                         Helpers.showPopupWindow(this,"Admin Saved",2000, Color.GREEN,Color.WHITE);
                     }
                 });
               } catch (Argon2Exception ignored) {

               }
           }else{
               Helpers.showPopupWindow(this,"Error Saving Admin",3000, Color.YELLOW,Color.WHITE);
           }
    });
    }
}