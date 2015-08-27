package com.dto;

public class LocationInfo {

    private String fullAddress;
    private String latitude;
    private String longitude;

    public LocationInfo(String fullAddress, String latitude, String longitude) {
        this.fullAddress = fullAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
