package com.example.myapplication;

public class Sale {
    private String ordNo;
    private String cid;
    private String pid;
    private int qty;

    public Sale(String ordNo, String cid, String pid, int qty) {
        this.ordNo = ordNo;
        this.cid   = cid;
        this.pid   = pid;
        this.qty   = qty;
    }

    public String getOrdNo() {
        return ordNo;
    }

    public String getCid() {
        return cid;
    }

    public String getPid() {
        return pid;
    }

    public int getQty() {
        return qty;
    }
}
