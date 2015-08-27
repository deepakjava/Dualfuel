package com.dualfual.remote.coned;

public class ConEdAddress {

    private String streetNumber;
    private String street;
    private String zip;

    private String area;

    public ConEdAddress(String streetNumber, String street, String zip, String area) {
        this.streetNumber = streetNumber;
        this.street = street;
        this.zip = zip;
        this.area = area;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getStreet() {
        return street;
    }

    public String getZip() {
        return zip;
    }

    public String getArea() {
        return area;
    }
}
