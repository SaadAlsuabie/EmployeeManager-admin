package com.adminems.adminui.actions;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adminems.R;
import com.adminems.employee.data.EmployeeDataService;
import com.adminems.employee.model.EmployeeModel;

public class ActionsUpdateFragment extends Fragment {
    private Button btnEditEmployee, btnSaveEmployee;
    private LinearLayout employeeDetailsLayout, editEmployeeDetails;
    private TextView tvSuccessMessage, employeeInfoTextView, tvErrorMessage;
    private LinearLayout successMessageLayout, errorMessageLayout;
    private ImageView ivSearchIcon;
    private Button btnUpdate, btnDelete;

    private String FName, LName, Email, Dpt, JDate, ID;
    private int Salary;

    public ActionsUpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_actions_update, container, false);

        Button navigateToDeleteButton = view.findViewById(R.id.btnDelete);

        EditText inputTextID = view.findViewById(R.id.etEmployeeId);
        employeeInfoTextView = view.findViewById(R.id.tvEmployeeInfo);
        ivSearchIcon = view.findViewById(R.id.ivSearchIcon);

        employeeDetailsLayout = view.findViewById(R.id.employeeDetailsLayout);
        editEmployeeDetails = view.findViewById(R.id.editEmployeeLayout);

        btnEditEmployee = view.findViewById(R.id.btn_edit_employee);

        EditText et_first_name = view.findViewById(R.id.et_update_first_name);
        EditText et_last_name = view.findViewById(R.id.et_update_last_name);
        btnSaveEmployee = view.findViewById(R.id.btn_update_save_employee);
        EditText et_email = view.findViewById(R.id.et_email);
        EditText et_department = view.findViewById(R.id.et_update_department);
        EditText et_salary = view.findViewById(R.id.et_update_salary);
        EditText et_joining_date = view.findViewById(R.id.et_joining_date);

        btnSaveEmployee = view.findViewById(R.id.btn_update_save_employee);

        successMessageLayout = view.findViewById(R.id.successMessageLayout);
        tvSuccessMessage = view.findViewById(R.id.tvSuccessMessage);
        errorMessageLayout = view.findViewById(R.id.errorMessageLayout);
        tvErrorMessage = view.findViewById(R.id.tvErrorMessage);

        navigateToDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new ActionsDeleteFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        ivSearchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String employee_id_str = inputTextID.getText().toString();
                ID = employee_id_str;
                int employee_id = Integer.parseInt(employee_id_str);

                EmployeeDataService employee_data = new EmployeeDataService(getContext());

                employee_data.getEmployeeById(employee_id, new EmployeeDataService.SingleEmployeeResponseInterface() {
                    @Override
                    public void onError(String message) {
                        tvErrorMessage.setText(message);
                        errorMessageLayout.setVisibility(view.VISIBLE);
                    }

                    @Override
                    public void onResponse(EmployeeModel employeeModel) {

                        FName = employeeModel.getFirst_name();
                        LName =  employeeModel.getLast_name();
                        Email = employeeModel.getEmail();
                        Dpt = employeeModel.getDepartment();
                        JDate = employeeModel.getJoiningdate();
                        Salary = employeeModel.getSalary();

                        employeeInfoTextView.setText(employeeModel.toString());
                        btnEditEmployee.setVisibility(view.VISIBLE);
                    }
                });
            }
        });

        btnEditEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employeeDetailsLayout.setVisibility(view.GONE);
                editEmployeeDetails.setVisibility(view.VISIBLE);
                et_first_name.setText(FName);
                et_last_name.setText(LName);
                et_email.setText(Email);
                et_department.setText(Dpt);
                et_salary.setText(String.valueOf(Salary));
                et_joining_date.setText(JDate);
            }
        });

        btnSaveEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(ID);
                String f_name = et_first_name.getText().toString();
                String l_name = et_last_name.getText().toString();
                String email = et_email.getText().toString();
                String dpt = et_department.getText().toString();
                int salary = Integer.parseInt(et_salary.getText().toString());
                String j_date = et_joining_date.getText().toString();

                EmployeeModel em = new EmployeeModel();
                EmployeeDataService ems = new EmployeeDataService(requireContext());

                em.setId(id);
                em.setFirst_name(f_name);
                em.setLast_name(l_name);
                em.setEmail(email);
                em.setDepartment(dpt);
                em.setSalary(salary);
                em.setJoiningdate(j_date);

                ems.updateEmployee(id, em, new EmployeeDataService.SingleEmployeeResponseInterface() {
                    @Override
                    public void onError(String message) {
                        tvErrorMessage.setText(message);
                        errorMessageLayout.setVisibility(view.VISIBLE);
                    }

                    @Override
                    public void onResponse(EmployeeModel employeeModel) {
                        editEmployeeDetails.setVisibility(view.GONE);
                        employeeDetailsLayout.setVisibility(view.VISIBLE);

                        employeeInfoTextView.setText(employeeModel.toString());

                        tvSuccessMessage.setText("Employee Record successfully updated!");
                        successMessageLayout.setVisibility(view.VISIBLE);
                    }
                });
            }
        });

        return view;
    }
}