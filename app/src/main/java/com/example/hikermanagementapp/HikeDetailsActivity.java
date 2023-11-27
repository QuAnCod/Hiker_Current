package com.example.hikermanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HikeDetailsActivity extends AppCompatActivity {
    // TODO: Declare variables
    private TextView txtIdEdit;
    private EditText  edtHikeName, edtLocation, edtLength, edtDescription, edtAccommodation;
    private CalendarView cldDate;
    private Spinner spnParking, spnDifficulty;
    private ImageButton btnObservations, btnDelete, btnEdit, btnCancelEdit;

    private DatabaseHelper database;
    private Hike hike;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_details);

        // TODO: Initialize variables
        txtIdEdit = findViewById(R.id.txtIdEdit);
        edtHikeName = findViewById(R.id.edtHikeName);
        edtLocation = findViewById(R.id.edtLocation);
        edtLength = findViewById(R.id.edtLength);
        edtDescription = findViewById(R.id.edtDescription);
        edtAccommodation = findViewById(R.id.edtAccommodation);

        cldDate = findViewById(R.id.cldDate);
        spnParking = findViewById(R.id.spnParking);
        spnDifficulty = findViewById(R.id.spnDifficulty);

        btnObservations = findViewById(R.id.btnObservations);
        btnDelete = findViewById(R.id.btnDelete);
        btnEdit = findViewById(R.id.btnEdit);
        btnCancelEdit = findViewById(R.id.btnCancelEdit);

        setUpSpinners();

        // TODO: Get intent and extras
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        int bundleId = bundle.getInt("id", 0);
        String bundleName = bundle.getString("name", "");
        String bundleLocation = bundle.getString("location", "");
        String bundleDate = bundle.getString("date", "");
        String bundleLength = bundle.getString("length", "");
        String bundleParking = bundle.getString("parking", "");
        String bundleDifficulty = bundle.getString("difficulty", "");
        String bundleDescription = bundle.getString("description", "");
        String bundleAccommodation = bundle.getString("accommodation", "");

        // TODO: Set text for Edit
        txtIdEdit.setText(String.valueOf(bundleId));
        edtHikeName.setText(bundleName);
        edtLocation.setText(bundleLocation);
        edtLength.setText(bundleLength);
        edtDescription.setText(bundleDescription);
        edtAccommodation.setText(bundleAccommodation);

        // TODO: Set date for Edit
        if (!bundleDate.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date date = sdf.parse(bundleDate);
                if (date != null) {
                    long millis = date.getTime();
                    cldDate.setDate(millis, false, true);
                }
            } catch (ParseException e) {
                e.printStackTrace();
                // Handle parsing error
            }
        }

        // TODO: Set spinner selections for Edit
        setSpinnerSelection(spnParking, bundleParking);
        setSpinnerSelection(spnDifficulty, bundleDifficulty);

        // TODO: Set onClickListeners
        btnObservations.setOnClickListener(v -> {
            Intent i = new Intent(HikeDetailsActivity.this, ObservationActivity.class);
            i.putExtra("id", bundleId);
            startActivity(i);
        });

        btnDelete.setOnClickListener(v -> {
            database = new DatabaseHelper(HikeDetailsActivity.this);
            database.deleteHike(bundleId);
            Toast.makeText(HikeDetailsActivity.this, "Successfully Deleted hike id" + bundleId, Toast.LENGTH_SHORT).show();
            finish();
        });

        btnEdit.setOnClickListener(v -> {
            database = new DatabaseHelper(HikeDetailsActivity.this);
            int id = Integer.parseInt(txtIdEdit.getText().toString());
            String name = edtHikeName.getText().toString();
            String location = edtLocation.getText().toString();
            String date = getDateFromDatePicker(cldDate);
            String parking = spnParking.getSelectedItem().toString();
            String length = edtLength.getText().toString();
            String difficulty = spnDifficulty.getSelectedItem().toString();
            String description = edtDescription.getText().toString();
            String accommodation = edtAccommodation.getText().toString();

            Hike hike = new Hike(id, name, location, date, parking, length, difficulty, description, accommodation);
            database.updateHike(hike);
            Toast.makeText(HikeDetailsActivity.this, "Successfully Updated hike id" + id, Toast.LENGTH_SHORT).show();
        });

        btnCancelEdit.setOnClickListener(v -> finish());

    }

    private void setSpinnerSelection(Spinner spinner, String value) {
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinner.getAdapter();
        int position = adapter.getPosition(value);
        if (position >= 0) {
            spinner.setSelection(position);
        }
    }

    private void setUpSpinners() {
        // Parking Spinner
        ArrayAdapter<CharSequence> parkingAdapter = ArrayAdapter.createFromResource(this,
                R.array.parking, android.R.layout.simple_spinner_item);
        parkingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnParking.setAdapter(parkingAdapter);

        // Difficulty Spinner
        ArrayAdapter<CharSequence> difficultyAdapter = ArrayAdapter.createFromResource(this,
                R.array.difficulty, android.R.layout.simple_spinner_item);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDifficulty.setAdapter(difficultyAdapter);
    }

    private String getDateFromDatePicker(CalendarView cldDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(cldDate.getDate());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        return String.format(Locale.getDefault(), "%d/%d/%d", month, dayOfMonth, year);
    }

}