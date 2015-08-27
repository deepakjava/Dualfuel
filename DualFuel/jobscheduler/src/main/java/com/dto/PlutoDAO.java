package com.dto;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlutoDAO {

    public static List<PlutoCustomer> getPlutoDataFuelType_6(DataSource ds) throws SQLException{
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        SQLServerDriver s = null;
        List<PlutoCustomer> plutoCustomers = new ArrayList<>(400);
        try{
            conn = ds.getConnection();
            psmt = conn.prepareStatement("select d.BBL, d.Borough, d.Zip, d.Address, d.BldgArea, d.UnitsRes ,\n" +
                    "dt.Boiler_Make_Model, dt.Burner_Make_Model, dt.Fuel_Type_1, dt.Fuel_Type_2, dt.Number_Units, dt.Boiler_Yr\n" +
                    "from dbo.PlutoData d, dbo.DEP_Details dt\n" +
                    "where \n" +
//                    "d.Zip in ('10034', '10040', '10039', '10033', '10032', '10039', '10031', '10030', '10037', '10128', '10028') and d.BBL = dt.BBL\n" +
                    "dt.Fuel_Type_1 like ('%6%') and d.BBL = dt.BBL\n" +
                    "and dt.IsActive = 1\n" +
                    "and dt.Fuel_Type_1 not like '%gas%'\n" +
                    "and dt.Fuel_Type_2 not like '%gas%'");
            rs = psmt.executeQuery();

            while(rs.next()){

                PlutoCustomer plutoCustomer = new PlutoCustomer(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12)
                );

                plutoCustomers.add(plutoCustomer);
            }

        }finally {
            if(rs != null){
                rs.close();
            }

            if(psmt != null){
                psmt.close();
            }

            if(conn != null){
                conn.close();
            }
        }

        return plutoCustomers;
    }

    public static List<PlutoCustomer> getPlutoAddress(DataSource ds) throws SQLException{
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        SQLServerDriver s = null;
        List<PlutoCustomer> plutoCustomers = new ArrayList<>(400);
        try{
            conn = ds.getConnection();
            psmt = conn.prepareStatement("select d.BBL, d.Borough, d.Zip, d.Address, d.BldgArea, d.UnitsRes\n" +
                            "from dbo.PlutoData d where d.BldgArea > 5000");
            rs = psmt.executeQuery();

            while(rs.next()){

                PlutoCustomer plutoCustomer = new PlutoCustomer(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDouble(5),
                        rs.getDouble(6),null, null, null, null, null, null
                );

                plutoCustomers.add(plutoCustomer);
            }

        }finally {
            if(rs != null){
                rs.close();
            }

            if(psmt != null){
                psmt.close();
            }

            if(conn != null){
                conn.close();
            }
        }

        return plutoCustomers;
    }

    public static List<PlutoCustomer> getPlutoData2(DataSource ds) throws SQLException{
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        SQLServerDriver s = null;
        List<PlutoCustomer> plutoCustomers = new ArrayList<>(400);
        try{
            conn = ds.getConnection();
            psmt = conn.prepareStatement("select d.BBL, d.Borough, d.Zip, d.Address, d.BldgArea, d.UnitsRes ,\n" +
                    "dt.Boiler_Make_Model, dt.Burner_Make_Model, dt.Fuel_Type_1, dt.Fuel_Type_2, dt.Number_Units, dt.Boiler_Yr\n" +
                    "from dbo.PlutoData d, dbo.DEP_Details dt\n" +
                    "where \n" +
                    "d.BBL = dt.BBL\n" +
//                    "d.Zip in ('10034', '10040', '10039', '10033', '10032', '10039', '10031', '10030', '10037', '10128', '10028') and d.BBL = dt.BBL\n" +
//                    "d.Zip in ('10468', '10458', '10457', '10453', '10467', '10463') and d.BBL = dt.BBL\n" +
//                    "dt.Fuel_Type_1 like ('%6%') and d.BBL = dt.BBL\n" +
                    "and dt.IsActive = 1\n" +
//                    "and dt.Fuel_Type_1 not like '%gas%'\n" +
//                    "and dt.Fuel_Type_2 not like '%gas%'");
                    "");
            rs = psmt.executeQuery();

            while(rs.next()){

                PlutoCustomer plutoCustomer = new PlutoCustomer(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12)
                );

                plutoCustomers.add(plutoCustomer);
            }

        }finally {
            if(rs != null){
                rs.close();
            }

            if(psmt != null){
                psmt.close();
            }

            if(conn != null){
                conn.close();
            }
        }

        return plutoCustomers;
    }
}
