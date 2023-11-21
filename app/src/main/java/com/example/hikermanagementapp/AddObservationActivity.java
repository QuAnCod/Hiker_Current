package com.example.hikermanagementapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;


public class AddObservationActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    // TODO: Declare variables
    private DatabaseHelper database;
    private TextView txtHikeId;
    private EditText edtObservation, edtComment, edtTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_observation);

        // TODO: Initialize variables
        txtHikeId = findViewById(R.id.txtHikeId);
        edtObservation = findViewById(R.id.edtObservation);
        edtTime = findViewById(R.id.edtTime);
        edtComment = findViewById(R.id.edtComment);

        database = new DatabaseHelper(this);

        // Set the hike ID
        Intent intent = getIntent();
        String hikeId = intent.getStringExtra("hikeId");
        txtHikeId.setText(hikeId);


        clearInputFields();

        // TODO: Set up btnSave click listener
        findViewById(R.id.btnSave).setOnClickListener(v -> {
            saveObservation();
        });

        // TODO: Set up btnCancel click listener
        findViewById(R.id.btnCancel).setOnClickListener(v -> {
            finish();
        });

    }

    private void clearInputFields() {
        edtObservation.setText("");
        edtTime.setText("");
        edtComment.setText("");
    }

    private void showRequiredFieldsDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Required Fields")
                .setMessage("Please fill in all required fields.")
                .setPositiveButton("OK", null)
                .create();
    }

    private void saveObservation() {
        String observation = edtObservation.getText().toString();
        String time = edtTime.getText().toString();
        String comment = edtComment.getText().toString();
        String hikeId = txtHikeId.getText().toString();

        if (observation.isEmpty() || time.isEmpty()) {
            showRequiredFieldsDialog();
            return;
        }

        Observation newObservation = new Observation(Integer.parseInt(hikeId), observation, time, comment);
        database.addObservation(newObservation);
    }
}