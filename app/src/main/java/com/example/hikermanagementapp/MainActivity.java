package com.example.hikermanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private HikeAdapter hikeAdapter;
    private DatabaseHelper database;
    private ArrayList<Hike> listHikes;
    private FloatingActionButton fabAddHike;
    private ImageButton btnSearch, btnCancelSearch;
    private TextView searchContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lvHikes);
        fabAddHike = findViewById(R.id.fabAddHike);
        btnSearch = findViewById(R.id.btnSearch);
        btnCancelSearch = findViewById(R.id.btnCancelSearch);
        searchContent = findViewById(R.id.searchContent);

        // TODO: Set up ListView with an adapter
        listHikes = new ArrayList<>();
        database = new DatabaseHelper(this);
        listHikes = database.readHikes();
        hikeAdapter = new HikeAdapter(listHikes, database, this);
        listView.setAdapter(hikeAdapter);


        // Set up Floating Action Button click listener
        fabAddHike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to start AddHikeActivity
                Intent intent = new Intent(MainActivity.this, AddHikeActivity.class);
                startActivity(intent);
            }
        });


        // Set up btnSearch to perform query listener
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = searchContent.getText().toString();
                listHikes = database.searchHikesByName(search);
                hikeAdapter = new HikeAdapter(listHikes, database, MainActivity.this);
                listView.setAdapter(hikeAdapter);
            }
        });

        // Set up btnCancelSearch to cancel query listener
        btnCancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listHikes = database.readHikes();
                hikeAdapter = new HikeAdapter(listHikes, database, MainActivity.this);
                listView.setAdapter(hikeAdapter);
            }
        });

        // Set up listView item click listener
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Hike hike = listHikes.get(position);
            Intent intent = new Intent(MainActivity.this, HikeDetailsActivity.class);
            intent.putExtra("id", listView.getItemIdAtPosition(position));
            intent.putExtra("name", hike.getName());
            intent.putExtra("location", hike.getLocation());
            intent.putExtra("date", hike.getDate());
            intent.putExtra("length", hike.getLength());
            intent.putExtra("parking", hike.getParking());
            intent.putExtra("difficulty", hike.getDifficulty());
            intent.putExtra("description", hike.getDescription());
            intent.putExtra("accommodation", hike.getAccommodation());
            startActivity(intent);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        listHikes = database.readHikes();
        hikeAdapter = new HikeAdapter(listHikes, database, this);
        listView.setAdapter(hikeAdapter);
    }


}