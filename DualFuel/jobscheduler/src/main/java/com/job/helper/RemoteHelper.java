package com.job.helper;

import javax.sql.DataSource;

public interface RemoteHelper {

    public void performWork(String loginUrl, String username, String password, String temp) throws Exception;

}
