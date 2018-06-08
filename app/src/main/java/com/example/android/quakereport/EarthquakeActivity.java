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

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    ArrayList<Earthquake> earthquakes = null;

    Context mContext;
    private final String USGS_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02";
    private EarthquakeAdapter earthquakeAdapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        listView = findViewById(R.id.list);
        mContext = this;
        earthquakes = new ArrayList<>();
        earthquakeAdapter = new EarthquakeAdapter(mContext, earthquakes);

        EarthquakeAsync task = new EarthquakeAsync();
        task.execute(USGS_URL);

        listView.setAdapter(earthquakeAdapter);
    }

    private void updateUI() {
        listView.setAdapter(earthquakeAdapter);
    }

    public void setEarthquakes(ArrayList<Earthquake> earthquakes) {
        this.earthquakes = earthquakes;
    }

    public void setEarthquakeAdapter(EarthquakeAdapter earthquakeAdapter) {
        this.earthquakeAdapter = earthquakeAdapter;
    }

    @SuppressLint("StaticFieldLeak")
    private class EarthquakeAsync extends AsyncTask<String, Void, ArrayList<Earthquake>> {
        @Override
        protected ArrayList<Earthquake> doInBackground(String... urls) {
            try {
                 return QueryUtils.fetchData(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Earthquake> results) {
//            earthquakes = results;
//            earthquakeAdapter = new EarthquakeAdapter(mContext, earthquakes);
            setEarthquakes(results);
            setEarthquakeAdapter(new EarthquakeAdapter(mContext, earthquakes));
            updateUI();
        }
    }
}