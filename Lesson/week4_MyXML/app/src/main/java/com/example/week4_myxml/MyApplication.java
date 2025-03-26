package com.example.week4_myxml;

import android.app.Application;

public class MyApplication extends Application {
    private String xml;
    private int type = 1;

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
