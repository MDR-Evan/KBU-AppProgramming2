package com.example.week5;

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
        Document document = makeDOM(xml);
        NodeList nodeList = document.getElementsByTagName("person");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String id = element.getAttribute("id");
                String name1 = element.getElementsByTagName("name").item(0).getTextContent();
                String age1 = element.getElementsByTagName("age").item(0).getTextContent();

                buffer.append("ID : " + id + "\n");
                buffer.append("이름 : " + name1 + "\n");
                buffer.append("나이 : " + age1 + "\n\n");
            }
        }
        return buffer.toString();
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

    public ArrayList<Person> parsingPersonList(String xml) {
        ArrayList<Person> list = new ArrayList<>();
        Document document = makeDOM(xml);
        NodeList nodeList = document.getElementsByTagName("person");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            String id = element.getAttribute("id");
            String name = element.getElementsByTagName("name").item(0).getTextContent();
            String age = element.getElementsByTagName("age").item(0).getTextContent();
            list.add(new Person(id, name, age));
        }

        return list;
    }
}
