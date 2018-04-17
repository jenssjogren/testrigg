package com.example.jens.testrigg;

import java.io.Serializable;

public class Coordinate implements Serializable{
    public String lat, lng, acc;
    public Coordinate(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }
    public Coordinate(String lat, String lng, String acc
    ) {
        this.lat = lat;
        this.lng = lng;
        this.acc = acc;
    }
}
