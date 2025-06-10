package com.example.myapplication;

public class Customer {
    private String cid;
    private String cname;

    public Customer(String cid, String cname) {
        this.cid = cid;
        this.cname = cname;
    }

    public String getCid() {
        return cid;
    }

    public String getCname() {
        return cname;
    }
}
