package com.example.myxml;

public class Country {
    private String name;
    private String flag;
    private String lang;
    private String capital;
    private String code;
    private String currencyName;

    public Country(String capital, String code, String currencyName, String flag, String lang, String name) {
        this.capital = capital;
        this.code = code;
        this.currencyName = currencyName;
        this.flag = flag;
        this.lang = lang;
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public String getCode() {
        return code;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public String getFlag() {
        return flag;
    }

    public String getLang() {
        return lang;
    }

    public String getName() {
        return name;
    }
}
