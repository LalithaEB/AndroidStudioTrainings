package com.example.employeeinformationapplication;

public class EmployeeModel {
    private int empNo;
    private String empName;
    private String designation;
    private int salary;

    public EmployeeModel(int empNo, String empName, String designation, int salary) {
        this.empNo = empNo;
        this.empName = empName;
        this.designation = designation;
        this.salary = salary;
    }
    public int getEmpNo() {
        return empNo;
    }
    public String getEmpName() {
        return empName;
    }
    public String getDesignation() {
        return designation;
    }
    public int getSalary() {
        return salary;
    }

}
