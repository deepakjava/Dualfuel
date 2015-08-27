package com.appmanager;

import com.dto.*;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CachedObjectFactory {

    private static ExecutorService service = Executors.newCachedThreadPool();

    public static final AtomicReference<List<PlutoCustomer>> plutoCustomerRef = new AtomicReference<>(null);

    public static void start() {
        service.submit(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1*60*1000);
                        ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
                        DataSource dataSourceSqlServer = (DataSource) ctx.getBean("dataSourceSqlServer");
                        DataSource dataSource = (DataSource) ctx.getBean("dataSource");
                        List<PlutoCustomer> data = PlutoDAO.getPlutoAddress(dataSourceSqlServer);
                        List<PlutoCustomer> data1 = AddressDAO.getPlutoCustomer(dataSource);
                        data.addAll(data1);
                        //data = data.stream().filter(p -> p.getBldgArea() >= 50000).map(Function.<PlutoCustomer>identity()).collect(Collectors.toCollection(ArrayList::new));
                        LocationDAO.fillLocationInfo(dataSource, data);
                        ConEdProjectDAO.getConEdProjectInfo(dataSource, data);
                       // List<PlutoCustomer> data = ConEdProjectDAO.getAllConEdProjects(dataSource);
                        plutoCustomerRef.set(data);
                        System.out.println("Object is cached....");
                        Thread.sleep(4*60*1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void stop(){
        service.shutdownNow();
    }

}
