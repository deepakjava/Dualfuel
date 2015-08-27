package com.dto;

import com.dualfual.pdf.Address;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddressDAO {

    public static void saveAddress(DataSource ds, List<Address> addresses) throws SQLException {
        Connection conn = null;
        PreparedStatement psmt1 = null;
        PreparedStatement psmt = null;
        try {
            conn = ds.getConnection();
            psmt1 = conn.prepareStatement("delete from dualfual.address where address_key=?");
            psmt = conn.prepareStatement("INSERT INTO dualfual.address(address, Borough, zip, town, state, address_key) VALUES (?, ?, ?, ?, ?, ?)");
            for (Address entry : addresses) {
                psmt.setString(1, entry.getAddress());
                psmt.setString(2, entry.getBorough());
                psmt.setString(3, entry.getZip());
                psmt.setString(4, entry.getTown());
                psmt.setString(5, entry.getState());
                psmt.setString(6, entry.getUniqueKey());
                psmt1.setString(1, entry.getUniqueKey());
                psmt1.execute();
                psmt.execute();
            }

        } finally {

            if (psmt != null) {
                psmt.close();
            }

            if (psmt1 != null) {
                psmt1.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }

    public  static List<PlutoCustomer> getPlutoCustomer(DataSource ds) throws SQLException {

        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        List<PlutoCustomer> customers = new ArrayList<>();
        try {

            conn = ds.getConnection();
            psmt = conn.prepareStatement("SELECT ID, address, Borough, zip, town, state, address_key FROM dualfual.address");


            rs = psmt.executeQuery();
            while (rs.next()) {
               PlutoCustomer c = new PlutoCustomer(rs.getString(1),
                       rs.getString(3), rs.getString(4), rs.getString(2));

                customers.add(c);
            }


        } finally {

            if (rs != null)
                rs.close();

            if (psmt != null)
                psmt.close();

            if (conn != null)
                conn.close();
        }

        return customers;
    }

}
