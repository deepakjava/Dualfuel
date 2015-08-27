package com.dualfual.pdf;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class ScareDaleParser implements IAddressParser{

    @Override
    public String getUrl() {
        return "http://www.scarsdale.com/Portals/0/Assessor/Scarsdale%202015%20Tentative.pdf";
    }

    @Override
    public void process(Map<String, Address> address, String line, String lastLine, BufferedReader st) throws IOException {

        if (line.startsWith("SCARSDALE")) {
            Address ad = new Address();
            String streetAddress = lastLine.split("   ")[0].trim();
            ad.setAddress(streetAddress);
            String[] str = line.split("   ")[0].trim().split(" ");
            if(str.length != 3){
                return;
            }

            if(!str[1].trim().equalsIgnoreCase("NY")){
                return;
            }
            ad.setTown(str[0].trim());
            String zip = str[2].trim().trim();
            ad.setZip(zip);
            ad.setState("NY");
            ad.setBorough("WESTCHESTER");
            address.put(ad.getUniqueKey(), ad);

            //System.out.println(streetAddress + " " + town + " " + zip);
        }
    }
}
