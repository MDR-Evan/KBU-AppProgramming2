package com.example.xml;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;


public class ReadRaw {
    private Context context;

    public ReadRaw(Context context) {
        this.context = context;
    }

    public String getXML() {
        InputStream inputStream = context.getResources().openRawResource(R.raw.order);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            reader.close();
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return stringBuilder.toString();
    }
}
