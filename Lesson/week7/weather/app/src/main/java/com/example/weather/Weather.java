package com.example.weather;

import android.graphics.Bitmap;
import android.widget.TextView;

public class Weather {
    private Bitmap bitmap;
    private String tv1;
    private String tv2;
    private String tv3;
    private String tv4;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getTv1() {
        return tv1;
    }

    public void setTv1(String tv1) {
        this.tv1 = tv1;
    }

    public String getTv2() {
        return tv2;
    }

    public void setTv2(String tv2) {
        this.tv2 = tv2;
    }

    public String getTv3() {
        return tv3;
    }

    public void setTv3(String tv3) {
        this.tv3 = tv3;
    }

    public String getTv4() {
        return tv4;
    }

    public void setTv4(String tv4) {
        this.tv4 = tv4;
    }
}
