package com.dualfual.google;

public class Geocode {

    private String latitude;
    private String longitude;

    public Geocode(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return new Double(latitude);
    }

    public Double getLongitude() {
        return new Double(longitude);
    }

    public String getLatitudeStr() {
        return latitude;
    }

    public String getLongitudeStr() {
        return longitude;
    }
}
