package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    private Context mContext;
    private List<Earthquake> mEarthquakeList = new ArrayList<>();;
    private String sourceLocation, CONTAIN_OF = " of ";
    private String distance, trueCity;

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

        TextView mags = listItem.findViewById(R.id.magnitude);
        TextView nearby = listItem.findViewById(R.id.nearby);
        TextView city = listItem.findViewById(R.id.city);
        TextView date = listItem.findViewById(R.id.date);
        GradientDrawable magnitudeCircle = (GradientDrawable) mags.getBackground();

        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMags());
        magnitudeCircle.setColor(magnitudeColor);

        sourceLocation = currentEarthquake.getCity();
        // modified the city & nearby
        if(sourceLocation.contains(CONTAIN_OF)) {
            String[] locationString = sourceLocation.split(CONTAIN_OF);
            distance = locationString[0];
            trueCity = locationString[1];

            city.setText(trueCity);
            nearby.setText(distance);
        } else {
            city.setText(sourceLocation);
        }

        date.setText(currentEarthquake.getDate());
        String formattedMags = formatMagnitude(currentEarthquake.getMags());
        mags.setText(formattedMags);

        return  listItem;
    }

    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }


    private int getMagnitudeColor(double mags) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(mags);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }

        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
