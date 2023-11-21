package com.example.hikermanagementapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class HikeAdapter extends BaseAdapter {
    private ArrayList<Hike> listHikes;
    private DatabaseHelper database;
    private Context context;

    private TextView txtNameOfHike, txtLocation, txtDate, txtLength, txtParking, txtDifficulty, txtDescription, txtAccommodation;

    @Override
    public int getCount() {
        return listHikes.size();
    }

    @Override
    public Object getItem(int position) {
        return listHikes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public HikeAdapter(ArrayList<Hike> listHikes, DatabaseHelper database, Context context){
        this.listHikes = listHikes;
        this.database = database;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Hike hike = listHikes.get(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_hike, parent, false);
        }


        txtNameOfHike = convertView.findViewById(R.id.txtNameOfHike);
        txtLocation = convertView.findViewById(R.id.txtLocation);
        txtDate = convertView.findViewById(R.id.txtDate);
        txtLength = convertView.findViewById(R.id.txtLength);
        txtParking = convertView.findViewById(R.id.txtParking);
        txtDifficulty = convertView.findViewById(R.id.txtDifficulty);
        txtDescription = convertView.findViewById(R.id.txtDescription);
        txtAccommodation = convertView.findViewById(R.id.txtAccommodation);

        txtNameOfHike.setText(hike.getName());
        txtLocation.setText(hike.getLocation());
        txtDate.setText(hike.getDate());
        txtLength.setText(hike.getLength());
        txtParking.setText(hike.getParking());
        txtDifficulty.setText(hike.getDifficulty());
        txtDescription.setText(hike.getDescription());
        txtAccommodation.setText(hike.getAccommodation());

        return convertView;
    }


}
