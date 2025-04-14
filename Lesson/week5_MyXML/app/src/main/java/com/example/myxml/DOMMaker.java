package com.example.myxml;

import android.content.Context;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class DOMMaker {
    private Context context;

    public DOMMaker(Context context) {
        this.context = context;
    }

    public String makeSource() {
        StringWriter writer= null;
        String[][] team = {{"Samsung", "류중일"}, {"Nexson", "염경엽"}, {"KiA", "김기태"}};
        int[] age = {45, 50, 60};

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element root = document.createElement("baseball");
            document.appendChild(root);
            for (int i = 0; i < team.length; i++) {
                Element element = document.createElement("team");
                element.setAttribute("name", team[i][0]);
                Element node = document.createElement("director");
                node.setAttribute("age", String.valueOf(age[i]));
                node.setTextContent(team[i][1]);
                element.appendChild(node);
                root.appendChild(element);
            }

            TransformerFactory factory1 = TransformerFactory.newInstance();
            Transformer transformer = factory1.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            DOMSource source = new DOMSource(document);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException e) {
            throw new RuntimeException(e);
        }

        return String.valueOf(writer);
    }
}
