package com.dualfual.pdf;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class DobbsFerryParser implements IAddressParser{

    @Override
    public String getUrl() {
        return "http://www.dobbsferry.com/docman/departments/treasurer/486-2015-final-assessment-roll/file.html";
    }

    @Override
    public void process(Map<String, Address> address, String line, String lastLine, BufferedReader st) throws IOException {
        if (line.startsWith("DOBBS FERRY  NY")) {
            Address ad = new Address();

            String streetAddress = lastLine.split("   ")[0].trim();
            ad.setAddress(streetAddress);
            String town = line.split("   ")[0].trim();
            ad.setTown(town.replaceAll("NY", "").trim());
            line = st.readLine().trim();
            String zip = line.split("   ")[0].trim();
            ad.setZip(zip);
            ad.setState("NY");
            ad.setBorough("WESTCHESTER");
            address.put(ad.getUniqueKey(), ad);

            //System.out.println(streetAddress + " " + town + " " + zip);
        }
    }
}
