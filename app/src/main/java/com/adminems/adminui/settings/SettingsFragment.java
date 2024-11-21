package com.adminems.adminui.settings;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.adminems.MainActivity;
import com.adminems.R;
import com.adminems.adminui.viewall.ViewAllEmployeesFragment;
import com.adminems.auth.LoginActivity;

public class SettingsFragment extends Fragment {

    private Switch switchNotifications;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        switchNotifications = view.findViewById(R.id.switchNotifications);

        LinearLayout navigateToAccDetails = view.findViewById(R.id.accountDetails);
        LinearLayout btnLogout = view.findViewById(R.id.btnLogout);

        sharedPreferences = getContext().getSharedPreferences("AppSettings", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        boolean isNotificationsEnabled = sharedPreferences.getBoolean("notificationsEnabled", true);
        switchNotifications.setChecked(isNotificationsEnabled);

        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                enableNotifications();
            } else {
                disableNotifications();
            }
            editor.putBoolean("notificationsEnabled", isChecked);
            editor.apply();
        });

        navigateToAccDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new SettingsAccountDetailsFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Successfully logged out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void enableNotifications() {
        Toast.makeText(getContext(), "Notifications Enabled", Toast.LENGTH_SHORT).show();
    }

    private void disableNotifications() {
        Toast.makeText(getContext(), "Notifications Disabled", Toast.LENGTH_SHORT).show();
    }
}