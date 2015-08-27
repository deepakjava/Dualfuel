package com.test;


import com.dto.*;
import com.dualfual.common.MathUtils;
import com.dualfual.google.GeoService;
import com.dualfual.google.Geocode;
import com.dualfual.google.GeocodeResponse;
import com.spring.mail.MailManagerBean;
import com.spring.mail.MailMessage;
import com.test.datasource.TestDataSource;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.DataSourceResourceLoader;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Ignore
public class TestSqlServer {

    private ApplicationContext context = null;

    @Before
    public void init() {
        context =
                new ClassPathXmlApplicationContext(new String[]{"spring-beans_def.xml"});

    }

//    @Test
//    public void loadData() throws SQLException {
//        DataSource dsMysql = TestDataSource.createMySqlDataSource();
//        LocationDAO.saveRegionBorder(dsMysql, ConEdRegionInfo._I5);
//        LocationDAO.saveRegionBorder(dsMysql, ConEdRegionInfo._H2);
//        LocationDAO.saveRegionBorder(dsMysql, ConEdRegionInfo._C4);
//        LocationDAO.saveRegionBorder(dsMysql, ConEdRegionInfo._G3);
//    }

    @Test
    @Ignore
    public void fuelType_6_Notice() throws Exception {

        DataSource dsSqlServer = TestDataSource.createSqlServerDataSource();
        DataSource dsMysql = TestDataSource.createMySqlDataSource();


        DataSourceResourceLoader ds = new DataSourceResourceLoader();
        ds.setDataSource(dsMysql);

        VelocityEngine ve = new VelocityEngine();

        ve.setProperty("resource.loader","ds");
//        properties.setProperty("ds.resource.loader.class","org.apache.velocity.runtime.resource.loader.DataSourceResourceLoader");
        ve.setProperty("ds.resource.loader.resource.table","tb_velocity_template");
        ve.setProperty("ds.resource.loader.resource.keycolumn","id_template");
        ve.setProperty("ds.resource.loader.resource.templatecolumn","template_definition");
        ve.setProperty("ds.resource.loader.resource.timestampcolumn","template_timestamp");
        ve.setProperty("ds.resource.loader.cache","false");
        ve.setProperty("ds.resource.loader.modificationCheckInterval", "60");

        ve.setProperty("ds.resource.loader.instance",ds);

        ve.init();

        Template t = ve.getTemplate( "helloworld.vm" );

        VelocityContext vcontext = new VelocityContext();
        vcontext.put("name", "World");

        StringWriter writer = new StringWriter();
        t.merge( vcontext, writer );
        /* show the World */
        System.out.println( writer.toString() );



        MailManagerBean mailSender = context.getBean("mailManager", MailManagerBean.class);

        //mailSender
        List<PlutoCustomer> data = PlutoDAO.getPlutoData2(dsSqlServer);

        int counter = 0;
        for (PlutoCustomer c : data) {

            counter++;
            Integer id = LocationDAO.getLocationId(dsMysql, c.getAddress(), c.getZip());
            if (id == null) {
                try {
                    GeocodeResponse response = GeoService.findLatitudeLongitude(c.getAddress() + " NY " + c.getZip());
                    if (response.getStatus().equalsIgnoreCase("OK")) {
                        // save
                        LocationDAO.addLocation(dsMysql, c.getAddress(), c.getZip(), response);
                    }
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println(c.getAddress() + "  " + c.getZip());
                    System.out.println(c.getAddress().toUpperCase().replaceAll(" ", "") + "_" + c.getZip());
                }
            }

            id = LocationDAO.getLocationId(dsMysql, c.getAddress(), c.getZip());
            if (id != null) {
                LocationInfo info = LocationDAO.getLocationInfo(dsMysql, id);
                c.setLatitude(info.getLatitude());
                c.setLongitude(info.getLongitude());
                c.setFullAddress(info.getFullAddress());
            }

            printPctComplete(data.size(), counter);
        }

//        sendEmail(mailSender, dsMysql, data, ConEdRegionInfo._I5);
//        sendEmail(mailSender, dsMysql, data, ConEdRegionInfo._H2);
        sendEmail_Fuel6(mailSender, dsMysql, data, false);

    }

