package com.example.myxml;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadRaw {
    private Context context;

    public ReadRaw(Context context) {
        this.context = context;
    }

    public String getXML() throws IOException {
        StringBuffer buffer = new StringBuffer();
        InputStream inputStream = context.getResources().openRawResource(R.raw.order);
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
