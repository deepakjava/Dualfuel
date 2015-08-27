package com.test.datasource;


import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

public class TestDataSource {

    public static DataSource createSqlServerDataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl("jdbc:sqlserver://167.114.211.57:1733;databaseName=Pluto");
        dataSource.setUsername("RupeshParikh");
        dataSource.setPassword("D8XVuPTW?g4b\\La!");
        return dataSource;
    }

    public static DataSource createMySqlDataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://167.114.211.57:3306/dualfual");
        dataSource.setUsername("dfAppUserRemote");
        dataSource.setPassword("D8XVuPTW?g4bLa!");
        return dataSource;
    }
}
