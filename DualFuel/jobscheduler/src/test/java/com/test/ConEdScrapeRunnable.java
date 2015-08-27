package com.test;


import com.dto.PlutoCustomer;
import com.job.helper.ConEdCustomRemoteHelper;
import com.job.helper.ConEdRemoteHelper;
import com.job.helper.RemoteHelper;

import javax.sql.DataSource;
import java.util.List;

public class ConEdScrapeRunnable implements Runnable {

    List<PlutoCustomer> customers;
    private DataSource dsSqlServer;
    private DataSource dsMysql;

    public ConEdScrapeRunnable(List<PlutoCustomer> customers, DataSource dsSqlServer, DataSource dsMysql) {
        this.customers = customers;
        this.dsSqlServer = dsSqlServer;
        this.dsMysql = dsMysql;
    }

    @Override
    public void run() {


        boolean isComplete = false;

        while(!isComplete) {
            ConEdRemoteHelper helper = new ConEdCustomRemoteHelper(customers);
            helper.setDsMysql(dsMysql);
            helper.setDsSqlServer(dsSqlServer);

            try {
                helper.performWork("https://apps.coned.com/ESWEB/Login.aspx",
                        "jbohm@dualfuelcorp.com", "nissan22", "C:\\Temp\\");
                isComplete = true;
                System.out.println("done");
            } catch (Exception e) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }

    }
}

