package com.dualfual.remote;


import com.dualfual.remote.coned.ConEdAddress;
import com.dualfual.remote.coned.ConEdProject;
import com.dualfual.remote.coned.ConEdStatus;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class ConEdRemote implements SeleniumRemote {

    private WebDriver driver = null;
    private String mainUrl;

    public ConEdRemote() throws FileNotFoundException {
        driver = new FirefoxDriver();
    }

    @Override
    public void login(String loginUrl, String username, String password) {
//ctl00_MainContent_TxtUsername
        //ctl00_MainContent_TxtPassword

        driver.get(loginUrl);
        driver.findElement(By.id("ctl00_MainContent_TxtUsername")).sendKeys(username);
        driver.findElement(By.id("ctl00_MainContent_TxtPassword")).sendKeys(password);
        driver.findElement(By.id("ctl00_MainContent_BtnLogin")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Search All Projects')]")));
        mainUrl = driver.getCurrentUrl();
        System.out.println("Login Successful");
    }

    public void fillProjectInfo(ConEdProject project) throws Exception {

        try {
            driver.findElement(By.xpath("//*[contains(text(), '" + project.getServiceId() + "')]")).click();
            driver.switchTo().defaultContent();
            driver.switchTo().frame("radwindow2");

            try {
                WebDriverWait wait = new WebDriverWait(driver, 20);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Updated Request')]")));
            } catch (Exception e) {
                // Ignore
            }


            String pageText = driver.getPageSource();
            Parser parser = new Parser(pageText);
            NodeList list = parser.parse(null);
            NodeList tds = list.extractAllNodesThatMatch(new TagNameFilter("td"), true);

            boolean isUpdatedRequest = false;
            String img = null;
            String status = null;

            ConEdStatus cStatus = new ConEdStatus();
            ConEdStatus nStatus = new ConEdStatus();

            for (int j = 0; j < tds.size(); j++) {
                TableColumn td = (TableColumn) tds.elementAt(j);

                if (checkValue(td, "Date submitted:")) {
                    System.out.println(getValue(td));
                    project.setDateSubmitted(getValue(td).split(":")[1].replaceAll("\n", "").replaceAll("\\.", "").trim());
                }

                if (checkValue(td, "Request Type:")) {
                    j++;
                    j++;
                    td = (TableColumn) tds.elementAt(j);
//                System.out.println(getValue(td));
                }

                if (checkValue(td, "Utility Type:")) {
                    j++;
                    td = (TableColumn) tds.elementAt(j);
                    project.setUtilityType(getValue(td));
                    // System.out.println(getValue(td));
                }

                if (checkValue(td, "Customer Rep:")) {
                    j++;
                    td = (TableColumn) tds.elementAt(j);
//                System.out.println(getValue(td));
                    project.setCustomerRep(getValue(td));
                }

                if (checkValue(td, "Status:")) {
                    j++;
                    td = (TableColumn) tds.elementAt(j);
//                System.out.println(getValue(td));
                    project.setStatus(getValue(td));
                }

                if (checkValue(td, "Updated Request")) {
                    isUpdatedRequest = true;
                }

                //specify the scope of work

                if (checkValue(td, "Annual Oil Consumption")) {
                    j++;
                    td = (TableColumn) tds.elementAt(j);
//                System.out.println(getValue(td));
                    String[] value = getValue(td).trim().split(" ");
                    if (value.length == 2) {
                        project.setOilConsumptionUnit(value[1]);
                        if (isUpdatedRequest)
                            project.setUpdatedOilConsumption(Double.parseDouble(value[0].trim()));
                        else
                            project.setOriginalOilConsumption(Double.parseDouble(value[0].trim()));
                    }
                }

                if (checkValue(td, "Building have Electric Heat")) {
                    j++;
                    td = (TableColumn) tds.elementAt(j);
                    project.setElectricHeat(getValue(td).trim());
                }

                if (checkValue(td, "specify the scope of work")) {
                    j++;
                    td = (TableColumn) tds.elementAt(j);
                    project.setScopeOFWork(getValue(td).trim());
                }

                if (checkValue(td, "Request for Service")) {
                    String table = td.toHtml();

                    int idxImg = table.indexOf("src=\"");
                    int idxSpan = table.indexOf("span class=\"");
                    if (idxImg != -1 && idxSpan != -1) {
                        img = table.substring(idxImg + 5, table.indexOf("\"", idxImg + 6));
                        status = table.substring(idxSpan + 12, table.indexOf("\"", idxSpan + 13));
                        setStatus(cStatus, nStatus, status, img, td.toPlainTextString().toUpperCase().trim());

                    }
                }

                if (checkValue(td, "Service Determination")) {
                    String table = td.toHtml();

                    int idxImg = table.indexOf("src=\"");
                    int idxSpan = table.indexOf("span class=\"");
                    if (idxImg != -1 && idxSpan != -1) {
                        img = table.substring(idxImg + 5, table.indexOf("\"", idxImg + 6));
                        status = table.substring(idxSpan + 12, table.indexOf("\"", idxSpan + 13));
                        setStatus(cStatus, nStatus, status, img, td.toPlainTextString().toUpperCase().trim());

                    }
                }

                if (checkValue(td, "Design")) {
                    String table = td.toHtml();

                    int idxImg = table.indexOf("src=\"");
                    int idxSpan = table.indexOf("span class=\"");
                    if (idxImg != -1 && idxSpan != -1) {
                        img = table.substring(idxImg + 5, table.indexOf("\"", idxImg + 6));
                        status = table.substring(idxSpan + 12, table.indexOf("\"", idxSpan + 13));
                        setStatus(cStatus, nStatus, status, img, td.toPlainTextString().toUpperCase().trim());
                    }
                }

                if (checkValue(td, "Construction")) {
                    String table = td.toHtml();

                    int idxImg = table.indexOf("src=\"");
                    int idxSpan = table.indexOf("span class=\"");
                    if (idxImg != -1 && idxSpan != -1) {
                        img = table.substring(idxImg + 5, table.indexOf("\"", idxImg + 6));
                        status = table.substring(idxSpan + 12, table.indexOf("\"", idxSpan + 13));
                        setStatus(cStatus, nStatus, status, img, td.toPlainTextString().toUpperCase().trim());
                    }
                }

                if (checkValue(td, "Service Complete")) {
                    String table = td.toHtml();

                    int idxImg = table.indexOf("src=\"");
                    int idxSpan = table.indexOf("span class=\"");
                    if (idxImg != -1 && idxSpan != -1) {
                        img = table.substring(idxImg + 5, table.indexOf("\"", idxImg + 6));
                        status = table.substring(idxSpan + 12, table.indexOf("\"", idxSpan + 13));
                        setStatus(cStatus, nStatus, status, img, td.toPlainTextString().toUpperCase().trim());
                    }
                }

            }

            Map<String, String> parameter = getUrlParameter(driver.getCurrentUrl());
            Set<Cookie> allCookies = driver.manage().getCookies();
            List<BasicClientCookie> cookies = new ArrayList<BasicClientCookie>();

            for (Cookie c : allCookies) {
                BasicClientCookie cookie = new BasicClientCookie(c.getName(), c.getValue());
                cookie.setDomain(c.getDomain());
                cookie.setCreationDate(c.getExpiry());
                cookie.setPath(c.getPath());
                cookie.setSecure(c.isSecure());

                cookies.add(cookie);
            }

            String pdf1 = "https://apps.coned.com/ESWEB/ViewCaseDetailsPDF.aspx?BoroughID=" + parameter.get("BoroughID") + "&CaseNumber=" + parameter.get("CaseNumber") + "&ViewType=";
            project.setOriginalRequest(ApacheHttpClientExample.printBytes(cookies, pdf1 + 1));
            project.setUpdatedRequest(ApacheHttpClientExample.printBytes(cookies, pdf1 + 2));

            if (cStatus.getImgUrl() != null) {
                byte[] temp = ApacheHttpClientExample.printBytes(cookies, "https://apps.coned.com/ESWEB/" + cStatus.getImgUrl());
//                FileOutputStream out = new FileOutputStream("C:\\Temp\\img.gif");
//                out.write(temp);
                cStatus.setImage(temp);
            }

            if (nStatus.getImgUrl() != null) {
                byte[] temp = ApacheHttpClientExample.printBytes(cookies, "https://apps.coned.com/ESWEB/" + nStatus.getImgUrl());
                nStatus.setImage(temp);
            }

            project.setCompletedStatus(cStatus);
            project.setNotCompletedStatus(nStatus);
        }finally {

            driver.switchTo().defaultContent();
            driver.findElement(By.id("CloseButtonctl00_MainContent_Singleton_radwindow2")).click();
            //driver.findElement(By.xpath("//*[contains(text(), '" + project.getServiceId() + "')]")).click();
            //CloseButtonctl00_MainContent_Singleton_radwindow2
            //driver.switchTo().defaultContent();
        }

        //

    }

    private void setStatus(ConEdStatus cStatus, ConEdStatus nStatus, String status, String url, String value) {

        int i = 0;
        if (value.toUpperCase().contains("Request for Service".toUpperCase())) {
            i++;
        }

        if (value.toUpperCase().contains("Service Determination".toUpperCase())) {
            i++;
        }

        if (value.toUpperCase().contains("Design".toUpperCase())) {
            i++;
        }

        if (value.toUpperCase().contains("Construction".toUpperCase())) {
            i++;
        }

        if (value.toUpperCase().contains("Service Complete".toUpperCase())) {
            i++;
        }

        if(i > 1){
            return;
        }

        if (status.equalsIgnoreCase("statusIconNA")) {
            if (nStatus.getImgUrl() == null) {
                nStatus.setImgUrl(url);
                nStatus.setStatus(value);
            }
        } else {
            cStatus.setImgUrl(url);
            cStatus.setStatus(value);
        }

    }

    public List<ConEdProject> performSearch(ConEdAddress address) throws Exception {

        driver.get(mainUrl);

        WebDriverWait wait = new WebDriverWait(driver, 25);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Search All Projects')]")));

        driver.findElement(By.xpath("//*[contains(text(), 'Search All Projects')]")).click();
        //ctl00_MainContent_OptSearchByServiceAddress
        //

        driver.findElement(By.id("ctl00_MainContent_OptSearchByServiceAddress")).click();

        Select select = new Select(driver.findElement(By.id("ctl00_MainContent_DdlServiceArea")));
//        select.selectByVisibleText("Bronx");
        select.selectByVisibleText(address.getArea());


        driver.findElement(By.id("ctl00_MainContent_TxtBuildNum")).sendKeys(address.getStreetNumber());
        driver.findElement(By.id("ctl00_MainContent_TxtSearchZip")).sendKeys(address.getZip());

        try {
            driver.findElement(By.id("ctl00_MainContent_RadStreetNameList_Input")).sendKeys(address.getStreet());
            Thread.sleep(5000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_MainContent_RadStreetNameList_c0")));
            driver.findElement(By.id("ctl00_MainContent_RadStreetNameList_c0")).click();
        }catch (Exception e){
            // Ignore
            // click it anyway
        }


        driver.findElement(By.id("ctl00_MainContent_BtnSearchByAddress")).click();

        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='search-notfound-msg']")));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_MainContent_GrdSearchResults_ctl01")));
        } catch (Exception e) {
//            WebElement nodata = driver.findElement(By.xpath("//*[@class='search-notfound-msg']"));
//            if(nodata != null){
            return null;
//            }
//            throw  e;
        }

        WebElement table_element = driver.findElement(By.id("ctl00_MainContent_GrdSearchResults_ctl01"));
        List<WebElement> tr_collection = table_element.findElements(By.xpath("id('ctl00_MainContent_GrdSearchResults_ctl01')/tbody/tr"));

