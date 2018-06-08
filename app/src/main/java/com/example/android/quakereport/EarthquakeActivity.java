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

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Earthquake>> {

    private ArrayList<Earthquake> earthquakes = null;
    private Context mContext;
    private static final String USGS_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02";
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
        EarthquakeLoader task = new EarthquakeLoader(mContext);

        task.startLoading();
        listView.setAdapter(earthquakeAdapter);
        getLoaderManager().initLoader(0, null, this).forceLoad();
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

    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int id, Bundle args) {
        Toast.makeText(mContext, "loader created", Toast.LENGTH_SHORT).show();
        return new EarthquakeLoader(mContext);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> result) {
        Toast.makeText(mContext, "Loader Finished", Toast.LENGTH_SHORT).show();
        earthquakeAdapter = new EarthquakeAdapter(mContext, earthquakes);
        setEarthquakes(result);
        setEarthquakeAdapter(new EarthquakeAdapter(mContext, earthquakes));
        updateUI();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Earthquake>> loader) {
        setEarthquakes(new ArrayList<Earthquake>());
    }

    private static class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {

        public EarthquakeLoader(Context context) {
            super(context);
        }

        @Override
        public ArrayList<Earthquake> loadInBackground() {
            try {return QueryUtils.fetchData(USGS_URL);}
            catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}