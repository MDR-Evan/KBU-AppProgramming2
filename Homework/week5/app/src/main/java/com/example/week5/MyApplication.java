package com.example.week5;

import android.app.Application;

public class MyApplication extends Application {

    private String xml;
    private int type = 1;
    private String url = "http://25.20.171.99:8081";
    private String[] page = new String[] {url + "company.xml", url + "laptops.xml", url + "university.xml"};


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
