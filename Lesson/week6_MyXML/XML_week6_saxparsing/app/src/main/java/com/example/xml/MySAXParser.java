package com.example.xml;

import android.content.Context;
import android.widget.Toast;

import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MySAXParser {
    private Context context;

    public MySAXParser(Context context) {
        this.context = context;
    }

    public ArrayList<Country> parsing1(String xml) {
        SAXParser parser = makeSAXParser();
        InputStream inputStream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        SAXHandler1 handler = new SAXHandler1();
        ArrayList<Country> countries = null;
        try {
            parser.parse(inputStream, handler);
            countries = handler.getResult();
        } catch (IOException | SAXException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return countries;
    }

    private SAXParser makeSAXParser() {
        SAXParser saxParser = null;
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            saxParser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return saxParser;
    }
}
