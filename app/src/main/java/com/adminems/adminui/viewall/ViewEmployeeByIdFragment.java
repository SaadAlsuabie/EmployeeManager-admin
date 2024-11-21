package com.adminems.adminui.viewall;

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
import android.widget.Toast;

import com.adminems.R;
import com.adminems.employee.data.EmployeeDataService;
import com.adminems.employee.model.EmployeeModel;


public class ViewEmployeeByIdFragment extends Fragment {


    private ImageView search_icon;
    private LinearLayout errorMessageLayout;
    private TextView tvErrorMessage;
    private Button empAll, emByID;
    public ViewEmployeeByIdFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_employee_by_id, container, false);

        Button navigateToViewAll = view.findViewById(R.id.btnViewAll);
        EditText vID = view.findViewById(R.id.et_view_employee_by_id);
        TextView tvInfo = view.findViewById(R.id.tv_ve_employee_info);

        search_icon = view.findViewById(R.id.ivSearchIcon);
        errorMessageLayout = view.findViewById(R.id.errorMessageLayout);
        tvErrorMessage = view.findViewById(R.id.tvErrorMessage);

        navigateToViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new ViewAllEmployeesFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String employee_id_str = vID.getText().toString();
                int employee_id = Integer.parseInt(employee_id_str);

                EmployeeDataService employee_data = new EmployeeDataService(getContext());

                employee_data.getEmployeeById(employee_id, new EmployeeDataService.SingleEmployeeResponseInterface() {
                    @Override
                    public void onError(String message) {
                        tvErrorMessage.setText(message);
                        errorMessageLayout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(EmployeeModel employeeModel) {
                        tvInfo.setText(employeeModel.toString());
                    }
                });

            }
        });
        return view;
    }
}