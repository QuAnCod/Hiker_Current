package com.example.hikermanagementapp;

public class Hike {

    private int id;
    private String name;
    private String location;
    private String date;
    private String parking;
    private String length;
    private String difficulty;
    private String description;
    private String accommodation;

    public Hike(String name, String location, String date, String parking, String length, String difficulty, String description, String accommodation) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.parking = parking;
        this.length = length;
        this.difficulty = difficulty;
        this.description = description;
        this.accommodation = accommodation;
    }

    public Hike(int id, String name, String location, String date, String parking, String length, String difficulty, String description, String accommodation) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.parking = parking;
        this.length = length;
        this.difficulty = difficulty;
        this.description = description;
        this.accommodation = accommodation;
    }

    public Hike(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(String accommodation) {
        this.accommodation = accommodation;
    }
}
