package com.dto;

import com.dualfual.google.Geocode;
import com.dualfual.google.GeocodeResponse;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationDAO {

    public static ConEdRegion getRegionCodeInfo(DataSource ds, String regionCode) throws SQLException {
        Connection conn = null;
        PreparedStatement psmt = null;
        PreparedStatement psmt2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        ConEdRegion region = null;
        try {
            conn = ds.getConnection();
            psmt = conn.prepareStatement("SELECT REGION_CODE, NORTH, SOUTH, EAST, WEST, ID FROM dualfual.region_code where REGION_CODE =?");
            psmt.setString(1, regionCode);
            rs = psmt.executeQuery();
            Integer id = null;
            if (rs.next()) {
                region = new ConEdRegion(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), new HashMap<>());
                id = rs.getInt(6);
            }

            if (id != null) {
                psmt2 = conn.prepareStatement("SELECT INFO, LATITUDE, LONGITUDE FROM dualfual.region_border_geocode where REGION_CODE_ID =?");
                psmt2.setInt(1, id);
                rs2 = psmt2.executeQuery();
                while (rs2.next()) {
                    region.getBorder().put(rs2.getString(1), new Geocode(rs2.getString(2), rs2.getString(3)));
                }
            }

        } finally {
            if (rs != null) {
                rs.close();
            }

            if (psmt != null) {
                psmt.close();
            }

            if (rs2 != null) {
                rs2.close();
            }

            if (psmt2 != null) {
                psmt2.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
        return region;
    }

    public static Integer getRegionCodeId(DataSource ds, String regionCode) throws SQLException {
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Integer id = null;
        try {
            conn = ds.getConnection();
            psmt = conn.prepareStatement("SELECT ID FROM dualfual.region_code where REGION_CODE =?");
            psmt.setString(1, regionCode);
            rs = psmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt(1);
            }

        } finally {
            if (rs != null) {
                rs.close();
            }

            if (psmt != null) {
                psmt.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
        return id;
    }

    public static void saveRegionBorder(DataSource ds, ConEdRegion d) throws SQLException {
        Connection conn = null;
        PreparedStatement psmt = null;
        try {
            conn = ds.getConnection();

            Integer id = getRegionCodeId(ds, d.get_ZONE());
            if (id == null) {
                throw new RuntimeException(d.get_ZONE() + " - entry not found in database");
            }
            psmt = conn.prepareStatement("INSERT INTO dualfual.region_border_geocode(INFO, LATITUDE, LONGITUDE, REGION_CODE_ID) VALUES (?, ?, ?, ?)");

            for (Map.Entry<String, Geocode> entry : d.getBorder().entrySet()) {
                psmt.setString(1, entry.getKey());
                psmt.setString(2, entry.getValue().getLatitudeStr());
                psmt.setString(3, entry.getValue().getLongitudeStr());
                psmt.setInt(4, id);
                psmt.addBatch();
            }

            psmt.executeBatch();

        } finally {

            if (psmt != null) {
                psmt.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }

    public static Integer getLocationId(PreparedStatement psmt, String address, String zipCode) throws SQLException {
        ResultSet rs = null;
        Integer id = null;
        try {
            psmt.setString(1, address.toUpperCase().replaceAll(" ", "") + "_" + zipCode);
            rs = psmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt(1);
            }

        } finally {
            if (rs != null) {
                rs.close();
            }

        }
        return id;
    }

    public static Integer getLocationId(DataSource ds, String address, String zipCode) throws SQLException {
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        SQLServerDriver s = null;
        Integer id = null;
        try {
            conn = ds.getConnection();
            psmt = conn.prepareStatement("select k.location_info_id from dualfual.plutokey_location k where k.Adress_Key =?");
            psmt.setString(1, address.toUpperCase().replaceAll(" ", "") + "_" + zipCode);
            rs = psmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt(1);
            }

        } finally {
            if (rs != null) {
                rs.close();
            }

            if (psmt != null) {
                psmt.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
        return id;
    }

    public static void fillLocationInfo(PreparedStatement psmt, PlutoCustomer customers) throws SQLException {


        // conn = ds.getConnection();
        //psmt2 = conn.prepareStatement("SELECT Full_Address, Latitude, Longitude FROM dualfual.location_info where ID = ?");

        Integer id = customers.getLocationId();
        if (id == null) {
            return;
        }

        LocationInfo info = getLocationInfo(psmt, id);
        if (info != null) {
            customers.setLatitude(info.getLatitude());
            customers.setLongitude(info.getLongitude());
            customers.setFullAddress(info.getFullAddress());
            customers.setLocationId(id);
        }


    }

    public static void fillLocationInfo(DataSource ds, List<PlutoCustomer> customers) throws SQLException {
        Connection conn = null;
        PreparedStatement psmt1 = null;
        PreparedStatement psmt2 = null;

        try {
            conn = ds.getConnection();
            psmt1 = conn.prepareStatement("select k.location_info_id from dualfual.plutokey_location k where k.Adress_Key =?");
            psmt2 = conn.prepareStatement("SELECT Full_Address, Latitude, Longitude FROM dualfual.location_info where ID = ?");

            for (PlutoCustomer c : customers) {
                Integer id = getLocationId(psmt1, c.getAddress(), c.getZip());
                if (id == null) {
                    continue;
                }

                LocationInfo info = getLocationInfo(psmt2, id);
                if (info != null) {
                    c.setLatitude(info.getLatitude());
                    c.setLongitude(info.getLongitude());
                    c.setFullAddress(info.getFullAddress());
                    c.setLocationId(id);
                }
            }
        } finally {
            if (psmt1 != null) {
                psmt1.close();
            }

            if (psmt2 != null) {
                psmt2.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }


    public static LocationInfo getLocationInfo(DataSource ds, String address, String zipCode) throws SQLException {
        Integer id = getLocationId(ds, address, zipCode);
        if (id == null) {
            return null;
        }
        return getLocationInfo(ds, id);
    }

    public static LocationInfo getLocationInfo(PreparedStatement psmt, int locId) throws SQLException {
        ResultSet rs = null;
        LocationInfo info = null;
        try {
            psmt.setInt(1, locId);
            rs = psmt.executeQuery();

            if (rs.next()) {
                info = new LocationInfo(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3));
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        return info;
    }


    public static LocationInfo getLocationInfo(DataSource ds, int locId) throws SQLException {
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        LocationInfo info = null;
        try {
            conn = ds.getConnection();
            psmt = conn.prepareStatement("SELECT Full_Address, Latitude, Longitude FROM dualfual.location_info where ID = ?");
            psmt.setInt(1, locId);
            rs = psmt.executeQuery();

            if (rs.next()) {
                info = new LocationInfo(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3));
            }

        } finally {
            if (rs != null) {
                rs.close();
            }

            if (psmt != null) {
                psmt.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
        return info;
    }

    public static List<String> getLocationFilterInfo(DataSource ds, String zone) throws SQLException {
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        List<String> temp = new ArrayList<>();
        try {
            conn = ds.getConnection();
            psmt = conn.prepareStatement("SELECT EXCLUDE FROM dualfual.location_filter_list where ZONE = ?");
            psmt.setString(1, zone);
            rs = psmt.executeQuery();

            while (rs.next()) {
                temp.add(rs.getString(1));
            }

        } finally {
            if (rs != null) {
                rs.close();
            }

            if (psmt != null) {
                psmt.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
        return temp;
    }

    public static void addLocation(DataSource ds, String address, String zipCode, GeocodeResponse response) throws SQLException {
        Connection conn = null;
        PreparedStatement psmt = null;
        PreparedStatement psmt2 = null;
        Boolean autoCommit = null;
        try {
            conn = ds.getConnection();
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            psmt = conn.prepareStatement("INSERT INTO dualfual.location_info(Full_Address, Latitude, Longitude) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            psmt.setString(1, response.getResult().getFormatted_address());
            psmt.setString(2, response.getResult().getGeometry().getLocation().getLat());
            psmt.setString(3, response.getResult().getGeometry().getLocation().getLng());
            psmt.execute();

            ResultSet generatedKeys = psmt.getGeneratedKeys();
            int id = -1;
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }

            generatedKeys.close();

            String key = address.toUpperCase().replaceAll(" ", "") + "_" + zipCode;
            psmt2 = conn.prepareStatement("INSERT INTO dualfual.plutokey_location(Adress_Key, location_info_id) VALUES (?, ?)");
            psmt2.setString(1, key);
            psmt2.setInt(2, id);
            psmt2.execute();

            conn.commit();

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
        } finally {

            if (autoCommit != null) {
                conn.setAutoCommit(autoCommit);
            }

            if (psmt != null) {
                psmt.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }
}
