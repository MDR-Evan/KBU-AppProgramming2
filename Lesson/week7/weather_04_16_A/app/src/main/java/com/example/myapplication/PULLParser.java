package com.example.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.TextView;
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
    private Activity activity;
    private MyApplication application;
    private TextView textView;

    public PULLParser(Activity activity, TextView textView) {
        this.activity = activity;
        this.application = (MyApplication) activity.getApplication();
        this.textView = textView;
    }

    public ArrayList<Weather> parsing() {

        textView.setText("ff" + application.getXml());
        ArrayList<Weather> weathers = new ArrayList<>();
        InputStream inputStream = new ByteArrayInputStream(application.getXml().getBytes(StandardCharsets.UTF_8));
        String text = null;
        String today = null;
        int hour = 0;
        int day = 0;
        String temp = null;
        String weather1 = null;
        String rain = null;
        String humidity = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inputStream, "UTF-8");
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        String tag = parser.getName();
                        if (tag.equals("tm")) {
                            today = text.substring(0, 8);
                            String pub = today.substring(0, 4) + " 년 "
                                    + today.substring(4, 6) + " 월 "
                                    + today.substring(6, 8) + " 일 "
                                    + text.substring(8, 10) + " 시 발표";
                            textView.setText(pub);
                        } else if (tag.equals("hour")) {
                            hour = Integer.parseInt(text);
                        } else if (tag.equals("day")) {
                            day = Integer.parseInt(text);
                        } else if (tag.equals("temp")) {
                            temp = text;
                        } else if (tag.equals("wfKor")) {
                            weather1 = text;
                        } else if (tag.equals("pop")) {
                            rain = text;
                        } else if (tag.equals("reh")) {
                            humidity = text;
                        } else if (tag.equals("data")) {
                            Weather weather = new Weather();
                            WeatherImage weatherImage = new WeatherImage(activity);
                            Bitmap bitmap = weatherImage.checkImage(hour, weather1);
                            weather.setImage(bitmap);

                            float index = application.calculateDiscomfortIndex(
                                    Float.parseFloat(temp), Float.parseFloat(humidity));
                            weather.setText1(String.format("%s %02d  시", application.addDate(today, day), hour));
                            weather.setText2(" 온도 : " + temp + "\u2103, 습도 : " + humidity + "%");
                            weather.setText3(" 날씨 : " + weather1 + ", 강수 확율 : " + rain + "%");
                            weather.setText4(" 불쾌 지수 : " + index + "(" + application.getDiscomfortIndexMeaning(index) + ")");
                            weathers.add(weather);
                        }
                        eventType = parser.next();
                }
            }
        } catch (XmlPullParserException | IOException e ) {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        return weathers;
    }
}
