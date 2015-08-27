package com.dto;

import com.dualfual.remote.coned.ConEdProject;

import java.util.List;

public class PlutoCustomer {

    private String bbl;
    private String borough;
    private String zip;
    private String address;
    private Double bldgArea;
    private Double unitRes;
    private String boilerMake;
    private String burnerMake;
    private String primaryFuelType;
    private String secondaryFuelType;
    private String numberUnit;
    private String boilerYearMake;

    private String latitude;
    private String longitude;
    private String fullAddress;
    private Integer locationId = null;

    private List<ConEdProject> conEdProjects;

    public PlutoCustomer(String bbl) {
        this.bbl = bbl;
    }

    public PlutoCustomer() {
    }

    public PlutoCustomer(String bbl, String borough, String zip, String address) {
        this.bbl = bbl;
        this.borough = borough;
        this.zip = zip;
        this.address = address;
    }

    public PlutoCustomer(String bbl, String borough, String zip, String address, Double bldgArea, Double unitRes, String boilerMake, String burnerMake, String primaryFuelType, String secondaryFuelType, String numberUnit, String boilerYearMake) {
        this.bbl = bbl;
        this.borough = borough;
        this.zip = zip;
        this.address = address;
        this.bldgArea = bldgArea;
        this.unitRes = unitRes;
        this.boilerMake = boilerMake;
        this.burnerMake = burnerMake;
        this.primaryFuelType = primaryFuelType;
        this.secondaryFuelType = secondaryFuelType;
        this.numberUnit = numberUnit;
        this.boilerYearMake = boilerYearMake;
    }

    public String getBbl() {
        return bbl;
    }

    public String getBorough() {
        return borough;
    }

    public String getZip() {
        return zip;
    }

    public String getAddress() {
        return address;
    }

    public Double getBldgArea() {
        return bldgArea;
    }

    public Double getUnitRes() {
        return unitRes;
    }

    public String getBoilerMake() {
        return boilerMake;
    }

    public String getBurnerMake() {
        return burnerMake;
    }

    public String getPrimaryFuelType() {
        return primaryFuelType;
    }

    public String getSecondaryFuelType() {
        return secondaryFuelType;
    }

    public String getNumberUnit() {
        return numberUnit;
    }

    public String getBoilerYearMake() {
        return boilerYearMake;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public List<ConEdProject> getConEdProjects() {
        return conEdProjects;
    }

    public void setConEdProjects(List<ConEdProject> conEdProjects) {
        this.conEdProjects = conEdProjects;
    }
}
