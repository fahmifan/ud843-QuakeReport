/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Earthquake> extractEarthquakes(String response) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // TODO: Parse the response
            JSONObject baseJsonResponse = new JSONObject(response);
            JSONArray earthquakesArray = baseJsonResponse.getJSONArray("features");

            for(int i = 0; i < earthquakesArray.length(); i++) {
                JSONObject currentEarthquake = earthquakesArray.getJSONObject(i);
                JSONObject properties = currentEarthquake.getJSONObject("properties");

                double magnitude = properties.getDouble("mag");
                String place = properties.getString("place");
                String city = place;

                DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.GERMANY);
                Long timestamp = Long.parseLong(properties.getString("time"));
                Date date = new Date(timestamp);

                String formattedDate;
                formattedDate = df.format(date);

                earthquakes.add(new Earthquake(city, magnitude, formattedDate));
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return earthquakes;
    }

    // TODO: add http request to fetch data from URL
    public static ArrayList<Earthquake> fetchData(String urls) throws IOException {
        URL url = new URL(urls);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setReadTimeout(10000 /* milliseconds */);
        urlConnection.setConnectTimeout(15000 /* milliseconds */);
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();

        try {
            StringBuilder output = new StringBuilder();
            if(urlConnection.getResponseCode() == 200) {
                InputStream ins = urlConnection.getInputStream();
                InputStreamReader insr = new InputStreamReader(ins, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(insr);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }

                return extractEarthquakes(output.toString());
            }
        } catch (Error error) {
            Log.e("QueryUtils", "Error response code: " + urlConnection.getResponseCode());
        }
        return null;
    }

}