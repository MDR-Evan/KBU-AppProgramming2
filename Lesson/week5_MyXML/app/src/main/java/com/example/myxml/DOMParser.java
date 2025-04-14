package com.example.myxml;

import android.content.Context;

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
    private Context context;

    public DOMParser(Context context) {
        this.context = context;
    }

    public String parsing1(String xml) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("프로야구 팀\n");
        Document document = makeDOM(xml);
        NodeList nodeList = document.getElementsByTagName("team");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                buffer.append("팀명 : " + element.getAttribute("name") + "\n");
                Element director = (Element) element.getElementsByTagName("director").item(0);
                buffer.append(String.format("감독 : %s(%d 세)\n\n", director.getTextContent(), Integer.parseInt(director.getAttribute("age"))));
            }
        }

        return buffer.toString();
    }

    public String parsing2(String xml) {
        StringBuffer buffer = new StringBuffer();
        Document document = makeDOM(xml);
        NodeList nodeList = document.getElementsByTagName("part");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Element item = (Element) element.getElementsByTagName("item").item(0);
                String Maker = item.getAttribute("Maker");
                String price = item.getAttribute("price");

                Element name = (Element) element.getElementsByTagName("name");
                String name1 = name.getTextContent();

                buffer.append("-------------------------------------------\n");
                buffer.append("품목 : " + name1 + "\n");
                buffer.append("Maker : " + Maker + "\n");
                buffer.append("price : " + price + "원\n\n");
                buffer.append("-------------------------------------------\n");
            }
        }

        return buffer.toString();
    }

    public ArrayList<Student> parsing3(String xml) {
        ArrayList<Student> students = new ArrayList<>();
        Document document = makeDOM(xml);
        NodeList nodeList = document.getElementsByTagName("student");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            Student student = new Student();
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                student.setHakbun(element.getAttribute("hakbun"));
                student.setGrade(element.getAttribute("grade"));
                Element name = (Element) element.getElementsByTagName("name").item(0);
                student.setName(name.getTextContent());
            }
            students.add(student);
        }

        return students;
    }

    public String parsing4(String xml) {
        StringBuffer buffer = new StringBuffer();
        Document document = makeDOM(xml);
        NodeList nodeList = document.getElementsByTagName("member");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Element item = (Element) element.getElementsByTagName("name").item(0);
                String name = item.getTextContent();

                item = (Element) element.getElementsByTagName("contact").item(0);
                String email = item.getAttribute("email");

                item = (Element) element.getElementsByTagName("title").item(0);
                String title = item.getAttribute("title");

                buffer.append("-------------------------------------------\n");
                buffer.append("이름 : " + name + "\n");
                buffer.append("Email : " + email + "\n");
                buffer.append("Title : " + title + "\n\n");
                buffer.append("-------------------------------------------\n");
            }
        }

        return buffer.toString();
    }

    public ArrayList<Country> parsing5(String xml) {
        ArrayList<Country> countries = new ArrayList<>();
        Document document = makeDOM(xml);
        NodeList nodeList = document.getElementsByTagName("nation");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                Element item = (Element) element.getElementsByTagName("name").item(0);
                String name = item.getTextContent();

                item = (Element) element.getElementsByTagName("flag").item(0);
                String flag = item.getTextContent();

                item = (Element) element.getElementsByTagName("lang").item(0);
                String lang = item.getTextContent();

                item = (Element) element.getElementsByTagName("capital").item(0);
                String capital = item.getTextContent();

                item = (Element) element.getElementsByTagName("code").item(0);
                String code = item.getTextContent();

                item = (Element) element.getElementsByTagName("currencyname").item(0);
                String currencyname = item.getTextContent();

                Country country = new Country(capital, code, currencyname,  flag, lang, name);

                countries.add(country);
            }
        }

        return countries;
    }

    private Document makeDOM(String xml) throws RuntimeException {
        InputStream inputStream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(inputStream);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }

        return document;
    }
}
