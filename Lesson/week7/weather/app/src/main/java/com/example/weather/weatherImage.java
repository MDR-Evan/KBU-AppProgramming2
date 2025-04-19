package com.example.weather;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class weatherImage {
    private Bitmap[] bitmaps;

    public weatherImage(Context context) {
        Resources resources = context.getResources();
        this.bitmaps = new Bitmap[] {
                BitmapFactory.decodeResource(resources, R.drawable.nb01),
                BitmapFactory.decodeResource(resources, R.drawable.nb02),
                BitmapFactory.decodeResource(resources, R.drawable.nb03),
                BitmapFactory.decodeResource(resources, R.drawable.nb04),
                BitmapFactory.decodeResource(resources, R.drawable.nb01_n),
                BitmapFactory.decodeResource(resources, R.drawable.nb02_n),
                BitmapFactory.decodeResource(resources, R.drawable.nb03_n),
                BitmapFactory.decodeResource(resources, R.drawable.nb07),
                BitmapFactory.decodeResource(resources, R.drawable.nb08),
                BitmapFactory.decodeResource(resources, R.drawable.nb11),
                BitmapFactory.decodeResource(resources, R.drawable.nb12),
                BitmapFactory.decodeResource(resources, R.drawable.nb12)
        };
    }

    public Bitmap checkImage(int hour, String forcast) {
        Bitmap bitmap = null;
        if (hour >= 6 && hour <= 18) {
            if (forcast.equals("맑음")) {
                bitmap = bitmaps[0];
            } else if (forcast.equals("구름 조금")) {
                bitmap = bitmaps[1];
            } else if (forcast.equals("구름 많음")) {
                bitmap = bitmaps[2];
            } else if (forcast.equals("흐림")) {
                bitmap = bitmaps[3];
            } else if (forcast.equals("비")) {
                bitmap = bitmaps[8];
            } else if (forcast.equals("눈")) {
                bitmap = bitmaps[9];
            } else if (forcast.equals("소나기")) {
                bitmap = bitmaps[7];
            } else {
                bitmap = bitmaps[10];
            }
        } else {
            if (forcast.equals("맑음")) {
                bitmap = bitmaps[4];
            } else if (forcast.equals("구름 조금")) {
                bitmap = bitmaps[5];
            } else if (forcast.equals("구름 많음")) {
                bitmap = bitmaps[6];
            } else if (forcast.equals("흐림")) {
                bitmap = bitmaps[6];
            } else if (forcast.equals("비")) {
                bitmap = bitmaps[8];
            } else if (forcast.equals("눈")) {
                bitmap = bitmaps[9];
            } else if (forcast.equals("소나기")) {
                bitmap = bitmaps[7];
            } else {
                bitmap = bitmaps[10];
            }
        }
        return bitmap;
    }
}
