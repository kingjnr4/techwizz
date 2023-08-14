package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.dialog.ChangePasswordDialog;
import com.example.myapplication.dialog.EditPhoneNumberDialog;
import com.example.myapplication.dialog.EditUsernameDialog;

public class Settings extends AppCompatActivity {


    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        ImageView backNav = findViewById(R.id.back_nav);

        backNav.setOnClickListener(e->{
            finish();
        });

        LinearLayout editUsernameList = findViewById(R.id.edit_phone_username_list);
        LinearLayout ediPhoneNotList = findViewById(R.id.edit_phone_number_list);
        TextView changePasswordList = findViewById(R.id.change_password_list);

        editUsernameList.setOnClickListener(e->{
            openEditUsernameDialog();
        });

        ediPhoneNotList.setOnClickListener(e->{
            openEditPhoneNumberDialog();
        });

        changePasswordList.setOnClickListener(e->{
            openChangePasswordDialog();
        });

    }

    private void openEditUsernameDialog() {
        EditUsernameDialog editUsernameDialog = new EditUsernameDialog();
        editUsernameDialog.show(getSupportFragmentManager(), "Edit username");
    }

    private void openEditPhoneNumberDialog() {
        EditPhoneNumberDialog editPhoneNumberDialog = new EditPhoneNumberDialog();
        editPhoneNumberDialog.show(getSupportFragmentManager(), "Edit phone number");
    }

    private void openChangePasswordDialog() {
        ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog();
        changePasswordDialog.show(getSupportFragmentManager(), "Change Password");
    }
}
