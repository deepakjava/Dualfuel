package com.dualfual.remote;

import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;

public interface SeleniumRemote {

    public void login(String loginUrl, String username, String password);

    public void downloadAllFile(int date, String saveToPath) throws Exception;

    public void downloadFile(WebElement element, String buildingId, int date, String saveToPath) throws Exception;

    public List<WebElement> listElementsById(String id);

    public void logout();

}
