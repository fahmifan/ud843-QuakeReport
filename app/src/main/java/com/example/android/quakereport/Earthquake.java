package com.example.android.quakereport;

public class Earthquake  {
    private String city;
    private double mags;
    private String date;

    public Earthquake(String city, double mags, String date) {
        this.city = city;
        this.mags = mags;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public double getMags() {
        return mags;
    }

    public String getCity() {
        return city;
    }
}