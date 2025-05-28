package com.example.week13;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class BarcodeHandler {
    private Context context;
    private ImageView iv;

    public BarcodeHandler(Context context, ImageView iv) {
        this.context = context;
        this.iv = iv;
    }

    public void encoding(int type, boolean color, String text) {
        BarcodeEncoder encoder = new BarcodeEncoder();
        try {
            BitMatrix bitMatrix;
            if (type == 1) {
                bitMatrix = encoder.encode(text, BarcodeFormat.CODE_128, 800, 400);
            } else {
                bitMatrix = encoder.encode(text, BarcodeFormat.PDF_417, 800, 400);
            }
            Bitmap bitmap = color ? createColor(bitMatrix) : encoder.createBitmap(bitMatrix);
            iv.setImageBitmap(bitmap);
        } catch (WriterException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap createColor(BitMatrix bitmatrix) {
        int width = bitmatrix.getWidth();
        int height = bitmatrix.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (bitmatrix.get(x, y)) {
                    bitmap.setPixel(x, y, bitmatrix.get(x, y) ? Color.RED : Color.YELLOW);
                }
            }
        }
        return bitmap;
    }
}
