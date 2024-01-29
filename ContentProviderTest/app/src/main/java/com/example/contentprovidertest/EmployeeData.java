package com.example.contentprovidertest;

public class EmployeeData {
    String id, name, dept;

    EmployeeData(String id,
                 String name,
                 String dept) {
        this.id = id;
        this.name = name;
        this.dept = dept;
    }

    public String getId() {
        return id;
    }

    public String getName() { return name; }

    public String getDept() {
        return dept;
    }
}

