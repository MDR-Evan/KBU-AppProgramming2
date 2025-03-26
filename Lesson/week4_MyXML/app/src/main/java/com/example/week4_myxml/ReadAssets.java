package com.example.week4_myxml;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadAssets {
    private Context context;

    public ReadAssets(Context context) {
        this.context = context;
    }

    public String getXML() throws IOException {
        StringBuffer buffer = new StringBuffer();
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open("students3.xml");
        InputStreamReader streamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(streamReader);
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line + '\n');
        }

        reader.close();
        streamReader.close();
        inputStream.close();

        return buffer.toString();
    }

}
