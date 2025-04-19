package com.example.weather;

import android.app.Activity;
import android.app.Application;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class DOMParser {
    private Activity activity;
    private Application application;
    private TextView tv;

    public DOMParser(Activity activity, TextView tv) {
        this.activity = activity;
        this.application = activity.getApplication();
        this.tv = tv;
    }

    public ArrayList<Weather> parsing() {
        ArrayList<Weather> weathers = new ArrayList<>();
        String text = null;
        String today = null;
        int hour = 0;
        int day = 0;
        String temp = null;
        String forcast = null;
        String rain = null;
        String humidity = null;

        InputStream inputStream = new ByteArrayInputStream(application.getXml().getBytes(StandardCharsets.UTF_8));
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream, "UTF-8");

            NodeList header = document.getElementsByTagName("tm");
            String tm = header.item(0).getTextContent();
            today = text.substring(0, 8);
            String pub = today.substring(0, 4) + "년" +
                    today.substring(4, 6) + "월" +
                    today.substring(6, 8) + "일" +
                    tm.substring(8, 10) + "시 발표";
            tv.setText(pub);
            NodeList nodeList = document.getElementsByTagName("data");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Weather weather = new Weather();
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    day = Integer.parseInt(element.getElementsByTagName("day").item(0).getTextContent());
                    hour = Integer.parseInt(element.getElementsByTagName("hour").item(0).getTextContent());
                    temp = element.getElementsByTagName("temp").item(0).getTextContent();
                    forcast = element.getElementsByTagName("wfKor").item(0).getTextContent();
                    rain = element.getElementsByTagName("pop").item(0).getTextContent();
                    humidity = element.getElementsByTagName("humidity").item(0).getTextContent();

                    float index = application.calculateDiscomfortIndex(Float.parseFloat(temp), Float.parseFloat(humidity));

                    weather.setTv1(String.format("%s %02d시", application.addDate(today, day), hour));
                    weather.setTv2(String.format("온도 : %s\u2103 습도: %s%", temp, humidity));
                    weather.setTv3(String.format("날씨 : %s, 강수확률 %s%", forcast, rain));
                    weather.setTv4(String.format("불쾌 지수 : %f(%s)", index, application.getDiscomfortIndexMeaning(index)));

                    weathers.add(weather);
                }
            }
        } catch (IOException | ParserConfigurationException | SAXException e) {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return weathers;
    }

}
