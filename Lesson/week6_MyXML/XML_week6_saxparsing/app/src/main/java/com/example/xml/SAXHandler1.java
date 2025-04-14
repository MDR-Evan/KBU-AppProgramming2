package com.example.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class SAXHandler1 extends DefaultHandler {
    private ArrayList<Country> countries;
    private String text;
    private Country country;

    public SAXHandler1() {
        this.countries = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
       if (localName.equals("nation"))
           country = new Country();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        text = new String(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (localName.equals("name"))
            country.setName(text);
        else if (localName.equals("flag"))
            country.setFlag(text);
        else if (localName.equals("lang"))
            country.setLang(text);
        else if (localName.equals("capital"))
            country.setCapital(text);
        else if (localName.equals("code"))
            country.setCode(text);
        else if (localName.equals("currencyname"))
            country.setCurrencyname(text);
        else if (localName.equals("nation"))
            countries.add(country);
    }

    public ArrayList<Country> getResult() {
        return countries;
    }
}
