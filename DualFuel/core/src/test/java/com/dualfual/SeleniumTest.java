package com.dualfual;


import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SeleniumTest {

    public static void main(String[] args) throws IOException, InterruptedException {

        // Selenium selenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://www.google.com");

        long timestamp = System.currentTimeMillis();
        File f = new File("C:\\Temp\\" + timestamp);
        f.mkdir();

        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream;application/csv;text/csv;application/vnd.ms-excel;");
        profile.setPreference("browser.helperApps.alwaysAsk.force", false);
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.dir", "C:\\Temp\\" + timestamp);


        WebDriver driver = new FirefoxDriver(profile);
//        WebDriver driver = new FirefoxDriver();
        //selenium.start();
        driver.get("http://virtualremote500.com/(S(qv3ppfqp3eg4kwfbvd5awkqq))/EntechLogin.aspx?ReturnUrl=%2f");

        driver.findElement(By.name("Login1$UserName")).sendKeys("dualfuelbarberryrose");
        driver.findElement(By.name("Login1$Password")).sendKeys("dualfuel1");
        driver.findElement(By.name("Login1$LoginButton")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("chart")));

        String myWindowHandle = driver.getWindowHandle();

//        if (driver instanceof JavascriptExecutor) {
//            ((JavascriptExecutor)driver).executeScript("onIconClick('chart', '3794');");
//        }

//        Set<String> windowId = driver.getWindowHandles();

        //  String myWindowHandle2 = driver.getWindowHandle();


        List<WebElement> elements = driver.findElements(By.id("chart"));

        //driver.findElement(By.tagName("img")).click();

        elements.get(0).click();

        Thread.sleep(10000);


        Set<String> windowId = driver.getWindowHandles();    // get  window id of current window
        Iterator<String> itererator = windowId.iterator();
        String mainWindow = itererator.next();
        String popWindow = itererator.next();

        driver.switchTo().window(popWindow);

        driver.findElement(By.id("datePicker_I")).clear();
        driver.findElement(By.id("datePicker_I")).sendKeys("6/17/2015");
        WebElement NextButton = driver.findElement(By.xpath("//img[@id='spanBox_B-1Img']"));
        NextButton.click();
        Thread.sleep(1000);
//        WebElement sixtyButton = driver.findElement(By.xpath("//td[@id='spanBox_DDD_L_LBI2T0']"));
        WebElement sixtyButton = driver.findElement(By.xpath("//td[@id='spanBox_DDD_L_LBI0T0']"));
        sixtyButton.click();

//       driver.findElement(By.id("spanBox_I")).sendKeys("Frequency: 30 minutes");
        driver.findElement(By.id("goButton")).click();
//        WebElement NextButton = driver.findElement(By.xpath("//div[@id='goButton']"));
//        NextButton.click();

//        WebElement NextButton = ApplicationManager.getWebDriver().findElement(By.xpath("//div[contains(@class,'slider-set') and not(contains(@class,'noDisplay'))]//div[@class='slider-nav next']"));
//        NextButton.click();

        Cookie cookie = driver.manage().getCookieNamed(".ASPXAUTH");
        String cookieVal = cookie.getValue();


        String url = driver.getCurrentUrl();

    //    ApacheHttpClientExample.sendPOST4(cookieVal, "6/17/2015", url);




        driver.findElement(By.id("xslButton")).click();
        Thread.sleep(5000);

        String fileName = "BennettAvenue_" + "20150617" + "_" + System.currentTimeMillis() + ".xls";

        File destDir = new File("C:\\Temp\\" + fileName);
        File srcFile = f.listFiles()[0];
        FileUtils.copyFile(srcFile, destDir);

        f.deleteOnExit();
        f.delete();


       // Thread.sleep(5000);

      //  WebElement fileInput = driver.findElement(By.name("uploadfile"));

        //driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);

        driver.switchTo().window(popWindow).close();

        driver.quit();

        FileUtils.deleteQuietly(f);
       // Thread.sleep(5000);

     //   driver.switchTo().window(popWindow).close();









   //     driver.close();
        //login_form = driver.find_element_by_id('loginForm')
        //selenium.click();
    }
}
