package com.example.android.quakereport;

public class Earthquake  {
    private String city;
    private int mags;
    private String date;

    public Earthquake(String city, int mags, String date) {
        this.city = city;
        this.mags = mags;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public int getMags() {
        return mags;
    }

    public String getCity() {
        return city;
    }
}