    @Test
    @Ignore
    public void querySqlServer() throws Exception {
        DataSource dsSqlServer = TestDataSource.createSqlServerDataSource();
        DataSource dsMysql = TestDataSource.createMySqlDataSource();

        MailManagerBean mailSender = context.getBean("mailManager", MailManagerBean.class);

        //mailSender

        List<PlutoCustomer> data = PlutoDAO.getPlutoData2(dsSqlServer);

        int counter = 0;
        int errorCounter = 0;
        for (PlutoCustomer c : data) {

            counter++;
            Integer id = LocationDAO.getLocationId(dsMysql, c.getAddress(), c.getZip());
            if (id == null) {
                try {
                    GeocodeResponse response = GeoService.findLatitudeLongitude(c.getAddress() + " NY " + c.getZip());
                    if (response.getStatus().equalsIgnoreCase("OK")) {
                        // save
                        LocationDAO.addLocation(dsMysql, c.getAddress(), c.getZip(), response);
                    }
                    Thread.sleep(1000);
                } catch (Exception e) {
                    errorCounter ++;
                    System.out.println(c.getAddress() + "  " + c.getZip());
                    if(errorCounter > 20){
                        System.exit(0);
                    }
                }
            }

            printPctComplete(data.size(), counter);
        }



//        sendEmail(mailSender, dsMysql, data, ConEdRegionInfo._I5);
//        sendEmail(mailSender, dsMysql, data, ConEdRegionInfo._H2);
        ConEdRegion region = LocationDAO.getRegionCodeInfo(dsMysql, "G3");
        sendEmail(mailSender, dsMysql, data, region, false);

    }

    public void sendEmail_Fuel6(MailManagerBean mailSender, DataSource dsMysql, List<PlutoCustomer> data, boolean sendAll) throws Exception {


        List<String> filter = LocationDAO.getLocationFilterInfo(dsMysql, "FUEL6");
        List<PlutoCustomer> filteredList = new ArrayList<>(data);
        for(String s : filter) {
            List<PlutoCustomer> temp = filteredList.stream()
                    .filter(p -> !p.getAddress().contains(s)).collect(Collectors.toList());
            filteredList = temp;
        }

        Map<String, List<PlutoCustomer>> byCity = new TreeMap<>();

        for(PlutoCustomer c : filteredList){
            if(c.getFullAddress() == null){
                continue;
            }
            String[] sp = c.getFullAddress().split(",");
            String city = sp[sp.length -3].trim() + " " + sp[sp.length -2].split(" ")[0];
            List<PlutoCustomer>  tmp = byCity.get(city);
            if(tmp == null){
                tmp = new ArrayList<>();
                byCity.put(city, tmp);
            }
            tmp.add(c);
        }

//        Collections.sort(filteredList, new Comparator<PlutoCustomer>() {
//            @Override
//            public int compare(PlutoCustomer o1, PlutoCustomer o2) {
//                //320 Ocean Parkway, Brooklyn, NY 11218, USA
//                String city1 = o1.getAddress().split(",")[1].trim();
//                String city2 = o2.getAddress().split(",")[1].trim();
//                return city1.compareTo(city2);
//            }
//        });

        HtmlBuilder2 builder = new HtmlBuilder2();

        List<String> cityFilter = new ArrayList<>();
        cityFilter.add("Bronx");
        cityFilter.add("New York");
        String cityTitle = "OTHER";


        for(String city : byCity.keySet()){
            if(cityFilter.contains(city.trim())){
                continue;
            }
            builder.startTable(city);
            for (PlutoCustomer c : byCity.get(city)) {
                builder.addToTable(c.getFullAddress(), c.getNumberUnit(), c.getPrimaryFuelType(), c.getBoilerMake(), c.getBurnerMake(),
                        "<a href=\"http://www.latlong.net/c/?lat=" + c.getLatitude() + "&long=" + c.getLongitude() + "\"> click </a>",
                        "<a href=\"http://dualfuel.mono-tek.com/Pluto/GetBBL?BBL=" + c.getBbl() + "#tab8" + "\">" + "http://dualfuel.mono-tek.com/Pluto/GetBBL?BBL=" + c.getBbl() + "#tab8" + "</a>"
                );
            }
            builder.endTable();
        }

        if (filteredList.size() > 0) {
            if (sendAll) {
                mailSender.sendMail("rparikh@dualfuelcorp.com, steven@dualfuelcorp.com, jbohm@dualfuelcorp.com", cityTitle + ": Sales opportunity in Gas Conversion [NO.6 OIL]", builder.getHtml(), MailMessage.Type.HTML);
            } else {
                mailSender.sendMail("rparikh@dualfuelcorp.com", cityTitle + ": Sales opportunity in Gas Conversion [NO.6 OIL]", builder.getHtml(), MailMessage.Type.HTML);
            }
//
        } else {
            System.out.println("");
            System.out.println("Nothing found");
        }
    }

    public static Integer oilTypeSort(String oilStr) {

        if (oilStr.contains("6")) {
            return 1;
        }
        if (oilStr.contains("4")) {
            return 2;
        }

        return 3;
    }

