package com.example.week13;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

public class ZXingHandler {
    private Context context;

    public ZXingHandler(Context context) {
        this.context = context;
    }

    public String decoding(){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.qrcode);
        int width = bitmap.getWidth(), height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
        HybridBinarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
        Reader reader = new MultiFormatReader();

        String message;

        try {
            Result result = reader.decode(binaryBitmap);
            message = result.getBarcodeFormat() + "\n" + result.getText();
        } catch (NotFoundException | FormatException | ChecksumException e) {
            message = "QR코드 인식 실패 : " + e.getMessage();
        }
        return message;
    }
}
