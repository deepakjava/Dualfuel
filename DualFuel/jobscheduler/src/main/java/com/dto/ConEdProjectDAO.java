package com.dto;

import com.dualfual.common.DateUtil;
import com.dualfual.remote.coned.ConEdProject;
import com.dualfual.remote.coned.ConEdStatus;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConEdProjectDAO {

    public static Date getLastUpdated(DataSource ds, PlutoCustomer customer) throws SQLException {
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Date lastUpdated = null;
        try {

            conn = ds.getConnection();
            psmt = conn.prepareStatement("SELECT LAST_UPDATED FROM dualfual.con_ed_project where BBL = ?");
            psmt.setString(1, customer.getBbl());

            rs = psmt.executeQuery();

            if (rs.next()) {
                lastUpdated = new Date(rs.getTimestamp(1).getTime());
            }

        } finally {

            if (rs != null)
                rs.close();

            if (psmt != null)
                psmt.close();

            if (conn != null)
                conn.close();

        }

        return lastUpdated;
    }

    public static byte[] getOriginalRequest(DataSource ds, int id) throws SQLException {

        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        byte[] temp = null;

        try {

            conn = ds.getConnection();
            psmt = conn.prepareStatement("SELECT d.ORIGINAL_REQ \n" +
                    "            FROM dualfual.con_ed_project_det d\n" +
                    "            where d.ID = ?");


            psmt.setInt(1, id);

            rs = psmt.executeQuery();

            if (rs.next()) {
                temp = rs.getBytes(1);
            }


        } finally {
            if (psmt != null)
                psmt.close();

            if (conn != null)
                conn.close();
        }

        return temp;
    }

    public static byte[] getUpdatedRequest(DataSource ds, int id) throws SQLException {

        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        byte[] temp = null;

        try {

            conn = ds.getConnection();
            psmt = conn.prepareStatement("SELECT d.UPDATED_REQ \n" +
                    "            FROM dualfual.con_ed_project_det d\n" +
                    "            where d.ID = ?");


            psmt.setInt(1, id);

            rs = psmt.executeQuery();

            if (rs.next()) {
                temp = rs.getBytes(1);
            }


        } finally {
            if (psmt != null)
                psmt.close();

            if (conn != null)
                conn.close();
        }

        return temp;
    }

    public static Map<String, Map<String, List<Integer>>> getConEdProjectIds(DataSource ds) throws SQLException {

        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Map<String, Map<String, List<Integer>>> temp = new HashMap<>();
        try {

            conn = ds.getConnection();
            psmt = conn.prepareStatement("SELECT p.BBL, d.SERVICE_REQ, d.ID, d.SERVICE_ADDRESS FROM dualfual.con_ed_project p, dualfual.con_ed_project_det d\n" +
                    "            where p.ID=d.PROJECT_ID");


            rs = psmt.executeQuery();
            while (rs.next()) {
                String bbl = rs.getString(1);
                Map<String, List<Integer>> tickets = temp.get(bbl);
                if (tickets == null) {
                    tickets = new HashMap<>();
                    temp.put(bbl, tickets);
                }

                List<Integer> ids = tickets.get(rs.getString(2));

                if (ids == null) {
                    ids = new ArrayList<>();
                    tickets.put(rs.getString(2), ids);
                }

                ids.add(rs.getInt(3));
            }


        } finally {

            if (rs != null)
                rs.close();

            if (psmt != null)
                psmt.close();

            if (conn != null)
                conn.close();
        }

        return temp;
    }

    public static List<PlutoCustomer> getAllConEdProjects(DataSource ds) throws SQLException {

        Connection conn = null;
        PreparedStatement psmt = null;
        PreparedStatement psmt2 = null;
        ResultSet rs = null;

        Map<String, PlutoCustomer> temp = new LinkedHashMap<>();

        try {

            conn = ds.getConnection();
            psmt = conn.prepareStatement("SELECT p.BBL, d.SERVICE_REQ, d.SERVICE_ADDRESS, d.REQUEST_TYPE, d.DATE_SUBMITTED, d.UTILITY_TYPE, d.CUSTOMER_REP, d.STATUS, d.ID, d.ORIG_OIL_CON, d.UPDATED_OIL_CON, d.SCOPE_OF_WORK, " +
                    " d.CURRENT_STATUS, d.CURRENT_STATUS_LINK, d.CURRENT_STATUS_IMG, d.PREV_STATUS, d.PREV_STATUS_LINK, d.PREV_STATUS_IMG, p.LOCATION_ID \n" +
                    "            FROM dualfual.con_ed_project p, dualfual.con_ed_project_det d\n" +
                    "            where p.ID=d.PROJECT_ID ");

            psmt2 = conn.prepareStatement("SELECT Full_Address, Latitude, Longitude FROM dualfual.location_info where ID = ?");

            rs = psmt.executeQuery();
            while (rs.next()) {


                PlutoCustomer c = temp.get(rs.getString(1));

                if(c == null){
                    c = new PlutoCustomer(rs.getString(1));
                    c.setConEdProjects(new ArrayList<>());
                    temp.put(rs.getString(1), c);
                }

                if(c.getLocationId() == null){
                    Integer id = rs.getInt(19);
                    if(rs.wasNull()){
                        id = null;
                    }
                    c.setLocationId(id);
                    LocationDAO.fillLocationInfo(psmt2, c);
                }

                ConEdProject p = new ConEdProject(rs.getString(2), rs.getString(3), rs.getString(4));
                p.setDateSubmitted(DateUtil.df_MMddyyyy.print(rs.getDate(5).getTime()));
                p.setUtilityType(rs.getString(6));
                p.setCustomerRep(rs.getString(7));
//                        p.setOriginalRequest(rs.getBytes(8));
//                        p.setUpdatedRequest(rs.getBytes(9));
                p.setStatus(rs.getString(8));
                p.setId(rs.getInt(9));
                Double uoc = rs.getDouble(10);
                if (rs.wasNull())
                    uoc = null;
                p.setUpdatedOilConsumption(uoc);

                Double ooc = rs.getDouble(11);
                if (rs.wasNull())
                    ooc = null;
                p.setOriginalOilConsumption(ooc);
                p.setScopeOFWork(rs.getString(12));

                p.setNotCompletedStatus(new ConEdStatus());
                p.getNotCompletedStatus().setStatus(getString(rs, 13));
                p.getNotCompletedStatus().setImgUrl(getString(rs, 14));
                p.getNotCompletedStatus().setImage(getBytes(rs, 15));

//                        if(p.getNotCompletedStatus() == null){
//                            p.setNotCompletedStatus(null);
//                        }

                p.setCompletedStatus(new ConEdStatus());
                p.getCompletedStatus().setStatus(getString(rs, 16));
                p.getCompletedStatus().setImgUrl(getString(rs, 17));
                p.getCompletedStatus().setImage(getBytes(rs, 18));

//                        if(p.getCompletedStatus() == null){
//                            p.setCompletedStatus(null);
//                        }


                c.getConEdProjects().add(p);
            }


        } finally {

            if (rs != null)
                rs.close();

            if (psmt2 != null)
                psmt2.close();


            if (psmt != null)
                psmt.close();

            if (conn != null)
                conn.close();
        }

        return new ArrayList<>(temp.values());
    }

    public static List<PlutoCustomer> getConEdProjectInfo(DataSource ds, List<PlutoCustomer> customers) throws SQLException {

        Connection conn = null;
        PreparedStatement psmt = null;

        try {

            conn = ds.getConnection();
            psmt = conn.prepareStatement("SELECT p.BBL, d.SERVICE_REQ, d.SERVICE_ADDRESS, d.REQUEST_TYPE, d.DATE_SUBMITTED, d.UTILITY_TYPE, d.CUSTOMER_REP, d.STATUS, d.ID, d.ORIG_OIL_CON, d.UPDATED_OIL_CON, d.SCOPE_OF_WORK, " +
                    " d.CURRENT_STATUS, d.CURRENT_STATUS_LINK, d.CURRENT_STATUS_IMG, d.PREV_STATUS, d.PREV_STATUS_LINK, d.PREV_STATUS_IMG, d.IS_ELECTRIC_HEAT \n" +
                    "            FROM dualfual.con_ed_project p, dualfual.con_ed_project_det d\n" +
                    "            where p.ID=d.PROJECT_ID and p.BBL = ?");
            for (PlutoCustomer c : customers) {
                ResultSet rs = null;
                try {
                    psmt.setString(1, c.getBbl());
                    rs = psmt.executeQuery();
                    while (rs.next()) {
                        if (c.getConEdProjects() == null) {
                            c.setConEdProjects(new ArrayList<>());
                        }
                        ConEdProject p = new ConEdProject(rs.getString(2), rs.getString(3), rs.getString(4));
                        p.setDateSubmitted(DateUtil.df_MMddyyyy.print(rs.getDate(5).getTime()));
                        p.setUtilityType(rs.getString(6));
                        p.setCustomerRep(rs.getString(7));
//                        p.setOriginalRequest(rs.getBytes(8));
//                        p.setUpdatedRequest(rs.getBytes(9));
                        p.setStatus(rs.getString(8));
                        p.setId(rs.getInt(9));
                        Double uoc = rs.getDouble(10);
                        if (rs.wasNull())
                            uoc = null;
                        p.setUpdatedOilConsumption(uoc);

                        Double ooc = rs.getDouble(11);
                        if (rs.wasNull())
                            ooc = null;
                        p.setOriginalOilConsumption(ooc);
                        p.setScopeOFWork(rs.getString(12));

                        p.setNotCompletedStatus(new ConEdStatus());
                        p.getNotCompletedStatus().setStatus(getString(rs, 13));
                        p.getNotCompletedStatus().setImgUrl(getString(rs, 14));
                        p.getNotCompletedStatus().setImage(getBytes(rs, 15));

//                        if(p.getNotCompletedStatus() == null){
//                            p.setNotCompletedStatus(null);
//                        }

                        p.setCompletedStatus(new ConEdStatus());
                        p.getCompletedStatus().setStatus(getString(rs, 16));
                        p.getCompletedStatus().setImgUrl(getString(rs, 17));
                        p.getCompletedStatus().setImage(getBytes(rs, 18));
                        p.setElectricHeat(getString(rs, 19));

//                        if(p.getCompletedStatus() == null){
//                            p.setCompletedStatus(null);
//                        }


                        c.getConEdProjects().add(p);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                } finally {
                    if (rs != null)
                        rs.close();
                }
            }
        } finally {
            if (psmt != null)
                psmt.close();

            if (conn != null)
                conn.close();
        }

        return customers.stream().filter(p -> p.getConEdProjects() != null).map(Function.<PlutoCustomer>identity()).collect(Collectors.toCollection(ArrayList::new));
    }

    public static void saveConEdProjectInfo(DataSource ds, PlutoCustomer customer, List<ConEdProject> projects) throws SQLException {

        Connection conn = null;
        PreparedStatement psmt1 = null;
        PreparedStatement psmt2 = null;
        PreparedStatement psmt3 = null;

        try {
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            psmt1 = conn.prepareStatement("DELETE FROM dualfual.con_ed_project WHERE BBL = ?");

            psmt1.setString(1, customer.getBbl());
            psmt1.execute();


            psmt2 = conn.prepareStatement("INSERT INTO dualfual.con_ed_project(BBL, LOCATION_ID) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            psmt2.setString(1, customer.getBbl());
            if (customer.getLocationId() == null)
                psmt2.setNull(2, Types.INTEGER);
            else
                psmt2.setInt(2, customer.getLocationId());

            psmt2.execute();

            ResultSet generatedKeys = psmt2.getGeneratedKeys();
            int id = -1;
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }
            generatedKeys.close();

            psmt3 = conn.prepareStatement("INSERT INTO dualfual.con_ed_project_det(SERVICE_REQ, SERVICE_ADDRESS, REQUEST_TYPE, DATE_SUBMITTED, " +
                    "UTILITY_TYPE, CUSTOMER_REP, ORIGINAL_REQ, UPDATED_REQ, PROJECT_ID, STATUS, ORIG_OIL_CON, UPDATED_OIL_CON, SCOPE_OF_WORK, " +
                    "CURRENT_STATUS, CURRENT_STATUS_LINK, CURRENT_STATUS_IMG, " +
                    "PREV_STATUS, PREV_STATUS_LINK, PREV_STATUS_IMG, IS_ELECTRIC_HEAT) \n" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                    "?, ?, ?," +
                    "?, ?, ?, ?)");

            if (projects != null) {
                for (ConEdProject project : projects) {

                    if (project.getDateSubmitted() == null) {
                        continue;
                    }
                    psmt3.setString(1, project.getServiceId());
                    psmt3.setString(2, project.getServiceAddress());
                    psmt3.setString(3, project.getRequestType());

                    psmt3.setDate(4, new Date(DateUtil.df_MMddyyyy.parseDateTime(project.getDateSubmitted()).toDate().getTime()));
                    psmt3.setString(5, project.getUtilityType());
                    psmt3.setString(6, project.getCustomerRep());
                    psmt3.setBytes(7, project.getOriginalRequest());
                    psmt3.setBytes(8, project.getUpdatedRequest());
                    psmt3.setInt(9, id);
                    psmt3.setString(10, project.getStatus());
                    if (project.getOriginalOilConsumption() == null)
                        psmt3.setNull(11, Types.DOUBLE);
                    else
                        psmt3.setDouble(11, project.getOriginalOilConsumption());

                    if (project.getUpdatedOilConsumption() == null)
                        psmt3.setNull(12, Types.DOUBLE);
                    else
                        psmt3.setDouble(12, project.getUpdatedOilConsumption());

                    if (project.getScopeOFWork() == null)
                        psmt3.setNull(13, Types.VARCHAR);
                    else
                        psmt3.setString(13, project.getScopeOFWork());

                    setString(psmt3, 14, project.getNotCompletedStatus().getStatus());
                    setString(psmt3, 15, project.getNotCompletedStatus().getImgUrl());
                    setBytes(psmt3, 16, project.getNotCompletedStatus().getImage());

                    setString(psmt3, 17, project.getCompletedStatus().getStatus());
                    setString(psmt3, 18, project.getCompletedStatus().getImgUrl());
                    setBytes(psmt3, 19, project.getCompletedStatus().getImage());

                    setString(psmt3, 20, project.getElectricHeat());


                    psmt3.execute();
                }
            }


            conn.commit();

        } catch (SQLException e) {
            if (conn != null)
                conn.rollback();

            throw e;
        } finally {

            if (psmt1 != null)
                psmt1.close();

            if (psmt2 != null)
                psmt2.close();

            if (psmt3 != null)
                psmt3.close();

            if (conn != null)
                conn.close();


        }

    }

    public static String getString(ResultSet rs, int idx) throws SQLException {
        String uoc = rs.getString(idx);
        if (rs.wasNull())
            uoc = null;

        return uoc;
    }

    public static byte[] getBytes(ResultSet rs, int idx) throws SQLException {
        byte[] uoc = rs.getBytes(idx);
        if (rs.wasNull())
            uoc = null;

        return uoc;
    }

    public static void setString(PreparedStatement psmt, int idx, String value) throws SQLException {
        if (value == null)
            psmt.setNull(idx, Types.VARCHAR);
        else
            psmt.setString(idx, value);
    }

    public static void setBytes(PreparedStatement psmt, int idx, byte[] value) throws SQLException {
        if (value == null)
            psmt.setNull(idx, Types.CLOB);
        else
            psmt.setBytes(idx, value);
    }
}
