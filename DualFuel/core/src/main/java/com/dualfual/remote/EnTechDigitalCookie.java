package com.dualfual.remote;


import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.util.Date;

public class EnTechDigitalCookie extends BasicClientCookie{

    public EnTechDigitalCookie(String value) {
        super(".ASPXAUTH", value);
        setAttribute("path", "/");
        setAttribute("httponly", null);
    }

    @Override
    public String getDomain() {
        return "virtualremote500.com";
    }

    @Override
    public String getPath() {
        return "/";
    }



}
