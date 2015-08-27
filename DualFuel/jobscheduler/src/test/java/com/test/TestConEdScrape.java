package com.test;

import com.dto.*;
import com.dualfual.common.DateUtil;
import com.dualfual.remote.ConEdRemote;
import com.dualfual.remote.coned.ConEdAddress;
import com.dualfual.remote.coned.ConEdProject;
import com.job.helper.ConEdBronxRemoteHelper;
import com.job.helper.ConEdCustomRemoteHelper;
import com.job.helper.RemoteHelper;
import com.test.datasource.TestDataSource;
import org.apache.poi.util.IOUtils;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Ignore
public class TestConEdScrape {

    private ApplicationContext context = null;

    @Before
    public void init() {
        context =
                new ClassPathXmlApplicationContext(new String[]{"spring-beans_def.xml"});

    }

    private Map<String, String> areaCode = new HashMap() {{
        put("MN", "Manhattan");
        put("BK", "Brooklyn");
        put("BX", "Bronx");
        put("QN", "Queens");
        put("SI", "Staten Island");
        put("WESTCHESTER", "Westchester");
    }};

    @Test
    @Ignore
    public void fillConEdData() throws Exception {

        DataSource dsSqlServer = TestDataSource.createSqlServerDataSource();
        DataSource dsMysql = TestDataSource.createMySqlDataSource();


        List<PlutoCustomer> data = PlutoDAO.getPlutoAddress(dsSqlServer);
        List<PlutoCustomer> data1 = AddressDAO.getPlutoCustomer(dsMysql);

        data.addAll(data1);
//        List<PlutoCustomer> data2 = data.stream().filter(p -> p.getBldgArea() >= 50000).map(Function.<PlutoCustomer>identity()).collect(Collectors.toCollection(ArrayList::new));
//        List<PlutoCustomer> data2 = data.stream().filter(p -> (p.getBorough().equalsIgnoreCase("BX"))).map(Function.<PlutoCustomer>identity()).collect(Collectors.toCollection(ArrayList::new));
        List<PlutoCustomer> data2 = data.stream().filter(p -> (p.getBorough().equalsIgnoreCase("MN"))).map(Function.<PlutoCustomer>identity()).collect(Collectors.toCollection(ArrayList::new));


        //String area = "Manhattan";
//        List<PlutoCustomer> data3 = data2.stream().filter(p -> p.getFullAddress() != null && p.getFullAddress().toUpperCase().contains("BRONX")).map(Function.<PlutoCustomer>identity()).collect(Collectors.toCollection(ArrayList::new));


        //System.out.println(data2);

        int loop = data2.size() / 1;

        ExecutorService service = Executors.newFixedThreadPool(15);

        for(int i =0; i<1; i++) {
            service.submit(new ConEdScrapeRunnable(data2.subList(i * loop,  loop * (i+1)), dsSqlServer, dsMysql));
        }

        service.shutdown();

        service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        //    service.submit(new ConEdScrapeRunnable(data2.subList(loop * 10,  data2.size() -1)));
    }




    @Test
    @Ignore
    public void testQuery() throws SQLException {

        DataSource dsSqlServer = TestDataSource.createSqlServerDataSource();
        DataSource dsMysql = TestDataSource.createMySqlDataSource();

        int tillDate = DateUtil.addDays(DateTime.now(), -7);

        //List<PlutoCustomer> data = PlutoDAO.getPlutoData(dsSqlServer);
        Map<String, Map<String, List<Integer>>> idMap = ConEdProjectDAO.getConEdProjectIds(dsMysql);
        // List<Map<String, Object>> activeTeamMap = new ArrayList<Map<String, Object>>();

        for (String bbl : idMap.keySet()) {
            for (String sId : idMap.get(bbl).keySet()) {
                if (idMap.get(bbl).get(sId).size() > 1) {
                    System.out.println(sId);
                }
            }
        }
    }


    //data = data.stream().filter(p -> p.getBldgArea() >= 50000).map(Function.<PlutoCustomer>identity()).collect(Collectors.toCollection(ArrayList::new));
//        LocationDAO.fillLocationInfo(dsMysql, data);


}
