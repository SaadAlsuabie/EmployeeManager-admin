package com.adminems.adminui;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.adminems.R;
import com.adminems.adminui.actions.ActionsUpdateFragment;
import com.adminems.adminui.add.AddNewEmployeeFragment;
import com.adminems.adminui.settings.SettingsFragment;
import com.adminems.adminui.viewall.ViewAllEmployeesFragment;
import com.adminems.adminui.viewall.ViewEmployeeByIdFragment;
import com.adminems.employee.data.EmployeeDataService;

public class DashboardFragment extends Fragment {
    private LinearLayout apiConnected, apiDisconnected;
    private boolean isApiOnline;
    private ProgressBar progressBar;
    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        CardView addEmployee = view.findViewById(R.id.cardAddEmployee);
        CardView ViewEmployees = view.findViewById(R.id.cardViewEmployees);
        CardView actions = view.findViewById(R.id.cardActions);
        CardView settings = view.findViewById(R.id.cardSettings);

        apiConnected = view.findViewById(R.id.statusConnected);
        apiDisconnected = view.findViewById(R.id.statusDisconnected);
        progressBar = view.findViewById(R.id.progressBar);
        apiConnected.setVisibility(View.VISIBLE);

        checkApiStatus();

        addEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new AddNewEmployeeFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });


        ViewEmployees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new ViewAllEmployeesFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });


        actions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new ActionsUpdateFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new SettingsFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;

    }
    private void checkApiStatus() {
        new android.os.Handler().postDelayed(() -> {

            EmployeeDataService emds = new EmployeeDataService(getContext());

            emds.healthCheck(new EmployeeDataService.SimpleResponseInterface() {
                @Override
                public void onError(String message) {
                    isApiOnline = false;
                    progressBar.setVisibility(View.VISIBLE);
                    apiDisconnected.setVisibility(View.VISIBLE);
                }

                @Override
                public void onResponse(String message) {
                    isApiOnline = true;
                    progressBar.setVisibility(View.VISIBLE);
                    apiConnected.setVisibility(View.VISIBLE);
                }
            });

        }, 3000);
    }
}