    public void sendEmail(MailManagerBean mailSender, DataSource dsMysql, List<PlutoCustomer> data, ConEdRegion info, boolean sendAll) throws Exception {
        List<PlutoCustomer> temp3 = filterLocationIn(dsMysql, data, new ArrayList<>(info.getBorder().values()));
        HtmlBuilder builder = new HtmlBuilder(info);


        List<String> filter = LocationDAO.getLocationFilterInfo(dsMysql, info.get_ZONE());
        List<PlutoCustomer> filteredList = new ArrayList<>(temp3);
        for(String s : filter) {
            List<PlutoCustomer> temp = filteredList.stream()
                    .filter(p -> !p.getAddress().contains(s)).collect(Collectors.toList());
            filteredList = temp;
        }

        Collections.sort(filteredList, new Comparator<PlutoCustomer>() {
            @Override
            public int compare(PlutoCustomer o1, PlutoCustomer o2) {
                return oilTypeSort(o1.getPrimaryFuelType()).compareTo(oilTypeSort(o2.getPrimaryFuelType()));
            }
        });


        for (PlutoCustomer c : filteredList) {
            builder.addToTable(c.getAddress(), c.getNumberUnit(), c.getPrimaryFuelType(), c.getBoilerMake(), c.getBurnerMake(),
                    "<a href=\"http://www.latlong.net/c/?lat=" + c.getLatitude() + "&long=" + c.getLongitude() + "\"> click </a>",
                    "<a href=\"http://dualfuel.mono-tek.com/Pluto/GetBBL?BBL=" + c.getBbl() + "#tab8" + "\">" + "http://dualfuel.mono-tek.com/Pluto/GetBBL?BBL=" + c.getBbl() + "#tab8" + "</a>"
            );
        }

//        mailSender.sendMail("rparikh@dualfuelcorp.com, steven@dualfuelcorp.com, steven@dualfuelcorp.com, jbohm@dualfuelcorp.com", "ConEd: Sales opportunity in Gas Conversion Growth Zone I5", builder.getHtml(), MailMessage.Type.HTML);
        if (filteredList.size() > 0) {
            if (sendAll) {
                mailSender.sendMail("rparikh@dualfuelcorp.com, steven@dualfuelcorp.com, jbohm@dualfuelcorp.com", "ConEd: Sales opportunity in Gas Conversion Growth Zone " + info.get_ZONE(), builder.getHtml(), MailMessage.Type.HTML);
            } else {
                mailSender.sendMail("rparikh@dualfuelcorp.com", "ConEd: Sales opportunity in Gas Conversion Growth Zone " + info.get_ZONE(), builder.getHtml(), MailMessage.Type.HTML);
            }
//
        } else {
            System.out.println("");
            System.out.println("Nothing found");
        }
    }

    public void printPctComplete(int total, int completed) {
        int pctComplete = (int) (completed * 100.0 / total);
        int pctCompleteLast = (int) ((completed - 1) * 100.0 / total);

        if (pctComplete % 5 == 0) {
            if (pctComplete != pctCompleteLast) {
                System.out.print("\t" + pctComplete + "% complete");
            }
        }
    }

    public List<PlutoCustomer> filterLocationIn(DataSource dsMysql, List<PlutoCustomer> data, List<Geocode> border) throws SQLException {
        double areaORegion = calculateArea(border);
        List<PlutoCustomer> temp = new ArrayList<>();
        for (PlutoCustomer c : data) {

            Integer id = LocationDAO.getLocationId(dsMysql, c.getAddress(), c.getZip());
            if (id != null) {
                LocationInfo info = LocationDAO.getLocationInfo(dsMysql, id);
                c.setLatitude(info.getLatitude());
                c.setLongitude(info.getLongitude());
                if (isWithinArea(border, new Geocode(info.getLatitude(), info.getLongitude()), areaORegion, info.getFullAddress())) {
                    temp.add(c);
                }
            }
        }

        return temp;

        //isWithinArea(border, new Geocode("40.855062", "-73.933579"), areaORegion, "Hello");
    }

    public boolean isWithinArea(List<Geocode> border, Geocode point, double areaOfReg, String address) {
        List<Geocode> tmp = new ArrayList<Geocode>(border);
        tmp.add(point);
        double area = calculateArea(tmp);

        double diff = MathUtils.round(area - areaOfReg, 6);
        if (diff <= 0) {
            // System.out.println(address);
            return true;
        }
        return false;
    }

    private double calculateArea(List<Geocode> codes) {
        Collections.sort(codes, new Comparator<Geocode>() {
            @Override
            public int compare(Geocode o1, Geocode o2) {
                int c = o1.getLatitude().compareTo(o2.getLatitude());
                if (c == 0) {
                    c = o1.getLongitude().compareTo(o2.getLongitude());
                }
                return c;
            }
        });

        double area = 0.0;

        for (int i = 0; i < codes.size() - 1; i++) {
            area = area + ((codes.get(i).getLongitude() + codes.get(i + 1).getLongitude()) / 2 * (codes.get(i + 1).getLatitude() - codes.get(i).getLatitude()));
        }

        return MathUtils.round(Math.abs(area), 10);
    }

}
