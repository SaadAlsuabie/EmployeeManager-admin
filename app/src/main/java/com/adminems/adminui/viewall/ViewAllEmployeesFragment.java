package com.adminems.adminui.viewall;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.adminems.R;
import com.adminems.employee.adapter.EmployeeAdapter;
import com.adminems.employee.data.EmployeeDataService;
import com.adminems.employee.model.EmployeeModel;

import java.util.ArrayList;
import java.util.List;


public class ViewAllEmployeesFragment extends Fragment {
    private ListView lvEmployees;
    private Button emById;

    private LinearLayout successMessageLayout, errorMessageLayout;
    private TextView tvSuccessMessage, tvErrorMessage;


    public ViewAllEmployeesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_all_employees, container, false);

        Button navigateToViewByID = view.findViewById(R.id.btnViewById);
        lvEmployees = view.findViewById(R.id.lvEmployees);

        successMessageLayout = view.findViewById(R.id.successMessageLayout);
        tvSuccessMessage = view.findViewById(R.id.tvSuccessMessage);
        errorMessageLayout = view.findViewById(R.id.errorMessageLayout);
        tvErrorMessage = view.findViewById(R.id.tvErrorMessage);


        navigateToViewByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new ViewEmployeeByIdFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });


        EmployeeDataService emds = new EmployeeDataService(getContext());

        emds.getAllEmployeeDetails(new EmployeeDataService.EmployeeDataResponseInterface() {
            @Override
            public void onError(String Message) {
                tvErrorMessage.setText(Message);
                errorMessageLayout.setVisibility(view.VISIBLE);
            }

            @Override
            public void onResponse(List<EmployeeModel> employeeModelList) {

                EmployeeAdapter adapter = new EmployeeAdapter(getContext(), employeeModelList);
                lvEmployees.setAdapter(adapter);

            }
        });


        return view;
    }
}