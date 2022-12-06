package com.example.mikedrop;

import android.location.Location;

public class Restaurants {

    private final String name;
    private final String address;
    private final double lon;
    private final double lat;

    public Restaurants(String name, String address, double lon, double lat) {
        this.name = name;
        this.address = address;
        this.lon = lon;
        this.lat = lat;
    }

    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    @Override
    public String toString(){
        return (getName() +"\n" + getAddress());
    }
}
