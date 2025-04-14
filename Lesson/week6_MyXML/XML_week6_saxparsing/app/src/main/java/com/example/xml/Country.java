package com.example.xml;

public class Country {
    private String name;
    private String flag;
    private String lang;
    private String capital;
    private String code;
    private String currencyname;

    public Country() {
    }

    public Country(String name, String flag, String lang, String capital, String code, String currencyname) {
        this.name = name;
        this.flag = flag;
        this.lang = lang;
        this.capital = capital;
        this.code = code;
        this.currencyname = currencyname;
    }

    public String getName() {
        return name;
    }

    public String getFlag() {
        return flag;
    }

    public String getLang() {
        return lang;
    }

    public String getCapital() {
        return capital;
    }

    public String getCode() {
        return code;
    }

    public String getCurrencyname() {
        return currencyname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCurrencyname(String currencyname) {
        this.currencyname = currencyname;
    }
}
