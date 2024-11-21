package com.adminems.employee.model;
public class EmployeeModel {

    private int id;
    private String department;
    private String email;
    private String first_name;
    private String last_name;
    private String joiningdate;
    private int salary;
    private String FullName;

    public String getFullName() {
        return first_name + " " + last_name;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public EmployeeModel() {
    }

    public EmployeeModel(int id, String first_name, String last_name, String email, String department, String joiningdate, int salary) {
        this.id = id;
        this.department = department;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.joiningdate = joiningdate;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                "\nFull Name: " + getFullName() +
                "\nEmail: " + email +
                "\nDepartment: " + department +
                "\nSalary: " + salary +
                "\nJoining Date: " + joiningdate ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getJoiningdate() {
        return joiningdate;
    }

    public void setJoiningdate(String joiningdate) {
        this.joiningdate = joiningdate;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}

