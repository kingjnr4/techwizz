package com.example.admin.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.admin.MainActivity;
import com.example.admin.Model.Admin;
import com.example.admin.R;
import com.example.admin.SignIn;
import com.example.admin.helpers.Helpers;
import com.example.admin.helpers.SessionManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import org.signal.argon2.Argon2Exception;
import org.signal.argon2.UnknownTypeException;

public class ChangePasswordDialog extends AppCompatDialogFragment {
    private FirebaseFirestore db;
    private TextInputLayout oldPassContainer;
    private TextInputEditText oldPassField;

    private TextInputLayout newPassContainer;
    private TextInputEditText newPassField;

    private TextInputLayout confirmPassContainer;
    private TextInputEditText confirmPassField;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        db=FirebaseFirestore.getInstance();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_password, null);
        oldPassContainer=view.findViewById(R.id.old_password_container);
        oldPassField=view.findViewById(R.id.old_password);
        newPassContainer=view.findViewById(R.id.new_password_container);
        newPassField=view.findViewById(R.id.new_password);
        confirmPassContainer=view.findViewById(R.id.confirm_new_password_container);
        confirmPassField=view.findViewById(R.id.confirm_new_password);
        builder.setView(view)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        validateAndSave();
                    }
                });

        return builder.create();
    }

    private void changePassword(Activity context, String oldpass, String newpass){
        SessionManager sessionManager = new SessionManager(getActivity());
        db.collection("admins").whereEqualTo("username",sessionManager.getUserName().toString()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot snapshot = task.getResult();
                if(!snapshot.isEmpty()){
                    Admin admin = snapshot.getDocuments().get(0).toObject(Admin.class);
                    try {
                        if (admin!=null && Helpers.verify(admin.getPassword(),oldpass)){
                            admin.setPassword(Helpers.encrypt(newpass));
                            db.collection("admins").document(admin.getId()).set(admin).addOnCompleteListener(task1 -> {
                                if (task.isSuccessful()){
                                    Helpers.showPopupWindow(context,"Password Changed",2000,Color.GREEN,Color.WHITE);
                                }
                                else{
                                    Helpers.showPopupWindow(context,"Error Occurred",2000, Color.YELLOW, Color.WHITE);

                                }
                            });
                        }
                        else {
                            Helpers.showPopupWindow(context,"Invalid Credentials",2000, Color.YELLOW, Color.WHITE);
                        }
                    } catch (UnknownTypeException | Argon2Exception ignored) {

                    }
                }
                else {
                    Helpers.showPopupWindow(context,"Invalid Credentials",2000,Color.YELLOW, Color.WHITE);
                }
            }
        });
        ;
    }

    private void validateAndSave(){
        confirmPassContainer.setError(null);
        oldPassContainer.setError(null);
        oldPassField.setError(null);
        boolean isValid = true;

        if (oldPassField.getText().toString().isEmpty()) {
            Helpers.showPopupWindow(getActivity(),"Old Password Is Required",2000, Color.YELLOW, Color.WHITE);
            return;
        }
        if(newPassField.getText().toString().isEmpty()){
            Helpers.showPopupWindow(getActivity(),"New Password Is Required",2000, Color.YELLOW, Color.WHITE);
            return;
        }
        if(confirmPassField.getText().toString().isEmpty()){
            Helpers.showPopupWindow(getActivity(),"Confirm Password Is Required",2000, Color.YELLOW, Color.WHITE);
            return;
        }
        if(!confirmPassField.getText().toString().equals(newPassField.getText().toString())){
            Helpers.showPopupWindow(getActivity(),"New Password Should Match",2000, Color.YELLOW, Color.WHITE);
            isValid =false;
        }
        if (isValid){
            changePassword(getActivity(),oldPassField.getText().toString(),newPassField.getText().toString());
        }
    }
}
