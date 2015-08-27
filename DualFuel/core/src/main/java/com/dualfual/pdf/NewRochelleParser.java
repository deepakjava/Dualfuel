package com.dualfual.pdf;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class NewRochelleParser implements IAddressParser{

    @Override
    public String getUrl() {
        return "http://www.newrochelleny.com/DocumentCenter/View/538";
    }

    @Override
    public void process(Map<String, Address> address, String line, String lastLine, BufferedReader st) throws IOException {
        if (line.startsWith("New Rochelle, NY")) {
            Address ad = new Address();

            String streetAddress = lastLine.split("   ")[0].trim();
            ad.setAddress(streetAddress);
            String town = line.split(",")[0].trim();
            ad.setTown(town.replaceAll("NY", "").trim());
            //line = st.readLine().trim();
            String zip = line.split(" ")[3].trim();
            ad.setZip(zip);
            ad.setState("NY");
            ad.setBorough("WESTCHESTER");
            address.put(ad.getUniqueKey(), ad);

            //System.out.println(streetAddress + " " + town + " " + zip);
        }
    }
}
