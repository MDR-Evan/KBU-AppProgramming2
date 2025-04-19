package com.example.week6;

public class ParsedResult {
    private final String resultText;
    private final String flagUrl;

    public ParsedResult(byte[] resultText, String flagUrl) {
        this.resultText = resultText;
        this.flagUrl = flagUrl;
    }

    public String getResultText() {
        return resultText;
    }

    public String getFlagUrl() {
        return flagUrl;
    }
}
