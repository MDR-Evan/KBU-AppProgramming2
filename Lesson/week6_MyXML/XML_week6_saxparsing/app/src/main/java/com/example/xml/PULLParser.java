package com.example.xml;

import android.content.Context;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class PULLParser {
    private Context context;

    public PULLParser(Context context) {
        this.context = context;
    }

    public ArrayList<Country> parsing1(String xml) {
        ArrayList<Country> countries = new ArrayList<>();
        Country country = null;
        String text = null;

        XmlPullParser parser = makePULLParser(xml);
        try {
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tag = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tag.equals("nation"))
                            country = new Country();
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (tag.equals("name"))
                            country.setName(text);
                        else if (tag.equals("flag"))
                            country.setFlag(text);
                        else if (tag.equals("lang"))
                            country.setLang(text);
                        else if (tag.equals("capital"))
                            country.setCapital(text);
                        else if (tag.equals("code"))
                            country.setCode(text);
                        else if (tag.equals("currencyname"))
                            country.setCurrencyname(text);
                        else if (tag.equals("nation"))
                            countries.add(country);
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return countries;
    }

    private XmlPullParser makePULLParser(String xml) {
        XmlPullParser parser = null;
        InputStream inputStream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            parser = factory.newPullParser();
            parser.setInput(inputStream, "UTF-8");
        } catch (XmlPullParserException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return parser;
    }
}
