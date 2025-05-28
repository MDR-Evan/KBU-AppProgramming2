package com.example.week13;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRcodeHandler {
    private Context context;
    private ImageView iv;

    public QRcodeHandler(Context context, ImageView iv) {
        this.context = context;
        this.iv = iv;
    }

    public void encoding(boolean color, boolean logo, String text) {
        BarcodeEncoder encoder = new BarcodeEncoder();
        try {
            BitMatrix bitMatrix = encoder.encode(text, BarcodeFormat.QR_CODE, 1000, 1000);
            Bitmap bitmap = color ? createColor(bitMatrix) : encoder.createBitmap(bitMatrix);
            if (logo) {
                Bitmap logoBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo);
                Bitmap combined = addLogo(bitmap, logoBitmap);
                iv.setImageBitmap(combined);
            } else {
                iv.setImageBitmap(bitmap);
            }
        } catch (WriterException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap addLogo(Bitmap bitmap, Bitmap logoBitmap) {
        Bitmap overlay = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(overlay);

        canvas.drawBitmap(bitmap, 0, 0, null);

        int size = bitmap.getWidth() / 5;
        Bitmap resizedLogo = Bitmap.createScaledBitmap(logoBitmap, size, size, true);

        int X = (bitmap.getWidth() - resizedLogo.getWidth()) / 2;
        int Y = (bitmap.getHeight() - resizedLogo.getHeight()) / 2;
        canvas.drawBitmap(resizedLogo, X, Y, null);

        return overlay;
    }

    private Bitmap createColor(BitMatrix bitMatrix) {
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (bitMatrix.get(x, y)) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.RED : Color.YELLOW);
                }
            }
        }
        return bitmap;
    }
}
