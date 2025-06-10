package com.example.myapplication;

public class Product {
    private String pid;
    private String pname;
    private float cost;

    public Product(String pid, String pname, float cost) {
        this.pid = pid;
        this.pname = pname;
        this.cost = cost;
    }

    public String getPid() {
        return pid;
    }

    public String getPname() {
        return pname;
    }

    public float getCost() {
        return cost;
    }
}
