package com.job.helper;

import com.dto.*;
import com.dualfual.common.DateUtil;
import com.dualfual.remote.ConEdRemote;
import com.dualfual.remote.coned.ConEdAddress;
import com.dualfual.remote.coned.ConEdProject;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import javax.sql.DataSource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConEdRemoteHelper implements RemoteHelper {

    private DataSource dsSqlServer;
    private DataSource dsMysql;

    public void setDsSqlServer(DataSource dsSqlServer) {
        this.dsSqlServer = dsSqlServer;
    }

    public void setDsMysql(DataSource dsMysql) {
        this.dsMysql = dsMysql;
    }


    private Map<String, String> areaCode = new HashMap() {{
        put("MN", "Manhattan");
        put("BK", "Brooklyn");
        put("BX", "Bronx");
        put("QN", "Queens");
        put("SI", "Staten Island");
        put("WESTCHESTER", "Westchester");
    }};

    public List<PlutoCustomer> filterData(List<PlutoCustomer> data) {
        return data.stream().filter(p -> p.getBldgArea() >= 50000).map(Function.<PlutoCustomer>identity()).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void performWork(String loginUrl, String username, String password, String temp) throws Exception {

        ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
        if (dsSqlServer == null)
            dsSqlServer = (DataSource) ctx.getBean("dataSourceSqlServer");

        if (dsMysql == null)
            dsMysql = (DataSource) ctx.getBean("dataSource");

        List<PlutoCustomer> data =  null;
        if(!(this instanceof  ConEdCustomRemoteHelper)){
            data = PlutoDAO.getPlutoAddress(dsSqlServer);
            List<PlutoCustomer> data1 = AddressDAO.getPlutoCustomer(dsMysql);
            data.addAll(data1);
        }

        data = filterData(data);
//        data = data.stream().filter(p -> p.getBldgArea() >= 50000).map(Function.<PlutoCustomer>identity()).collect(Collectors.toCollection(ArrayList::new));
        //List<PlutoCustomer> data2 = data.stream().filter(p -> p.getBldgArea() >= 50000).map(Function.<PlutoCustomer>identity()).collect(Collectors.toCollection(ArrayList::new));
        LocationDAO.fillLocationInfo(dsMysql, data);
        //List<PlutoCustomer> data3 = data.stream().filter(p -> p.getFullAddress() != null && p.getFullAddress().toUpperCase().contains("BRONX")).map(Function.<PlutoCustomer>identity()).collect(Collectors.toCollection(ArrayList::new));

        performWork(data, dsMysql, loginUrl, username, password);
    }

    public void performWork(List<PlutoCustomer> data3, DataSource dsMysql, String loginUrl, String username, String password) throws Exception {

        ConEdRemote remote = new ConEdRemote();

        try {

            remote.login(loginUrl,
                    username, password);

            int today = DateUtil.getIntDate(DateTime.now());
            int errorCounter = 0;
            for (PlutoCustomer c : data3) {
                try {
                    Date lastUpdated = ConEdProjectDAO.getLastUpdated(dsMysql, c);

                    if (lastUpdated != null) {
                        int testDate = DateUtil.getIntDate(lastUpdated);

                        if (DateUtil.addDays(testDate, 2) > today) {
                            continue;
                        }
                    }
                    int idx = c.getAddress().indexOf(" ");
                    String streetNumber = c.getAddress().substring(0, idx);
                    String street = c.getAddress().substring(idx).trim();
                    String zip = c.getZip();

                    String fullAddress = c.getFullAddress();
                    if (fullAddress != null) {

                        String[] tokens = fullAddress.split(",");
                        String[] zipState = tokens[tokens.length - 2].trim().split(" ");
                        String region = tokens[tokens.length - 3].trim();
                        String streetAddress = tokens[tokens.length - 4].trim();
                        streetNumber = streetAddress.substring(0, streetAddress.indexOf(" ")).trim();
                        street = streetAddress.substring(streetAddress.indexOf(" ")).trim();
                        zip = zipState[1].trim();
                        String state = zipState[0].trim();

                    }


                    String a = areaCode.get(c.getBorough());
                    if (a == null)
                        continue;

                    List<ConEdProject> projects = remote.performSearch(new ConEdAddress(streetNumber, street, zip, a));
                    ConEdProjectDAO.saveConEdProjectInfo(dsMysql, c, projects);
                    errorCounter = 0;
                } catch (Exception e) {
                    errorCounter++;
                    if (errorCounter > 100) {
                        throw e;
                    }
                }
            }

        } finally {
            remote.logout();

        }
    }
}