//        List<ConEdProject> projects = new ArrayList<ConEdProject>();
        Map<String, ConEdProject> projects = new LinkedHashMap<String, ConEdProject>();

        for (WebElement trElement : tr_collection) {
            List<WebElement> td_collection = trElement.findElements(By.xpath("td"));

            //  String link = driver.findElement(By.xpath("//*[contains(text(), 'MC-89751')]")).getAttribute("onclick");
            ConEdProject project = new ConEdProject(td_collection.get(0).getText(),
                    td_collection.get(1).getText(),
                    td_collection.get(2).getText());

            projects.put(project.getServiceId(), project);
        }

        for (ConEdProject project : projects.values()) {
            try {
                fillProjectInfo(project);
            } catch (Exception e) {
                //  e.printStackTrace();
            }
        }

        return new ArrayList<ConEdProject>(projects.values());
    }

    private String getValue(TableColumn td) {
        return td.toPlainTextString().trim();
    }

    private boolean checkValue(TableColumn td, String key) {
        return td.toPlainTextString().toUpperCase().trim().contains(key.toUpperCase());
    }

    @Override
    public void downloadAllFile(int date, String saveToPath) throws Exception {

    }

    @Override
    public void downloadFile(WebElement element, String buildingId, int date, String saveToPath) throws Exception {

    }

    @Override
    public List<WebElement> listElementsById(String id) {
        return null;
    }

    @Override
    public void logout() {
        try {
            driver.findElement(By.xpath("//*[contains(text(), 'Logout')]")).click();
        } catch (Exception e) {

        }

        driver.quit();
    }

    private static Map<String, String> getUrlParameter(String url) {
        String[] tokens = url.substring(url.indexOf("?") + 1).split("&");
        Map<String, String> params = new HashMap<String, String>();
        for (String token : tokens) {
            String[] t = token.split("=");
            params.put(t[0].trim(), t[1].trim());
        }

        return params;
    }

    public static void main(String[] args) throws Exception {
        ConEdRemote remote = new ConEdRemote();
        ConEdAddress add = new ConEdAddress("1750", "DAVIDSON AVE", "10453", "Bronx");
        remote.login("https://apps.coned.com/ESWEB/Login.aspx", "jbohm@dualfuelcorp.com", "nissan22");
        remote.performSearch(add);


    }
}
