package com.example.week6;

public class Country {
    private String name;
    private String flagUrl;    // 국기 이미지 URL
    private String language;
    private String capital;
    private String currency;
    private String currencyCode; // KRW, JPY 등

    public Country(String name, String flagUrl, String language, String capital,
                   String currency, String currencyCode) {
        this.name = name;
        this.flagUrl = flagUrl;
        this.language = language;
        this.capital = capital;
        this.currency = currency;
        this.currencyCode = currencyCode;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
