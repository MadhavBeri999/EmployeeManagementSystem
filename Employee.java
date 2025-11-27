package com.madhav.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empid")
    private Integer empid;

    @Column(name = "empname")
    private String empname;

    @Column(name = "empsalary")
    private int empsalary;

    @Column(name = "empage")
    private int empage;

    @Column(name = "empcity")
    private String empcity;

    public Employee() {}

    // Getters and Setters
    public Integer getEmpid() {
        return empid;
    }

    public void setEmpid(Integer empid) {
        this.empid = empid;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public int getEmpsalary() {
        return empsalary;
    }

    public void setEmpsalary(int empsalary) {
        this.empsalary = empsalary;
    }

    public int getEmpage() {
        return empage;
    }

    public void setEmpage(int empage) {
        this.empage = empage;
    }

    public String getEmpcity() {
        return empcity;
    }

    public void setEmpcity(String empcity) {
        this.empcity = empcity;
    }
}
