package com.dualfual.pdf;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class WhitePlainsParser implements IAddressParser{

    @Override
    public String getUrl() {
        return "http://www.cityofwhiteplains.com/DocumentCenter/View/1334";
    }

    @Override
    public void process(Map<String, Address> address, String line, String lastLine, BufferedReader st) throws IOException {
        if (line.startsWith("WHITE PLAINS")) {
            Address ad = new Address();
            String streetAddress = lastLine.split("   ")[0].trim();
            ad.setAddress(streetAddress.replaceAll("\\.", ""));
            String[] str = line.split("   ")[0].trim().split(" ");

            if(ad.getAddress().contains("P O BOX")){
                return;
            }

            if(str.length != 4){
                return;
            }

            if(!str[2].trim().equalsIgnoreCase("NY")){
                return;
            }
            ad.setTown(str[0].trim() + " " + str[1].trim());
            String zip = str[3].trim().trim();
            ad.setZip(zip);
            ad.setState("NY");
            ad.setBorough("WESTCHESTER");
            address.put(ad.getUniqueKey(), ad);

            //System.out.println(streetAddress + " " + town + " " + zip);
        }
    }
}
