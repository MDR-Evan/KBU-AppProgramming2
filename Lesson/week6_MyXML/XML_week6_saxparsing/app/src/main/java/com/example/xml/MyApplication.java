package com.example.xml;


import android.app.Application;

public class MyApplication extends Application {
    private String xml;
    private int type = 1;
    private String url = "http://10.10.190.156:8078/";
    private String[] page = new String[] {url + "member.xml", url + "nation.xml"};

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
