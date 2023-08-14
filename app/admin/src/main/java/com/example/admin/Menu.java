package com.example.admin;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.admin.helpers.SessionManager;

public class Menu extends Fragment {
    private TextView usernameField;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.menu, container, false);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        LinearLayout settingsRouteLink = view.findViewById(R.id.settings_route);
        LinearLayout addAdminRouteLink = view.findViewById(R.id.add_admin_route);
        LinearLayout adminRouteLink = view.findViewById(R.id.view_admin_route);
        LinearLayout contactusRouteLink = view.findViewById(R.id.contact_us_route);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        LinearLayout logoutRoutLink = view.findViewById(R.id.logout_route);
        usernameField=view.findViewById(R.id.username_field);
        SessionManager sessionManager = new SessionManager(getContext());
        usernameField.setText(sessionManager.getUserName());
        settingsRouteLink.setOnClickListener(e->{
            Intent intent = new Intent(view.getContext(), SettingsActivity.class);
            startActivity(intent);
        });

        addAdminRouteLink.setOnClickListener(e->{
            Intent intent = new Intent(view.getContext(), AddAdminActivity.class);
            startActivity(intent);
        });

        adminRouteLink.setOnClickListener(e->{
            Intent intent = new Intent(view.getContext(), AdminActivity.class);
            startActivity(intent);
        });

        contactusRouteLink.setOnClickListener(e->{
            Intent intent = new Intent(view.getContext(), Contactus.class);
            startActivity(intent);
        });

        logoutRoutLink.setOnClickListener(e->{
            new SessionManager(getActivity()).logout();
            Intent intent = new Intent(view.getContext(), SignIn.class);
            startActivity(intent);
        });

        return view;
    }
}
