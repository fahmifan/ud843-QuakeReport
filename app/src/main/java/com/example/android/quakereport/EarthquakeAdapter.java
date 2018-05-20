package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    private Context mContext;
    private List<Earthquake> mEarthquakeList = new ArrayList<>();;

    public EarthquakeAdapter(@NonNull Context context, ArrayList<Earthquake> itemList) {
        super(context, 0, itemList);
        this.mContext = context;
        this.mEarthquakeList = itemList;
    }

    /**
     * This will provide a single view of our component which is a word of QuakeReport & defaultLang
     * for an AdapterView (ListView, GridView, ...)
     * */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null) {
            listItem = LayoutInflater.from(this.mContext).inflate(R.layout.list_item, parent, false);
        }

        Earthquake currentEarthquake = this.mEarthquakeList.get(position);

        TextView mags = (TextView) listItem.findViewById(R.id.mags);
        mags.setText(currentEarthquake.getMags() + "");

        TextView city = (TextView) listItem.findViewById(R.id.city);
        city.setText(currentEarthquake.getCity());

        TextView date = (TextView) listItem.findViewById(R.id.date);
        date.setText(currentEarthquake.getDate());

        return  listItem;
    }
}
