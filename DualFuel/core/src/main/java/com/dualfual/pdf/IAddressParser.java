package com.dualfual.pdf;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public interface IAddressParser {

    public String getUrl();
    public void process(Map<String, Address> address, String line, String lastLine, BufferedReader st) throws IOException;
}
