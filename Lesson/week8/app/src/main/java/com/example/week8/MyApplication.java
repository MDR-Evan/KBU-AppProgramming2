package com.example.week8;

import android.app.Application;

public class MyApplication extends Application {
    private boolean isServiced = false;
    private String xml;
    private int type = 1;
    private String url = "http://192.168.93.1:8081/";
    private String[] page = new String[] {url + "weather.xml"};

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

    public String getPage(int index) {
        return page[index];
    }
}
