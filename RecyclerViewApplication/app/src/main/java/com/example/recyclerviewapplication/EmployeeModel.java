package com.example.recyclerviewapplication;

public class EmployeeModel {
    private int empNo;
    private String empName;
    private double salary;

    public EmployeeModel(int empNo, String empName, double salary) {
        this.empNo = empNo;
        this.empName = empName;
        this.salary = salary;
    }

    public int getEmpNo() {
        return empNo;
    }

    public String getEmpName() {
        return empName;
    }

    public double getSalary() {
        return salary;
    }
}
