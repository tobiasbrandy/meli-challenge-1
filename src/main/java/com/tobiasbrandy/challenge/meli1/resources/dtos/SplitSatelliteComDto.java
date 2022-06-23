package com.tobiasbrandy.challenge.meli1.resources.dtos;

import java.util.List;

public class SplitSatelliteComDto {
    private double        distance;
    private List<String>  message;

    /////////////////// Burocracia //////////////////////

    public double getDistance() {
        return distance;
    }
    public void setDistance(final double distance) {
        this.distance = distance;
    }

    public List<String> getMessage() {
        return message;
    }
    public void setMessage(final List<String> message) {
        this.message = message;
    }
}
