package com.example.hikermanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ObservationActivity extends AppCompatActivity {
    // TODO: Declare variables
    private ListView lvObservations;
    private ObservationAdapter observationAdapter;
    private ArrayList<Observation> listObservations;
    private DatabaseHelper database;
    private FloatingActionButton fabObservation, fabDelteAllObservations;
    private TextView tvHikeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation);

        // TODO: Initialize variables
        lvObservations = findViewById(R.id.lvObservations);
        fabObservation = findViewById(R.id.fabAddObservation);
        fabDelteAllObservations = findViewById(R.id.fabDeleteAllObservations);
        tvHikeId = findViewById(R.id.tvHikeId);

        // Set the hike ID
        int hikeId = getIntent().getIntExtra("id", 0);
        tvHikeId.setText(String.valueOf(hikeId));

        // TODO: set up ListView with an adapter
        listObservations = new ArrayList<>();
        database = new DatabaseHelper(this);
        listObservations = database.readObservations(hikeId);
        observationAdapter = new ObservationAdapter(listObservations, database, this);
        lvObservations.setAdapter(observationAdapter);


        // TODO: Set up Floating Action Button click listener
        fabObservation.setOnClickListener(v -> {
            // Intent to start AddObservationActivity
            Intent intent = new Intent(ObservationActivity.this, AddObservationActivity.class);
            intent.putExtra("hikeId", tvHikeId.getText().toString());
            startActivity(intent);
        });

        fabDelteAllObservations.setOnClickListener(v -> {
            // Delete all observations
            database.deleteAllObservations();
            listObservations = database.readObservations(Integer.parseInt(tvHikeId.getText().toString()));
            observationAdapter = new ObservationAdapter(listObservations, database, this);
            lvObservations.setAdapter(observationAdapter);
        });

        // TODO: Set up ListView item click listener
        lvObservations.setOnItemClickListener((parent, view, position, id) -> {
            // Intent to start EditObservationActivity
            Intent intent = new Intent(ObservationActivity.this, EditObservationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("id", listObservations.get(position).getId());
            bundle.putInt("hikeId", listObservations.get(position).getHikeId());
            bundle.putString("observation", listObservations.get(position).getObservation());
            bundle.putString("timeObserved", listObservations.get(position).getTimeObserved());
            bundle.putString("comments", listObservations.get(position).getComments());
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listObservations = database.readObservations(Integer.parseInt(tvHikeId.getText().toString()));
        observationAdapter = new ObservationAdapter(listObservations, database, this);
        lvObservations.setAdapter(observationAdapter);
    }
}