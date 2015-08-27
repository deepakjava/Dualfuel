package com.dualfual.pdf;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PortChesterParser implements IAddressParser {

    @Override
    public String getUrl() {
        return "http://www.townofryeny.com/uploads/2/6/3/9/26392529/2015_rev_twn_tentassmnt_roll_w-correct_fmv&vis_chgs.pdf";
    }

    private List<String> split(String line) {
        String[] l2 = line.trim().split("   ");
        List<String> temp = Arrays.asList(l2);

      //  temp = temp.stream().filter(p -> p.trim().length() > 0).map(s -> s.trim()).collect(Collectors.toCollection(ArrayList::new));
        return temp;
    }

    @Override
    public void process(Map<String, Address> address, String line, String lastLine, BufferedReader st) throws IOException {
        if (line.startsWith("********")) {
            String line1 = st.readLine();
            String line2 = st.readLine();
            if (line2 == null) {
                return;
            }
            List<String> l2 = split(line2.trim());

            String texId = l2.get(0);
            String bldClass = l2.get(1);

            String line3 = st.readLine();
            l2 = split(line3.trim());
            String owner1 = l2.get(0);

            String line4 = st.readLine();
            l2 = split(line4.trim());
            String owner2 = l2.get(0);

            String line5 = st.readLine();
            l2 = split(line5.trim());
            String streetAddress = l2.get(0);

            String line6 = st.readLine();
            if (!line6.contains("Port Chester")) {
                return;
            }
            String town = line6.split(",")[0].trim();
            l2 = split(line6.trim());
            String zip = l2.get(0).split(" ")[3].trim();
            String east = null;
            String nrth = null;
            String fullMktValue = null;
            String line7 = st.readLine();
            if (line7.contains("EAST") && line7.contains("NRTH")) {
                l2 = split(line7.trim());
                east = l2.get(0).split(" ")[0];
                nrth = l2.get(0).split(" ")[1];
            }

            if (line7.contains("FULL MARKET VALUE")) {
                l2 = split(line7.trim());
                fullMktValue = l2.get(1);
            }else{
                String line8 = st.readLine();
                if (line8.contains("EAST") && line8.contains("NRTH")) {
                    l2 = split(line8.trim());
                    east = l2.get(0).split(" ")[0];
                    nrth = l2.get(0).split(" ")[1];
                }
                String line9 = st.readLine();
                if (line9.contains("EAST") && line9.contains("NRTH")) {
                    l2 = split(line9.trim());
                    east = l2.get(0).split(" ")[0];
                    nrth = l2.get(0).split(" ")[1];
                }

                if (line8.contains("FULL MARKET VALUE")) {
                    l2 = split(line8.trim());
                    fullMktValue = l2.get(1);
                } else {
                    if (line9.contains("FULL MARKET VALUE")) {
                        l2 = split(line9.trim());
                        fullMktValue = l2.get(1);
                    } else {
                        String line10 = st.readLine();
                        if (line10.contains("FULL MARKET VALUE")) {
                            l2 = split(line10.trim());
                            fullMktValue = l2.get(1);
                        } else {
                            String line11 = st.readLine();
                            l2 = split(line11.trim());
                            fullMktValue = l2.get(1);
                        }

                    }
                }

            }

            Address ad = new Address();
            ad.setAddress(streetAddress);
            ad.setTown(town.replaceAll("NY", "").trim());
            ad.setZip(zip);
            ad.setState("NY");
            ad.setBorough("WESTCHESTER");
            address.put(ad.getUniqueKey(), ad);


        }
    }

}
