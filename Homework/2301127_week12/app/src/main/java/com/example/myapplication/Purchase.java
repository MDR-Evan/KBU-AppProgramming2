package com.example.myapplication;

public class Purchase {
    private String pname;
    private int qty;
    private float cost;
    private float total;

    public Purchase(String pname, int qty, float cost, float total) {
        this.pname = pname;
        this.qty   = qty;
        this.cost  = cost;
        this.total = total;
    }

    public String getPname() {
        return pname;
    }

    public int getQty() {
        return qty;
    }

    public float getCost() {
        return cost;
    }

    public float getTotal() {
        return total;
    }
}
