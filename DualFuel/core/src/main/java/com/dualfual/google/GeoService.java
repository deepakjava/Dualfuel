package com.dualfual.google;

import com.dualfual.xstream.XStreamUtils;
import com.thoughtworks.xstream.XStream;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;

public class GeoService {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.2; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0";
    public static void findLatitudeLongitude2(String address) throws IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String addressEnc = address.replaceAll(" ", "%20");
        //https://maps.googleapis.com/maps/api/js/GeocodeService.Search?4s53%20monroe%20turnpike%2006611&7sUS&9sen-US&callback=_xdc_._kqvln4&token=78477
        HttpGet httpGet = new HttpGet("https://maps.googleapis.com/maps/api/js/GeocodeService.Search?4s"+addressEnc+"&7sUS&9sen-US&callback=_xdc_._bbt4j1&token=25280");
        httpGet.addHeader("User-Agent", USER_AGENT);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        BufferedReader reader =null;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    httpResponse.getEntity().getContent()));

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }


            System.out.println(response);

        }finally {
            if(reader != null)
                reader.close();
        }

    }

    public static GeocodeResponse findLatitudeLongitude(String address) throws IOException {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        String addressEnc = URLEncoder.encode(address, "UTF-8");
//        HttpGet httpGet = new HttpGet("https://maps.googleapis.com/maps/api/geocode/xml?address=" + addressEnc + "&token=72988");
        HttpGet httpGet = new HttpGet("https://maps.googleapis.com/maps/api/geocode/xml?address=" + addressEnc + "&token=78477");
        httpGet.addHeader("User-Agent", USER_AGENT);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        BufferedReader reader =null;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    httpResponse.getEntity().getContent()));

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }


            XStream xStream = XStreamUtils.getXStream();
            GeocodeResponse geocodeResponse = (GeocodeResponse)xStream.fromXML(response.toString());
            return geocodeResponse;

        }finally {
            if(reader != null)
            reader.close();
        }

    }


    public static void main(String[] args) throws IOException {

        findLatitudeLongitude("5902 Decatur St Queens, NY 11385");
    }

}
