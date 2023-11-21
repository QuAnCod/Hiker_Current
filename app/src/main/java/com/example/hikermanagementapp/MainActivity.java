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
    private FloatingActionButton fabAddHike, fabDeleteAllHikes;
    private ImageButton btnSearch, btnCancelSearch;
    private TextView searchContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lvHikes);
        fabAddHike = findViewById(R.id.fabAddHike);
        fabDeleteAllHikes = findViewById(R.id.fabDeleteAllHikes);
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

        // Set up Floating Action Button click listener
        fabDeleteAllHikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to start AddHikeActivity
                database.deleteAllHikes();
                listHikes = database.readHikes();
                hikeAdapter = new HikeAdapter(listHikes, database, MainActivity.this);
                listView.setAdapter(hikeAdapter);
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
        listView.setOnItemClickListener((parent, view, position, i) -> {
            int id;
            id = listHikes.get(position).getId();
            Intent intent = new Intent(MainActivity.this, HikeDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("id", id);
            bundle.putString("name", listHikes.get(position).getName());
            bundle.putString("location", listHikes.get(position).getLocation());
            bundle.putString("date", listHikes.get(position).getDate());
            bundle.putString("length", listHikes.get(position).getLength());
            bundle.putString("parking", listHikes.get(position).getParking());
            bundle.putString("difficulty", listHikes.get(position).getDifficulty());
            bundle.putString("description", listHikes.get(position).getDescription());
            bundle.putString("accommodation", listHikes.get(position).getAccommodation());
            intent.putExtras(bundle);
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