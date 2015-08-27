package com.spring;

import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.appmanager.CachedObjectFactory;
import com.dto.ConEdProjectDAO;
import com.dto.LocationDAO;
import com.dto.PlutoCustomer;
import com.dto.PlutoDAO;
import com.dualfual.common.DateUtil;
import com.dualfual.remote.coned.ConEdProject;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;


@Controller
public class SpringMVCController {

    private static final Logger logger = Logger.getLogger(SpringMVCController.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DataSource dataSourceSqlServer;

    @RequestMapping(value = "/angularServiceCall")
    public String angularServiceCall() {

        logger.info("Log4j info is working");
        logger.warn("Log4j warn is working");
        logger.debug("Log4j debug is working");
        logger.error("Log4j error is working");
        System.out.println("System out is working");
        return "angularJSngGrid";
    }

    @RequestMapping(value = "/getServiceAreaData")
    public
    @ResponseBody
    Map<String, String> getServiceAreaData() {

        Map<String, String> dropDownData = new HashMap<String, String>();
        dropDownData.put("BX", "Bronx");
        dropDownData.put("BK", "Brooklyn");
        dropDownData.put("MN", "Manhattan");
        dropDownData.put("QN", "Queens");
        dropDownData.put("SI", "Staten Island");
        dropDownData.put("WESTCHESTER", "Westchester");
        //dropDownData.put("Westchester", "Westchester");

        return dropDownData;
    }

    @RequestMapping(value = "/getActiveTeamData/{name}/{days}")
    @ResponseBody
    public List<Map<String, Object>> getActiveTeamData(@PathVariable String name, @PathVariable String days) {

        List<Map<String, Object>> activeTeamMap = new ArrayList<Map<String, Object>>();

        try {
            List<PlutoCustomer> data = CachedObjectFactory.plutoCustomerRef.get();

            int tillDate = DateUtil.addDays(DateTime.now(), -7);
            if (days.equalsIgnoreCase("7d")) {
                tillDate = DateUtil.addDays(DateTime.now(), -7);
            } else if (days.equalsIgnoreCase("30d")) {
                tillDate = DateUtil.addMonth(DateTime.now(), -1);
            } else if (days.equalsIgnoreCase("90d")) {
                tillDate = DateUtil.addMonth(DateTime.now(), -3);
            } else if (days.equalsIgnoreCase("1y")) {
                tillDate = DateUtil.addMonth(DateTime.now(), -12);
            }


            List<PlutoCustomer> conedCustomer = data.stream().filter(p -> p.getConEdProjects() != null).map(Function.<PlutoCustomer>identity()).collect(Collectors.toCollection(ArrayList::new));
            if (name != null && !name.trim().isEmpty() && !name.equalsIgnoreCase("All")) {
                conedCustomer = conedCustomer.stream().filter(p -> p.getBorough().toUpperCase().contains(name.toUpperCase())).map(Function.<PlutoCustomer>identity()).collect(Collectors.toCollection(ArrayList::new));
            }


            for (PlutoCustomer c : conedCustomer) {
                for (ConEdProject p : c.getConEdProjects()) {

                    Date dtSubmiited = DateUtil.df_MMddyyyy.parseDateTime(p.getDateSubmitted()).toDate();

                    if (!days.equalsIgnoreCase("All")) {
                        if (DateUtil.getIntDate(dtSubmiited) < tillDate) {
                            continue;
                        }
                    }

                    Map<String, Object> dropDownData = new LinkedHashMap<>();
                    dropDownData.put("BBL", c.getBbl());
                    dropDownData.put("Address", p.getServiceAddress());
                    dropDownData.put("DateSubmitted", dtSubmiited.getTime());
                    dropDownData.put("ServiceId", p.getServiceId() + "");
                    dropDownData.put("RequestType", p.getRequestType());

                    dropDownData.put("Status", (p.getStatus()==null)?"":p.getStatus());
                    dropDownData.put("ScopeOfWork", p.getScopeOFWork());
                    dropDownData.put("OriginalOilConsumption", (p.getOriginalOilConsumption() == null) ? "" : p.getOriginalOilConsumption());
                    dropDownData.put("UpdatedOilConsumption", (p.getUpdatedOilConsumption() == null) ? "" : p.getUpdatedOilConsumption());
                    dropDownData.put("isElectricHeat", (p.getElectricHeat() == null) ? "" : p.getElectricHeat());

                    String currentStatus = p.getNotCompletedStatus().getStatus();
                    String img = null;
//                    String img = p.getNotCompletedStatus().getImageBase64();
//                    if(currentStatus == null) {
//                        currentStatus = p.getCompletedStatus().getStatus();
//                        img = p.getCompletedStatus().getImageBase64();
//                    }
                   // img = (currentStatus == null) ? p.getCompletedStatus().getImage() : img;

                    String base64Img = (currentStatus == null || img == null) ? "R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7" : img;
                    //base64Img = (base64Img == null) ? "" : "<img src=\"data:image/png;base64," + base64Img + "\" alt=\"" + currentStatus + "\">";
                    dropDownData.put("ServiceStatusImg", base64Img);
                    dropDownData.put("ServiceStatus", (currentStatus==null)?"":currentStatus);

                    dropDownData.put("ID", p.getId() + "");
                    dropDownData.put("Map", c.getLatitude() != null ? "http://www.latlong.net/c/?lat=" + c.getLatitude() + "&long=" + c.getLongitude() : "https://www.google.com/maps/place/" + c.getAddress() + " " + c.getZip());

                    activeTeamMap.add(dropDownData);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return activeTeamMap;
    }


    @RequestMapping(value = "/getpdf/{type}/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getPDF(@PathVariable String type, @PathVariable int id) {

        byte[] contents = new byte[0];
        try {
            if (type.equalsIgnoreCase("original"))
                contents = ConEdProjectDAO.getOriginalRequest(dataSource, id);
            else
                contents = ConEdProjectDAO.getUpdatedRequest(dataSource, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
        return response;
    }


}