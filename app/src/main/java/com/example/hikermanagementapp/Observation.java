package com.example.hikermanagementapp;

public class Observation {
    // TODO: Declare variables
    private int id;
    private int hikeId;
    private String observation;
    private String timeObserved;
    private String comments;

    public Observation(int id, int hikeId, String observation, String dateObserved, String comments) {
        this.id = id;
        this.hikeId = hikeId;
        this.observation = observation;
        this.timeObserved = dateObserved;
        this.comments = comments;
    }

    public Observation(int hikeId, String observation, String dateObserved, String comments) {
        this.hikeId = hikeId;
        this.observation = observation;
        this.timeObserved = dateObserved;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHikeId() {
        return hikeId;
    }

    public void setHikeId(int hikeId) {
        this.hikeId = hikeId;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getTimeObserved() {
        return timeObserved;
    }

    public void setTimeObserved(String dateObserved) {
        this.timeObserved = dateObserved;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
