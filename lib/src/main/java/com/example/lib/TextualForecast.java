package com.example.lib;

public class TextualForecast {
    public String text;
    public String period;

    @Override
    public String toString() {
        return "{" + period + ", " + text + "}";
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text=text;
    }
    public String getPeriod() {
        return period;
    }
    public void setPeriod(String period) {
        this.period=period;
    }

}