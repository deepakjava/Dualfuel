package com.dualfual.remote;


import com.dualfual.common.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.joda.time.DateTime;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class EntechRemote implements SeleniumRemote {

    private WebDriver driver = null;
    private File f = null;

    public EntechRemote(String tempDirPath) throws FileNotFoundException {

        long timestamp = System.currentTimeMillis();
        File tempDir = new File(tempDirPath);

        if (!tempDir.exists()) {
            throw new FileNotFoundException(tempDirPath + " --> path does not exist");
        }

        f = new File(tempDir, timestamp + "");
        if (f.exists()) {
            throw new RuntimeException(f.getAbsolutePath() + " --> Path already exist, don't worry I'll try again in few sec.");
        }
        f.mkdir();

        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream;application/csv;text/csv;application/vnd.ms-excel;");
        profile.setPreference("browser.helperApps.alwaysAsk.force", false);
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("browser.download.folderList", 2);
        String tempPath = f.getAbsolutePath();
        profile.setPreference("browser.download.dir", tempPath);

        driver = new FirefoxDriver(profile);
    }

    @Override
    public void login(String loginUrl, String username, String password) {
        driver.get(loginUrl);
        driver.findElement(By.name("Login1$UserName")).sendKeys(username);
        driver.findElement(By.name("Login1$Password")).sendKeys(password);
        driver.findElement(By.name("Login1$LoginButton")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("chart")));
    }

    @Override
    public void downloadFile(WebElement element, String buildingId, int date, String saveToPath) throws Exception {
        int retry = 3;
        for (int i = 1; i <= retry; i++) {
            try {
                if(i > 1){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // Ignore
                    }
                }
                downloadFileInternal(element, buildingId, date, saveToPath);
                break;
            } catch (Exception e) {
                if (i == retry) {
                    throw e;
                }
            }
        }
    }

    @Override
    public void downloadAllFile(int date, String saveToPath) throws Exception {
        List<WebElement> elements = listElementsById("");
        date = DateUtil.addDays(date, 0);
        for (WebElement e : elements) {
            String id = e.getAttribute("class");
            downloadFile(e, id, date, saveToPath);
        }
    }

    public void downloadFileInternal(WebElement element, String buildingId, int date, String saveToPath) throws InterruptedException, IOException {

        String mainWindow = null;
        String popWindow = null;

        try {
            FileUtils.cleanDirectory(f);

            element.click();
            Thread.sleep(12000);

            Set<String> windowId = driver.getWindowHandles();    // get  window id of current window
            Iterator<String> itererator = windowId.iterator();
            mainWindow = itererator.next();
            popWindow = itererator.next();

            driver.switchTo().window(popWindow);

            String dateStr = DateUtil.print_M_D_YYYY(date);

            driver.findElement(By.id("datePicker_I")).clear();
            driver.findElement(By.id("datePicker_I")).sendKeys(dateStr);

            WebElement NextButton = driver.findElement(By.xpath("//img[@id='spanBox_B-1Img']"));
            NextButton.click();
            Thread.sleep(1000);
            WebElement sixtyButton = driver.findElement(By.xpath("//td[@id='spanBox_DDD_L_LBI0T0']"));
            sixtyButton.click();

            driver.findElement(By.id("goButton")).click();

            Thread.sleep(15000);

            driver.findElement(By.id("xslButton")).click();
            Thread.sleep(10000);

            if (f.listFiles().length != 1) {
                new RuntimeException("!!!! Could not able to download file !!!!");
            }

            String fileName = buildingId + "_" + date + ".xls";
            File destDir = new File(saveToPath + fileName);
            File srcFile = f.listFiles()[0];

            validateDownload(srcFile);
            FileUtils.copyFile(srcFile, destDir);

        } finally {
            if (popWindow != null)
                driver.switchTo().window(popWindow).close();

            if (mainWindow != null)
                driver.switchTo().window(mainWindow);

            FileUtils.cleanDirectory(f);
        }
    }

    public void validateDownload(File file) throws IOException {

        BufferedInputStream stream = null;
        try {
            int lineNum = 0;
            stream = new BufferedInputStream(new FileInputStream(file));
            HSSFWorkbook workbook = new HSSFWorkbook(stream);
            HSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows from first sheet
            Iterator<Row> rowIterator = sheet.iterator();
            String stringAt2 = null;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                lineNum++;
                Iterator<Cell> cellIterator = row.cellIterator();
                Cell cell = cellIterator.next();
                if (lineNum == 3) {
                    stringAt2 = cell.getStringCellValue().trim();
                    break;
                }
            }

            if(lineNum < 2){
                throw new RuntimeException("File is empty");
            }

            if(lineNum == 3){
                if(!stringAt2.equalsIgnoreCase("12:10 AM")){
                    throw new RuntimeException("File format is wrong, expected 10 min.");
                }
            }
        } finally {
            if (stream != null)
                stream.close();
        }


    }

    @Override
    public List<WebElement> listElementsById(String id) {
        return driver.findElements(By.id("chart"));
    }

    @Override
    public void logout() {

        try {
            WebElement logout = null;

            try {
                logout = driver.findElement(By.xpath("//*[contains(text(), 'Log Out')]"));
            } catch (Exception e) {
            }

            if (logout != null) {
                logout.click();
                Alert prompt = driver.switchTo().alert();
                prompt.accept();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    // Ignore
                }
            }
        } finally {
            driver.quit();
            try {
                FileUtils.deleteDirectory(f);
            } catch (IOException e) {
            }
        }
    }


}
