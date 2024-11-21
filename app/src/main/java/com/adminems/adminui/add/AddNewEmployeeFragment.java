package com.adminems.adminui.add;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adminems.R;
import com.adminems.employee.data.EmployeeDataService;
import com.adminems.employee.model.EmployeeModel;


public class AddNewEmployeeFragment extends Fragment {

    private Button btnAddEmployee;
    private LinearLayout successMessageLayout, errorMessageLayout;
    private TextView tvSuccessMessage, tvErrorMessage;

    public AddNewEmployeeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_employee, container, false);

        successMessageLayout = view.findViewById(R.id.successMessageLayout);
        tvSuccessMessage = view.findViewById(R.id.tvSuccessMessage);
        errorMessageLayout = view.findViewById(R.id.errorMessageLayout);
        tvErrorMessage = view.findViewById(R.id.tvErrorMessage);

        EditText et_first_name = view.findViewById(R.id.etFirstName);
        EditText et_last_name = view.findViewById(R.id.etLastName);
        EditText et_email = view.findViewById(R.id.etEmail);
        EditText et_department = view.findViewById(R.id.etDepartment);
        EditText et_salary = view.findViewById(R.id.etSalary);
        EditText et_joining_date = view.findViewById(R.id.etJoiningDate);


        btnAddEmployee = view.findViewById(R.id.btnSubmit);

        btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String f_name = et_first_name.getText().toString();
                String l_name = et_last_name.getText().toString();
                String email = et_email.getText().toString();
                String dpt = et_department.getText().toString();
                int salary = Integer.parseInt(et_salary.getText().toString());
                String j_date = et_joining_date.getText().toString();

                EmployeeModel em = new EmployeeModel();
                EmployeeDataService ems = new EmployeeDataService(requireContext());

                em.setFirst_name(f_name);
                em.setLast_name(l_name);
                em.setEmail(email);
                em.setDepartment(dpt);
                em.setSalary(salary);
                em.setJoiningdate(j_date);

                ems.addEmployee(em, new EmployeeDataService.SimpleResponseInterface() {
                    @Override
                    public void onError(String message) {
                        tvErrorMessage.setText(message);
                        errorMessageLayout.setVisibility(view.VISIBLE);
                    }

                    @Override
                    public void onResponse(String message) {
                        tvSuccessMessage.setText(message);
                        successMessageLayout.setVisibility(view.VISIBLE);
                    }
                });

            }
        });



        return view;
    }
}