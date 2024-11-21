package com.adminems.adminui.actions;

import android.os.Bundle;

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

public class ActionsDeleteFragment extends Fragment {
    private String ID;
    private ImageView ivSearchIcon;
    private Button btnDelete, navigateToDeleteButton;
    private LinearLayout successMessageLayout, errorMessageLayout;
    private TextView tvSuccessMessage, tvErrorMessage;

    public ActionsDeleteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_actions_delete, container, false);

        View view = inflater.inflate(R.layout.fragment_actions_delete, container, false);
        successMessageLayout = view.findViewById(R.id.successMessageLayout);
        tvSuccessMessage = view.findViewById(R.id.tvSuccessMessage);
        errorMessageLayout = view.findViewById(R.id.errorMessageLayout);
        tvErrorMessage = view.findViewById(R.id.tvErrorMessage);

        navigateToDeleteButton = view.findViewById(R.id.btnUpdate);
        ivSearchIcon = view.findViewById(R.id.ivSearchIcon);
        btnDelete = view.findViewById(R.id.btn_delete_employee);

        ivSearchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDelete.setVisibility(view.VISIBLE);
                EditText etID = view.findViewById(R.id.et_dlt_employee_id);
                TextView tvDetails = view.findViewById(R.id.tv_dlt_employee_info);

                String employee_id_str = etID.getText().toString();
                ID = employee_id_str;
                int employee_id = Integer.parseInt(employee_id_str);
                EmployeeDataService employee_data = new EmployeeDataService(getContext());

                employee_data.getEmployeeById(employee_id, new EmployeeDataService.SingleEmployeeResponseInterface() {
                    @Override
                    public void onError(String message) {
                        tvDetails.setText(message);
                    }

                    @Override
                    public void onResponse(EmployeeModel employeeModel) {
                        tvDetails.setText(employeeModel.toString());
                    }
                });

                }
        });

        navigateToDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new ActionsUpdateFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int employee_id = Integer.parseInt(ID);
                EmployeeDataService employee_data = new EmployeeDataService(getContext());

                employee_data.deleteEmployee(employee_id, new EmployeeDataService.SimpleResponseInterface() {
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