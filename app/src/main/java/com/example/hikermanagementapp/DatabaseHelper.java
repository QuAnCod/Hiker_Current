package com.example.hikermanagementapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "HikerDatabase";

    // Table Names
    private static final String TABLE_HIKES = "hikes";
    private static final String TABLE_OBSERVATIONS = "observations";

    // Common column names
    private static final String KEY_ID = "id";

    // HIKES Table - column names
    private static final String KEY_NAME = "name";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_DATE = "date";
    private static final String KEY_PARKING = "parking";
    private static final String KEY_LENGTH = "length";
    private static final String KEY_DIFFICULTY = "difficulty";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_ACCOMMODATION = "accommodation";

    // OBSERVATIONS Table - column names
    private static final String KEY_HIKE_ID = "hike_id";
    private static final String KEY_OBSERVATION = "observation";
    private static final String KEY_OBSERVATIONDATE = "dateObserved";
    private static final String KEY_COMMENTS = "comments";
    private static final String KEY_IMAGE = "image";

    private SQLiteDatabase database;

    // Table Create Statements
    // HIKES table create statement
    private static final String CREATE_TABLE_HIKES = "CREATE TABLE "
            + TABLE_HIKES + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME
            + " TEXT," + KEY_LOCATION + " TEXT," +  KEY_DATE + " DATE,"
            + KEY_PARKING + " TEXT," + KEY_LENGTH + " TEXT," + KEY_DIFFICULTY + " TEXT,"
            + KEY_DESCRIPTION + " TEXT," + KEY_ACCOMMODATION + " TEXT" + ");";

    // OBSERVATIONS table create statement
    private static final String CREATE_TABLE_OBSERVATIONS = "CREATE TABLE "
            + TABLE_OBSERVATIONS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_HIKE_ID + " INTEGER," + KEY_OBSERVATION + " TEXT,"
            + KEY_OBSERVATIONDATE + " DATE,"
            + KEY_COMMENTS + " TEXT,"
            + KEY_IMAGE + " TEXT,"
            + " FOREIGN KEY (" + KEY_HIKE_ID + ") REFERENCES " + TABLE_HIKES + "(" + KEY_ID + "));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_HIKES);
        db.execSQL(CREATE_TABLE_OBSERVATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIKES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OBSERVATIONS);

        // create new tables
        onCreate(db);
    }

    // Hikes
    // Add a new Hike
    public long addHike(Hike hike) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, hike.getName());
        values.put(KEY_LOCATION, hike.getLocation());
        values.put(KEY_DATE, hike.getDate());
        values.put(KEY_PARKING, hike.getParking());
        values.put(KEY_LENGTH, hike.getLength());
        values.put(KEY_DIFFICULTY, hike.getDifficulty());
        values.put(KEY_DESCRIPTION, hike.getDescription());
        values.put(KEY_ACCOMMODATION, hike.getAccommodation());
        return database.insertOrThrow(TABLE_HIKES, null, values);
    }

    // Fetch all Hikes
    public ArrayList<Hike> readHikes(){
        ArrayList<Hike> hikes = new ArrayList<>();
        database = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_HIKES;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Hike hike = new Hike(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5), cursor.getString(6),
                    cursor.getString(7), cursor.getString(8));
            hikes.add(hike);
            cursor.moveToNext();
        }
        database.close();
        return hikes;
    }

    // Search by name:
    public ArrayList<Hike> searchHikesByName(String searchName){
        ArrayList<Hike> hikes = new ArrayList<>();
        database = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_HIKES + " WHERE " + KEY_NAME + " LIKE ?";
        String[] selectionArgs = new String[]{"%" + searchName + "%"};
        Cursor cursor = database.rawQuery(query, selectionArgs);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Hike hike = new Hike(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5), cursor.getString(6),
                    cursor.getString(7), cursor.getString(8));
            hikes.add(hike);
            cursor.moveToNext();
        }

        database.close();
        return hikes;
    }

    // Update a Hike
    public void updateHike(Hike hike) {
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, hike.getName());
        values.put(KEY_LOCATION, hike.getLocation());
        values.put(KEY_DATE, hike.getDate());
        values.put(KEY_PARKING, hike.getParking());
        values.put(KEY_LENGTH, hike.getLength());
        values.put(KEY_DIFFICULTY, hike.getDifficulty());
        values.put(KEY_DESCRIPTION, hike.getDescription());
        values.put(KEY_ACCOMMODATION, hike.getAccommodation());
        database.update(TABLE_HIKES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(hike.getId())});
        database.close();
    }

    // Delete a Hike
    public void deleteHike(int id) {
        database = this.getWritableDatabase();
        database.delete(TABLE_HIKES, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        database.close();
    }

    // Delete all Hikes
    public void deleteAllHikes() {
        database = this.getWritableDatabase();
        database.delete(TABLE_HIKES, null, null);
        database.close();
    }

}
