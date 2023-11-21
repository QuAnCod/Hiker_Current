package com.example.hikermanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class EditObservationActivity extends AppCompatActivity {
    // TODO: Declare variables
    private DatabaseHelper database;
    private Observation ob;
    private EditText editObservation, editTime, editCMT;
    private TextView txtObservationId, txtHikeID;
    private ImageButton btnEditObservation, btnCancelEditObservation, btnDeleteObservation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_observation);

        // TODO: Initialize variables
        editObservation = findViewById(R.id.editObservation);
        editTime = findViewById(R.id.editTime);
        editCMT = findViewById(R.id.editCMT);
        txtObservationId = findViewById(R.id.txtObservationId);
        txtHikeID = findViewById(R.id.txtHikeID);
        btnEditObservation = findViewById(R.id.btnEditObservation);
        btnCancelEditObservation = findViewById(R.id.btnCancelEditObservation);
        btnDeleteObservation = findViewById(R.id.btnDeleteOneObservation);

        // TODO: Get intent and extras
        Bundle bundle = getIntent().getExtras();
        int observationId = bundle.getInt("id");
        int hikeId = bundle.getInt("hikeId");
        String observation = bundle.getString("observation");
        String observationDate = bundle.getString("timeObserved");
        String comments = bundle.getString("comments");

        // TODO: Set text for edit
        txtObservationId.setText(String.valueOf(observationId));
        txtHikeID.setText(String.valueOf(hikeId));
        editObservation.setText(observation);
        editTime.setText(observationDate);
        editCMT.setText(comments);


        // TODO: Set onClickListeners
        if (btnEditObservation != null){
            btnEditObservation.setOnClickListener(v -> {
            database = new DatabaseHelper(this);
            int id = Integer.parseInt(txtObservationId.getText().toString());
            int hikeID = Integer.parseInt(txtHikeID.getText().toString());
            String observationEdit = editObservation.getText().toString();
            String timeEdit = editTime.getText().toString();
            String commentsEdit = editCMT.getText().toString();
            ob = new Observation(id, hikeID, observationEdit, timeEdit, commentsEdit);
            database.updateObservation(ob);
            Toast.makeText(this, "Observation updated with id " + id, Toast.LENGTH_SHORT).show();
        });}

        btnCancelEditObservation.setOnClickListener(v -> {
            finish();
        });

        btnDeleteObservation.setOnClickListener(v -> {
            database = new DatabaseHelper(this);
            int id = Integer.parseInt(txtObservationId.getText().toString());
            database.deleteObservation(id);
            Toast.makeText(this, "Observation deleted with id " + id, Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}