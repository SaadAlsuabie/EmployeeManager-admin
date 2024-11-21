package com.adminems.employee.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.adminems.R;
import com.adminems.employee.model.EmployeeModel;

import java.util.List;

public class EmployeeAdapter extends ArrayAdapter<EmployeeModel> {

    private Context context;
    private List<EmployeeModel> employees;

    public EmployeeAdapter(Context context, List<EmployeeModel> employees) {
        super(context, R.layout.list_item_employee, employees);
        this.context = context;
        this.employees = employees;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item_employee, parent, false);
        }

        EmployeeModel employee = employees.get(position);

        TextView tvId = convertView.findViewById(R.id.tvId);
        TextView tvFullNames = convertView.findViewById(R.id.tvFullNames);
        TextView tvEmail = convertView.findViewById(R.id.tvEmail);
        TextView tvDepartment = convertView.findViewById(R.id.tvDepartment);

        tvId.setText(String.valueOf(employee.getId()));
        tvFullNames.setText(employee.getFullName());
        tvEmail.setText(employee.getEmail());
        tvDepartment.setText(employee.getDepartment());

        return convertView;
    }
}