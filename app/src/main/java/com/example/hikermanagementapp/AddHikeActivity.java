package com.example.hikermanagementapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class AddHikeActivity extends AppCompatActivity {
    // TODO: Declare variables for widgets
    private EditText edtHikeName, edtLocation, edtLength, edtDescription, edtAccommodation;
    private CalendarView cldDate;
    private Spinner spnParking, spnDifficulty;
    private DatabaseHelper database;
    private Button btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hike);

        // TODO: Assign variables to widgets
        edtHikeName = findViewById(R.id.edtHikeName);
        edtLocation = findViewById(R.id.edtLocation);
        cldDate = findViewById(R.id.cldDate);
        spnParking = findViewById(R.id.spnParking);
        edtLength = findViewById(R.id.edtLength);
        spnDifficulty = findViewById(R.id.spnDifficulty);
        edtDescription = findViewById(R.id.edtDescription);
        edtAccommodation = findViewById(R.id.edtAccommodation);

        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        database = new DatabaseHelper(this);

        setUpSpinners();

        clearInputFields();

        // TODO: Set up btnSave click listener
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveHike();
            }
        });

        // TODO: Set up btnCancel click listener
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

    private void showRequiredFieldsDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Required Fields")
                .setMessage("Please fill in all required fields.")
                .setPositiveButton("OK", null)
                .create();
    }

    private void clearInputFields() {
        edtHikeName.setText("");
        edtLocation.setText("");
        edtLength.setText("");
        edtDescription.setText("");
        edtAccommodation.setText("");
        cldDate.setDate(Calendar.getInstance().getTimeInMillis());
    }

    private void saveHike() {
        String name = edtHikeName.getText().toString();
        String location = edtLocation.getText().toString();
        String date = getDateFromDatePicker(cldDate);
        String parking = spnParking.getSelectedItem().toString();
        String length = edtLength.getText().toString();
        String difficulty = spnDifficulty.getSelectedItem().toString();
        String description = edtDescription.getText().toString();
        String accommodation = edtAccommodation.getText().toString();

        // TODO: Validate input
        if (name.isEmpty() || location.isEmpty() || date.isEmpty() || parking.isEmpty() || length.isEmpty() || difficulty.isEmpty() ) {
            showRequiredFieldsDialog();
            return;
        }

        // TODO: Create a new Hike object and save it to the database
        Hike hike = new Hike(name, location, date, parking, length, difficulty, description, accommodation);
        database.addHike(hike);
        Toast.makeText(this, "Hike saved successfully!", Toast.LENGTH_SHORT).show();

        // TODO: Clear all input fields
        clearInputFields();
    }
}