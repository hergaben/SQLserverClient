package com.sqlserverclient;

public class PolyModelClass {

    String name;
    String address;
    String detail;
    String department;
    String date;
    String s_work;
    String e_work;
    String id;

    public PolyModelClass(String name, String address, String detail, String department, String date, String s_work, String e_work, String id) {
        this.name = name;
        this.address = address;
        this.detail = detail;
        this.department = department;
        this.date = date;
        this.s_work = s_work;
        this.e_work = e_work;
        this.id = id;
    }

    public PolyModelClass() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getS_work() {
        return s_work;
    }

    public void setS_work(String s_work) {
        this.s_work = s_work;
    }

    public String getE_work() {
        return e_work;
    }

    public void setE_work(String e_work) {
        this.e_work = e_work;
    }
}
