package com.dualfual;


import com.dualfual.common.DateUtil;
import com.dualfual.remote.ConEdRemote;
import com.dualfual.remote.EntechRemote;
import com.dualfual.remote.SeleniumRemote;
import com.dualfual.remote.coned.ConEdAddress;
import com.dualfual.remote.coned.ConEdProject;
import org.apache.poi.util.IOUtils;
import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.util.List;

public class TestEntechRemote {

    @Test
    @Ignore
    public void testTestRemote() throws Exception {
        SeleniumRemote remote = new EntechRemote("C:\\Temp");

        try {
            remote.login("http://virtualremote500.com/(S(qv3ppfqp3eg4kwfbvd5awkqq))/EntechLogin.aspx?ReturnUrl=%2f",
                    "dualfuelbarberryrose", "dualfuel1");
            List<WebElement> elements = remote.listElementsById("");
            DateTime now = DateTime.now();

            int date = DateUtil.getIntDate(now);
            date = DateUtil.addDays(date, 0);

            for (WebElement e : elements) {
                String id = e.getAttribute("class");
                remote.downloadFile(e, id, date, "C:\\Temp\\");
            }
        }finally {
            remote.logout();
        }

    }

    @Test
    @Ignore
    public void testTestRemote2() throws Exception {
        ConEdRemote remote = new ConEdRemote();

        try {
            remote.login("https://apps.coned.com/ESWEB/Login.aspx",
                    "jbohm@dualfuelcorp.com", "nissan22");
            List<ConEdProject> projects = remote.performSearch(new ConEdAddress("", "", "", ""));

            for(ConEdProject p : projects){
                FileOutputStream out = new FileOutputStream("C:\\temp\\doc1.pdf");
                FileOutputStream out2 = new FileOutputStream("C:\\temp\\doc2.pdf");

                IOUtils.copy(new ByteArrayInputStream(p.getOriginalRequest()), out);
                IOUtils.copy(new ByteArrayInputStream(p.getUpdatedRequest()), out2);
            }

        }finally {
            remote.logout();

        }

    }

}
