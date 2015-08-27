package com.job.helper;

import com.dualfual.common.DateUtil;
import com.dualfual.remote.EntechRemote;
import com.dualfual.remote.SeleniumRemote;
import com.job.core.base.MantraJobExecutionResult;
import org.joda.time.DateTime;

public class EntechRemoteHelper implements RemoteHelper {

    @Override
    public void performWork(String loginUrl, String username, String password, String temp) throws Exception{

        SeleniumRemote remote = new EntechRemote(temp);
        try {

            remote.login(loginUrl, username, password);

            DateTime now = DateTime.now();

            int date = DateUtil.getIntDate(now);
            int min = now.getMinuteOfHour();
            int hour = now.getMinuteOfHour();
            if(hour == 0 && min < 30){
                int yesterday = DateUtil.addDays(date, -1);
                remote.downloadAllFile(yesterday, temp);
            }
            date = DateUtil.addDays(date, 0);
            remote.downloadAllFile(date, temp);
        } finally {
            remote.logout();
        }

    }
}
