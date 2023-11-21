package com.example.hikermanagementapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ObservationAdapter extends BaseAdapter {
    // TODO: Declare variables
    private DatabaseHelper database;
    private ArrayList<Observation> observations;
    private Context context;
    private TextView txtHikeID, txtObservationId, txtObservation, txtObservationTime, txtObservationCmt;


    @Override
    public int getCount() {
        return observations.size();
    }

    @Override
    public Object getItem(int position) {
        return observations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ObservationAdapter(ArrayList<Observation> observations, DatabaseHelper database, Context context) {
        this.observations = observations;
        this.database = database;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Observation observation = observations.get(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_observation, parent, false);
        }

        // TODO: Initialize variables
        txtObservationId = convertView.findViewById(R.id.txtObservationId);
        txtHikeID = convertView.findViewById(R.id.txtHikeID);
        txtObservation = convertView.findViewById(R.id.txtObservation);
        txtObservationTime = convertView.findViewById(R.id.txtObservationTime);
        txtObservationCmt = convertView.findViewById(R.id.txtObservationCmt);

        // TODO: Set text
        txtObservationId.setText(String.valueOf(observation.getId()));
        txtHikeID.setText(String.valueOf(observation.getHikeId()));
        txtObservation.setText(observation.getObservation());
        txtObservationTime.setText(observation.getTimeObserved());
        txtObservationCmt.setText(observation.getComments());

        return convertView;
    }
}
