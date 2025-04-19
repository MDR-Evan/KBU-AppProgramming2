package com.example.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class WeatherImage {
    private Bitmap[] image;

    public WeatherImage(Context context) {
        Resources resources = context.getResources();
        image = new Bitmap[]{
                BitmapFactory.decodeResource(resources, R.drawable.nb01_n),
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
                BitmapFactory.decodeResource(resources, R.drawable.nb12)};
    }

    public Bitmap checkImage(int time, String wfKor) {
         Bitmap bitmap;
         if (time >= 6 && time <= 18) {
            if (wfKor.equals("맑음"))
                bitmap = image[0];
            else if (wfKor.equals("구름 조금"))
                bitmap = image[1];
            else if (wfKor.equals("구름 많음"))
                bitmap = image[2];
            else if (wfKor.equals("흐림"))
                bitmap = image[3];
            else if (wfKor.equals("비"))
                bitmap = image[8];
            else if (wfKor.equals("눈"))
                bitmap = image[9];
            else if (wfKor.equals("소나기"))
                bitmap = image[7];
            else
                bitmap = image[10];
         }else {
             if (wfKor.equals("맑음"))
                 bitmap = image[4];
             else if (wfKor.equals("구름 조금"))
                 bitmap = image[5];
             else if (wfKor.equals("구름 많음"))
                 bitmap = image[6];
             else if (wfKor.equals("흐림"))
                 bitmap = image[6];
             else if (wfKor.equals("비"))
                 bitmap = image[8];
             else if (wfKor.equals("눈"))
                 bitmap = image[9];
             else if (wfKor.equals("소나기"))
                 bitmap = image[7];
             else
                 bitmap = image[10];
         }
         return bitmap;
    }
}
