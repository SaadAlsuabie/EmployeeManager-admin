package com.adminems.employee.data;


import android.content.Context;
import android.util.Log;

import com.adminems.employee.model.EmployeeModel;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDataService {
    Context context;
    final String BASE_URL = "http://10.224.41.11/comp2000";

    public EmployeeDataService(Context context) {
        this.context = context;
    }

    public void getEmployeeDetails(){

    }
    public interface EmployeeDataResponseInterface{
        void onError(String Message);
        void onResponse(List<EmployeeModel> employeeModelList);
    }

    public interface SingleEmployeeResponseInterface {
        void onError(String message);
        void onResponse(EmployeeModel employeeModel);
    }

    public interface SimpleResponseInterface {
        void onError(String message);
        void onResponse(String message);
    }
    public void getAllEmployeeDetails( EmployeeDataResponseInterface employeeDataResponseInterface){
        List<EmployeeModel> employeeModelList = new ArrayList<>();
        String url = BASE_URL + "/employees";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                EmployeeModel em = new EmployeeModel();
                                JSONObject employeeData = response.getJSONObject(i);

                                String firstName = employeeData.getString("firstname");
                                String lastName = employeeData.getString("lastname");
                                String department = employeeData.getString("department");
                                String email = employeeData.getString("email");
                                int salary = employeeData.getInt("salary");
                                String joiningDate = employeeData.getString("joiningdate");

                                em.setId(employeeData.getInt("id"));
                                em.setDepartment(department);
                                em.setFirst_name(firstName);
                                em.setLast_name(lastName);
                                em.setEmail(email);
                                em.setSalary(salary);
                                em.setJoiningdate(joiningDate);
                                employeeModelList.add(em);
                            }
                            employeeDataResponseInterface.onResponse(employeeModelList);

                        } catch (JSONException e) {
                            employeeDataResponseInterface.onError("Error parsing response");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        employeeDataResponseInterface.onError("An error occurred");
                    }
                });
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public void getEmployeeById(int id, SingleEmployeeResponseInterface employeeResponseInterface) {
        String url = BASE_URL + "/employees/get/" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            EmployeeModel em = new EmployeeModel();

                            em.setId(response.getInt("id"));
                            em.setDepartment(response.getString("department"));
                            em.setFirst_name(response.getString("firstname"));
                            em.setLast_name(response.getString("lastname"));
                            em.setEmail(response.getString("email"));
                            em.setSalary(response.getInt("salary"));
                            em.setJoiningdate(response.getString("joiningdate"));

                            employeeResponseInterface.onResponse(em);
                        } catch (JSONException e) {
                            employeeResponseInterface.onError("Error parsing response");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        employeeResponseInterface.onError("An error occurred");
                    }
                });
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void addEmployee(EmployeeModel employee, SimpleResponseInterface responseInterface) {
        String url = BASE_URL + "/employees/add";

        JSONObject employeeData = new JSONObject();
        try {
            employeeData.put("firstname", employee.getFirst_name());
            employeeData.put("lastname", employee.getLast_name());
            employeeData.put("email", employee.getEmail());
            employeeData.put("department", employee.getDepartment());
            employeeData.put("salary", employee.getSalary());
            employeeData.put("joiningdate", employee.getJoiningdate());
        } catch (JSONException e) {
            responseInterface.onError("Error creating JSON data");
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, employeeData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        responseInterface.onResponse("Employee added successfully");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseInterface.onError("An error occurred while adding employee");
                    }
                });
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void updateEmployee(int id, EmployeeModel employee, SingleEmployeeResponseInterface responseInterface) {
        String url = BASE_URL+"/employees/edit/" + id;

        JSONObject employeeData = new JSONObject();
        try {
            employeeData.put("firstname", employee.getFirst_name());
            employeeData.put("lastname", employee.getLast_name());
            employeeData.put("email", employee.getEmail());
            employeeData.put("department", employee.getDepartment());
            employeeData.put("salary", employee.getSalary());
            employeeData.put("joiningdate", employee.getJoiningdate());
        } catch (JSONException e) {
            responseInterface.onError("Error creating JSON data");
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.PUT, url, employeeData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        responseInterface.onResponse(employee);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseInterface.onError("An error occurred while updating employee");
                    }
                });
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void deleteEmployee(int id, SimpleResponseInterface responseInterface) {
        String url = BASE_URL + "/employees/delete/" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        responseInterface.onResponse("Employee deleted successfully");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseInterface.onError("An error occurred while deleting employee");
                    }
                });
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void healthCheck(SimpleResponseInterface responseInterface) {
        String url = BASE_URL + "/health";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String exp_res = "API is working";

                        try {
                            String status_text = (String) response.get("status");

                            if (status_text.contains(exp_res)){
                                responseInterface.onResponse("API is working");
                            } else {
                                responseInterface.onError("API is not working");
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseInterface.onError("API is not working");
                    }
                });
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

}
