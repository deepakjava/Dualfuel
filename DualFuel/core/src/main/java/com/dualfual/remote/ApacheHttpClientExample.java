package com.dualfual.remote;


import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ApacheHttpClientExample {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.2; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0";

    //    private static final String GET_URL = "http://virtualremote500.com/(S(qv3ppfqp3eg4kwfbvd5awkqq))/EntechLogin.aspx?ReturnUrl=%2f";
    private static final String GET_URL = "http://virtualremote500.com/(S(woucykuvcof50o2izbd3uuee))/entechlogin.aspx?Login1%24LoginButton=Login&Login1%24Password=dualfuel1&Login1%24UserName=dualfuelbarberryrose&__EVENTARGUMENT=&__EVENTTARGET=&__EVENTVALIDATION=%2FwEdAARLwhm1ulwom1ezM5Hj0jzW8x5TPe4Fb2SCxWQFXQqD6Fz4Ff%2FmRdr9eJovHJ26GXDQt9u8Yj9aTUScKk9HMLRqtb62lmULR7tos5TeXzvt9i202sITPCZjKQOsjzhFxsY%3D&__LASTFOCUS=&__VIEWSTATE=%2FwEPDwUKLTE1MTgzMzc3MGQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgEFF0xvZ2luMSRMb2dpbkltYWdlQnV0dG9ueDH4R0TdBJm9oijQ9O1vLBRYuKWjYhF0iYhtP7WJrf0%3D&__VIEWSTATEGENERATOR=B9CA3B8C";

    private static final String POST_URL = "http://localhost:9090/SpringMVCExample/home";

    public static void main2(String[] args) throws IOException {
        List<Cookie> cookies = sendGET();
        System.out.println("GET DONE");
        sendPOST2(cookies);
        sendPOST3(cookies, "jlp4ouo5wrmc4pouboscbjpe");
        // System.out.println("POST DONE");
        sendGET2(cookies);
        sendPOST3(cookies);
    }

    private static List<Cookie> sendGET() throws IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        CookieStore cookieStore = httpClient.getCookieStore();
        //cookieStore.addCookie();

        HttpGet httpGet = new HttpGet(GET_URL);
        httpGet.addHeader("User-Agent", USER_AGENT);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

        System.out.println("GET Response Status:: "
                + httpResponse.getStatusLine().getStatusCode());

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpResponse.getEntity().getContent()));

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();

        // print result
        System.out.println(response.toString());
        HttpParams param = httpClient.getParams();
        List<Cookie> cookies = httpClient.getCookieStore().getCookies();
        System.out.println(cookies);
        return cookies;
        // httpClient.close();
    }

    private static void sendGET2(List<Cookie> cookies) throws IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        CookieStore cookieStore = httpClient.getCookieStore();
        for (Cookie c : cookies) {
            cookieStore.addCookie(c);
        }

        HttpGet httpGet = new HttpGet("http://virtualremote500.com/%28S%28mwreegpmjnczojsz0kpjnsb3%29%29/Chart.aspx");
        httpGet.addHeader("User-Agent", USER_AGENT);
        httpGet.addHeader("Referer", "http://virtualremote500.com/(S(54yw4uyt332fd0hpumutiisr))/Snapshot.aspx");
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

        System.out.println("GET Response Status:: "
                + httpResponse.getStatusLine().getStatusCode());

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpResponse.getEntity().getContent()));

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();

        // print result
        System.out.println(response.toString());

        // httpClient.close();
    }


    private static void sendPOST() throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(POST_URL);
        httpPost.addHeader("User-Agent", USER_AGENT);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("userName", "Pankaj Kumar"));

        HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);
        httpPost.setEntity(postParams);

        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

        System.out.println("POST Response Status:: "
                + httpResponse.getStatusLine().getStatusCode());

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpResponse.getEntity().getContent()));

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();

        // print result
        System.out.println(response.toString());
        httpClient.close();

    }

    private static void sendPOST2(List<Cookie> cookies) throws IOException {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        CookieStore cookieStore = httpClient.getCookieStore();
        for (Cookie c : cookies) {
            cookieStore.addCookie(c);
        }

        //http://virtualremote500.com/(S(3jkhzwc2pqxkwv0203yptaxl))/Snapshot.aspx

        HttpPost httpPost = new HttpPost("http://virtualremote500.com/(S(54yw4uyt332fd0hpumutiisr))/Snapshot.aspx");
        httpPost.addHeader("User-Agent", USER_AGENT);
        httpPost.addHeader("Referer", "http://virtualremote500.com/(S(54yw4uyt332fd0hpumutiisr))/Snapshot.aspx");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("ASPxButtonEdit1", ""));
        urlParameters.add(new BasicNameValuePair("DXCss", "0_1755,1_16,0_1759,1_8,1_6,0_1611,0_1613,0_1615,0_1617,1_14,1_1,1_7,Images/EnTechIcon.ico,DevExpress\n" +
                "/fixes.css"));
        urlParameters.add(new BasicNameValuePair("DXScript", "1_187,1_101,1_180,1_98,1_172,1_178,1_163,1_130,1_137,1_120,1_154,1_99,1_136,1_171,1_170"));
        urlParameters.add(new BasicNameValuePair("__CALLBACKID", "callback"));
//        urlParameters.add(new BasicNameValuePair("__CALLBACKPARAM", "c0:3793"));
        urlParameters.add(new BasicNameValuePair("__CALLBACKPARAM", "c0:3793"));
        urlParameters.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
        urlParameters.add(new BasicNameValuePair("__EVENTTARGET", ""));
        urlParameters.add(new BasicNameValuePair("__EVENTVALIDATION", "/wEdAA36QKZj1Pdyizlkg3Et9VVKdPvVnubBhRZQJTCAm7kvR9bSjAUQSpSB4eDcOSwPf4xFRSpuKQP8/mP6qofc5c2VJDp79r8OQeJmlS\n" +
                "/VjstwWpDo41b8u7kvB9q3GLtEhc2rlyAqOB1W0S00/1EO31v1avMJv06wJ07rlS1iaRumiovasdc8d3bq9zTE5fyqS+6l31wEFrJt8g13XLxjXGjEK2EQcPT7da0rOybfYM0oP1tLvXANRHBT87z7c8r5iQu25KBPIJRKbLMUXlWjdOWEfl4fQ8hkNt\n" +
                "+YU6bW88gHCiHpHoHbf6X5GrYa9YAJCHY="));

        urlParameters.add(new BasicNameValuePair("__VIEWSTATE", "/wEPDwUKLTk4NTY3MDA5Mg9kFgICAw9kFhACAQ88KwAJAQAPFgIeDl8hVXNlVmlld1N0YXRlZ2RkAgMPPCsABAEADxYCHgVWYWx1\n" +
                "ZWVkZAIHDzwrABwDAA8WAh4PRGF0YVNvdXJjZUJvdW5kZ2QGD2QQFgECARYBPCsADAEAFgIeCVNvcnRJbmRleGYPFgECARYBBX9E\n" +
                "ZXZFeHByZXNzLldlYi5HcmlkVmlld0RhdGFUZXh0Q29sdW1uLCBEZXZFeHByZXNzLldlYi52MTQuMiwgVmVyc2lvbj0xNC4yLjQu\n" +
                "MCwgQ3VsdHVyZT1uZXV0cmFsLCBQdWJsaWNLZXlUb2tlbj1iODhkMTc1NGQ3MDBlNDlhGDwrAAYBBRQrAAJkZBYCAgEPZBYCZg9k\n" +
                "FgJmD2QWAmYPZBYCBQtEWE1haW5UYWJsZQ9kFgQFCkRYRGF0YVJvdzAPZBYCBQZ0Y3JvdzAPZBYCZg9kFgJmD2QWBgIBDzwrAAQB\n" +
                "AA8WBB8BBRAxIEJlbm5ldHQgQXZlbnVlHwJnZGQCAw88KwAEAQAPFgQfAQUSTkVXIFlPUkssIE5ZIDEwMDMzHwJnZGQCBA8VEhxJ\n" +
                "bWFnZXNTbmFwc2hvdC9Db25uZWN0ZWQucG5nBEFVVE8GU1VNTUVSAAU4MC4yMAI3NgEwAzEzNQMxMjIDMTExBDM3OTMEMzc5MwQz\n" +
                "NzkzBDM3OTMEMzc5MwQzNzkzBDM3OTMEMzc5M2QFCkRYRGF0YVJvdzEPZBYCBQZ0Y3JvdzEPZBYCZg9kFgJmD2QWBgIBDzwrAAQB\n" +
                "AA8WBB8BBR8xNTIwLTE1MjYgU2FpbnQgTmljaG9sYXMgQXZlbnVlHwJnZGQCAw88KwAEAQAPFgQfAQUSTkVXIFlPUkssIE5ZIDEw\n" +
                "MDMzHwJnZGQCBA8VEhxJbWFnZXNTbmFwc2hvdC9Db25uZWN0ZWQucG5nBEFVVE8GU1VNTUVSAAU4MS40NgI3NgEwAzEzNwMxMTcD\n" +
                "MTEzBDM3OTQEMzc5NAQzNzk0BDM3OTQEMzc5NAQzNzk0BDM3OTQEMzc5NGQCDQ88KwAEAQAPFgIfAQUFRmFsc2VkZAIPDzwrAAkB\n" +
                "AA8WAh8AZ2RkAhEPPCsACQEADxYCHwBnZGQCEw88KwAJAQAPFgIfAGdkZAIVDzwrAAkCAA8WAh8AZ2QGD2QQFgICBgIHFgI8KwAM\n" +
                "AQAWAh4HVmlzaWJsZWg8KwAMAQAWAh8EaGRkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYPBQRtZW51BQRncmlk\n" +
                "BQpyZXBvcnRNZW51BRZyZXBvcnRNZW51V2l0aEZ1ZWxUcmFrBQtzZXR0aW5nTWVudQURY29tbXVuaWNhdGlvbk1lbnUFDnNldHBv\n" +
                "aW50c1BvcHVwBQ1jb250YWN0c1BvcHVwBQtib2lsZXJQb3B1cAUKcGFuZWxQb3B1cAUIYXB0UG9wdXAFCGVtc1BvcHVwBQl1c2VyUG9wdXAFDWZvcmVjYXN0UG9wdXAFD2NsaWVudE5vdGVQb3B1cH1Oqed\n" +
                "+etyEy8D3HF/CHzg5SFMok6bE0SlTQmpERLd3"));
        urlParameters.add(new BasicNameValuePair("__VIEWSTATEGENERATOR", "269772BF"));
        urlParameters.add(new BasicNameValuePair("aptPopupWS", "0:0:-1:-10000:-10000:0:775:590:1:0:0:0"));
        urlParameters.add(new BasicNameValuePair("boilerPopupWS", "0:0:-1:-10000:-10000:0:1010:600:1:0:0:0"));

        urlParameters.add(new BasicNameValuePair("clientNotePopup$clientNoteMemo", ""));
        urlParameters.add(new BasicNameValuePair("clientNotePopupWS", "0:0:-1:-10000:-10000:0:300:300:1:0:0:0"));
        urlParameters.add(new BasicNameValuePair("contactsPopupWS", "0:0:-1:-10000:-10000:0:300:300:1:0:0:0"));
        urlParameters.add(new BasicNameValuePair("clientNotePopupWS", "0:0:-1:-10000:-10000:0:930:450:1:0:0:0"));

        urlParameters.add(new BasicNameValuePair("emsPopupWS", "0:0:-1:-10000:-10000:0:960:618:1:0:0:0"));
        urlParameters.add(new BasicNameValuePair("forecastPopupWS", "0:0:-1:-10000:-10000:0:500:200:1:0:0:0"));

        urlParameters.add(new BasicNameValuePair("grid$DXKVInput", "['3793','3794']"));
        urlParameters.add(new BasicNameValuePair("grid$DXSelInput", ""));
        urlParameters.add(new BasicNameValuePair("panelPopupWS", "0:0:-1:-10000:-10000:0:452:540:1:0:0:0"));
        urlParameters.add(new BasicNameValuePair("setpointsPopupWS", "0:0:-1:-10000:-10000:0:810:590:1:0:0:0"));
        urlParameters.add(new BasicNameValuePair("userPopupWS", "0:0:-1:-10000:-10000:0:344:230:1:0:0:0"));
        //  urlParameters.add(new BasicNameValuePair("__EVENTTARGET", ""));
        //urlParameters.add(new BasicNameValuePair("__EVENTTARGET", ""));

        HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);
        httpPost.setEntity(postParams);

        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

        System.out.println("POST Response Status:: "
                + httpResponse.getStatusLine().getStatusCode());

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpResponse.getEntity().getContent()));

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();

        // print result
        System.out.println(response.toString());
        httpClient.close();

    }

    public static byte[] printBytes(List<BasicClientCookie> cookie, String url) throws IOException {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        CookieStore cookieStore = httpClient.getCookieStore();
        for(BasicClientCookie cookie1 : cookie) {
//            BasicClientCookie c = new BasicClientCookie(temp.getKey(), temp.getValue());
            cookieStore.addCookie(cookie1);
        }

        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("User-Agent", USER_AGENT);

        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
//
//        System.out.println("GET Response Status:: "
//                + httpResponse.getStatusLine().getStatusCode());

//        BufferedReader reader = new BufferedReader(new InputStreamReader(
//                httpResponse.getEntity().getContent()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.copy(httpResponse.getEntity().getContent(), out);
        return out.toByteArray();
    }

    private static void sendPOST3(List<Cookie> cookies, String key) throws IOException {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        CookieStore cookieStore = httpClient.getCookieStore();
        for (Cookie c : cookies) {
            cookieStore.addCookie(c);
        }

        //http://virtualremote500.com/(S(3jkhzwc2pqxkwv0203yptaxl))/Snapshot.aspx

        HttpPost httpPost = new HttpPost("http://virtualremote500.com/(S(" + key + "))/Snapshot.aspx");
        httpPost.addHeader("User-Agent", USER_AGENT);
        httpPost.addHeader("Referer", "http://virtualremote500.com/(S(" + key + "))/Snapshot.aspx");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("ASPxButtonEdit1", ""));
        urlParameters.add(new BasicNameValuePair("DXCss", "0_1755,1_16,0_1759,1_8,1_6,0_1611,0_1613,0_1615,0_1617,1_14,1_1,1_7,Images/EnTechIcon.ico,DevExpress/fixes.css"));
        urlParameters.add(new BasicNameValuePair("DXScript", "1_187,1_101,1_180,1_98,1_172,1_178,1_163,1_130,1_137,1_120,1_154,1_99,1_136,1_171,1_170"));
        urlParameters.add(new BasicNameValuePair("__CALLBACKID", "callback"));
//        urlParameters.add(new BasicNameValuePair("__CALLBACKPARAM", "c0:3793"));
        urlParameters.add(new BasicNameValuePair("__CALLBACKPARAM", "c0:3793"));
        urlParameters.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
        urlParameters.add(new BasicNameValuePair("__EVENTTARGET", ""));
        urlParameters.add(new BasicNameValuePair("__EVENTVALIDATION", "/wEdAA36QKZj1Pdyizlkg3Et9VVKdPvVnubBhRZQJTCAm7kvR9bSjAUQSpSB4eDcOSwPf4xFRSpuKQP8/mP6qofc5c2VJDp79r8OQeJmlS\n" +
                "/VjstwWpDo41b8u7kvB9q3GLtEhc2rlyAqOB1W0S00/1EO31v1avMJv06wJ07rlS1iaRumiovasdc8d3bq9zTE5fyqS+6l31wEFrJt8g13XLxjXGjEK2EQcPT7da0rOybfYM0oP1tLvXANRHBT87z7c8r5iQu25KBPIJRKbLMUXlWjdOWEfl4fQ8hkNt\n" +
                "+YU6bW88gHCiHpHoHbf6X5GrYa9YAJCHY="));

        urlParameters.add(new BasicNameValuePair("__VIEWSTATE", "/wEPDwUKLTk4NTY3MDA5Mg9kFgICAw9kFhACAQ88KwAJAQAPFgIeDl8hVXNlVmlld1N0YXRlZ2RkAgMPPCsABAEADxYCHgVWYWx1\n" +
                "ZWVkZAIHDzwrABwDAA8WAh4PRGF0YVNvdXJjZUJvdW5kZ2QGD2QQFgECARYBPCsADAEAFgIeCVNvcnRJbmRleGYPFgECARYBBX9E\n" +
                "ZXZFeHByZXNzLldlYi5HcmlkVmlld0RhdGFUZXh0Q29sdW1uLCBEZXZFeHByZXNzLldlYi52MTQuMiwgVmVyc2lvbj0xNC4yLjQu\n" +
                "MCwgQ3VsdHVyZT1uZXV0cmFsLCBQdWJsaWNLZXlUb2tlbj1iODhkMTc1NGQ3MDBlNDlhGDwrAAYBBRQrAAJkZBYCAgEPZBYCZg9k\n" +
                "FgJmD2QWAmYPZBYCBQtEWE1haW5UYWJsZQ9kFgQFCkRYRGF0YVJvdzAPZBYCBQZ0Y3JvdzAPZBYCZg9kFgJmD2QWBgIBDzwrAAQB\n" +
                "AA8WBB8BBRAxIEJlbm5ldHQgQXZlbnVlHwJnZGQCAw88KwAEAQAPFgQfAQUSTkVXIFlPUkssIE5ZIDEwMDMzHwJnZGQCBA8VEhxJ\n" +
                "bWFnZXNTbmFwc2hvdC9Db25uZWN0ZWQucG5nBEFVVE8GU1VNTUVSAAU4MC4yMAI3NgEwAzEzNQMxMjIDMTExBDM3OTMEMzc5MwQz\n" +
                "NzkzBDM3OTMEMzc5MwQzNzkzBDM3OTMEMzc5M2QFCkRYRGF0YVJvdzEPZBYCBQZ0Y3JvdzEPZBYCZg9kFgJmD2QWBgIBDzwrAAQB\n" +
                "AA8WBB8BBR8xNTIwLTE1MjYgU2FpbnQgTmljaG9sYXMgQXZlbnVlHwJnZGQCAw88KwAEAQAPFgQfAQUSTkVXIFlPUkssIE5ZIDEw\n" +
                "MDMzHwJnZGQCBA8VEhxJbWFnZXNTbmFwc2hvdC9Db25uZWN0ZWQucG5nBEFVVE8GU1VNTUVSAAU4MS40NgI3NgEwAzEzNwMxMTcD\n" +
                "MTEzBDM3OTQEMzc5NAQzNzk0BDM3OTQEMzc5NAQzNzk0BDM3OTQEMzc5NGQCDQ88KwAEAQAPFgIfAQUFRmFsc2VkZAIPDzwrAAkB\n" +
                "AA8WAh8AZ2RkAhEPPCsACQEADxYCHwBnZGQCEw88KwAJAQAPFgIfAGdkZAIVDzwrAAkCAA8WAh8AZ2QGD2QQFgICBgIHFgI8KwAM\n" +
                "AQAWAh4HVmlzaWJsZWg8KwAMAQAWAh8EaGRkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYPBQRtZW51BQRncmlk\n" +
                "BQpyZXBvcnRNZW51BRZyZXBvcnRNZW51V2l0aEZ1ZWxUcmFrBQtzZXR0aW5nTWVudQURY29tbXVuaWNhdGlvbk1lbnUFDnNldHBv\n" +
                "aW50c1BvcHVwBQ1jb250YWN0c1BvcHVwBQtib2lsZXJQb3B1cAUKcGFuZWxQb3B1cAUIYXB0UG9wdXAFCGVtc1BvcHVwBQl1c2VyUG9wdXAFDWZvcmVjYXN0UG9wdXAFD2NsaWVudE5vdGVQb3B1cH1Oqed\n" +
                "+etyEy8D3HF/CHzg5SFMok6bE0SlTQmpERLd3"));
        urlParameters.add(new BasicNameValuePair("__VIEWSTATEGENERATOR", "269772BF"));
        urlParameters.add(new BasicNameValuePair("aptPopupWS", "0:0:-1:-10000:-10000:0:775:590:1:0:0:0"));
        urlParameters.add(new BasicNameValuePair("boilerPopupWS", "0:0:-1:-10000:-10000:0:1010:600:1:0:0:0"));

        urlParameters.add(new BasicNameValuePair("clientNotePopup$clientNoteMemo", ""));
        urlParameters.add(new BasicNameValuePair("clientNotePopupWS", "0:0:-1:-10000:-10000:0:300:300:1:0:0:0"));
        urlParameters.add(new BasicNameValuePair("contactsPopupWS", "0:0:-1:-10000:-10000:0:300:300:1:0:0:0"));
        urlParameters.add(new BasicNameValuePair("clientNotePopupWS", "0:0:-1:-10000:-10000:0:930:450:1:0:0:0"));

        urlParameters.add(new BasicNameValuePair("emsPopupWS", "0:0:-1:-10000:-10000:0:960:618:1:0:0:0"));
        urlParameters.add(new BasicNameValuePair("forecastPopupWS", "0:0:-1:-10000:-10000:0:500:200:1:0:0:0"));

        urlParameters.add(new BasicNameValuePair("grid$DXKVInput", "['3793','3794']"));
        urlParameters.add(new BasicNameValuePair("grid$DXSelInput", ""));
        urlParameters.add(new BasicNameValuePair("panelPopupWS", "0:0:-1:-10000:-10000:0:452:540:1:0:0:0"));
        urlParameters.add(new BasicNameValuePair("setpointsPopupWS", "0:0:-1:-10000:-10000:0:810:590:1:0:0:0"));
        urlParameters.add(new BasicNameValuePair("userPopupWS", "0:0:-1:-10000:-10000:0:344:230:1:0:0:0"));
        //  urlParameters.add(new BasicNameValuePair("__EVENTTARGET", ""));
        //urlParameters.add(new BasicNameValuePair("__EVENTTARGET", ""));

        HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);
        httpPost.setEntity(postParams);

        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

        System.out.println("POST Response Status:: "
                + httpResponse.getStatusLine().getStatusCode());

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpResponse.getEntity().getContent()));

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();

        // print result
        System.out.println(response.toString());
        httpClient.close();

    }

    private static void sendPOST3(List<Cookie> cookies) throws IOException {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        CookieStore cookieStore = httpClient.getCookieStore();
        for (Cookie c : cookies) {
            cookieStore.addCookie(c);
        }


        HttpPost httpPost = new HttpPost("http://virtualremote500.com/(S(1jzdb2clporlkc1cqub4alca))/Chart.aspx");
        httpPost.addHeader("User-Agent", USER_AGENT);
        // httpPost.addHeader("Referer", "http://virtualremote500.com/(S(mwreegpmjnczojsz0kpjnsb3))/Snapshot.aspx");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("__EVENTTARGET", "xslButton"));
        urlParameters.add(new BasicNameValuePair("__EVENTARGUMENT", "Click"));

        urlParameters.add(new BasicNameValuePair("__VIEWSTATEGENERATOR", "880B5DAF"));


        urlParameters.add(new BasicNameValuePair("datePicker_Raw", "1434496824156.87"));
        urlParameters.add(new BasicNameValuePair("datePicker", "6/16/2015"));
//        urlParameters.add(new BasicNameValuePair("datePicker_DDDWS", "0:0:-1:-10000:-10000:0:0:0:1:0:0:0")); // 30  min
        urlParameters.add(new BasicNameValuePair("datePicker_DDDWS", "0:0:12000:14:60:1:0:0:1:0:0:0")); // 10 min
        urlParameters.add(new BasicNameValuePair("datePicker_DDD_C_FNPWS", "0:0:-1:-10000:-10000:0:0:0:1:0:0:0"));

        urlParameters.add(new BasicNameValuePair("datePicker$DDD$C", "06/16/2015:06/16/2015"));
        urlParameters.add(new BasicNameValuePair("spanBox_VI", "10"));

        urlParameters.add(new BasicNameValuePair("spanBox", "Frequency: 10 minutes"));
        urlParameters.add(new BasicNameValuePair("grid$DXSelInput", ""));
//        urlParameters.add(new BasicNameValuePair("spanBox_DDDWS", "0:0:-1:-10000:-10000:0:0:0:1:0:0:0"));
        urlParameters.add(new BasicNameValuePair("spanBox_DDDWS", "0:0:12000:198:60:1:178:73:1:0:0:0"));
        // urlParameters.add(new BasicNameValuePair("setpointsPopupWS", "0:0:-1:-10000:-10000:0:810:590:1:0:0:0"));
        urlParameters.add(new BasicNameValuePair("spanBox_DDD_LDeletedItems", ""));
        urlParameters.add(new BasicNameValuePair("spanBox$DDD$L", "10"));
        //  urlParameters.add(new BasicNameValuePair("__EVENTTARGET", ""));
        //urlParameters.add(new BasicNameValuePair("__EVENTTARGET", ""));

        HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);
        httpPost.setEntity(postParams);

        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

        System.out.println("POST Response Status:: "
                + httpResponse.getStatusLine().getStatusCode());

//        BufferedReader reader = new BufferedReader(new InputStreamReader(
//                httpResponse.getEntity().getContent()));

        HSSFWorkbook workbook = new HSSFWorkbook(httpResponse.getEntity().getContent());

        HSSFSheet sheet = workbook.getSheetAt(0);

        //Iterate through each rows from first sheet
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            //For each row, iterate through each columns
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {

                Cell cell = cellIterator.next();

                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_BOOLEAN:
                        System.out.print(cell.getBooleanCellValue() + "\t\t");
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        System.out.print(cell.getNumericCellValue() + "\t\t");
                        break;
                    case Cell.CELL_TYPE_STRING:
                        System.out.print(cell.getStringCellValue() + "\t\t");
                        break;
                }
            }
            System.out.println("");
        }
        httpClient.close();

    }

    public static void sendPOST4(String cookieValue, String dateStr, String urlStr) throws IOException {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        CookieStore cookieStore = httpClient.getCookieStore();
        cookieStore.addCookie(new EnTechDigitalCookie(cookieValue));

        HttpPost httpPost = new HttpPost("http://virtualremote500.com/(S(jz1pxlfditehyanv0dujkqc0))/Chart.aspx");
        httpPost.addHeader("User-Agent", USER_AGENT);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("__EVENTTARGET", "xslButton"));
        urlParameters.add(new BasicNameValuePair("__EVENTARGUMENT", "Click"));
        urlParameters.add(new BasicNameValuePair("__VIEWSTATEGENERATOR", "880B5DAF"));


      //  urlParameters.add(new BasicNameValuePair("datePicker_Raw", "1434572458705.71"));
        urlParameters.add(new BasicNameValuePair("datePicker_Raw", "1434577767504.24"));
//        urlParameters.add(new BasicNameValuePair("datePicker_Raw", "1434489050478"));
//        urlParameters.add(new BasicNameValuePair("datePicker_Raw", "1434575450478"));
//        urlParameters.add(new BasicNameValuePair("datePicker_Raw", "1434402650478"));
        urlParameters.add(new BasicNameValuePair("datePicker", "6/17/2015"));
//        urlParameters.add(new BasicNameValuePair("datePicker_DDDWS", "0:0:-1:-10000:-10000:0:0:0:1:0:0:0")); // 30  min
        urlParameters.add(new BasicNameValuePair("datePicker_DDDWS", "0:0:12000:14:60:1:0:0:1:0:0:0")); // 10 min
        urlParameters.add(new BasicNameValuePair("datePicker_DDD_C_FNPWS", "0:0:-1:-10000:-10000:0:0:0:1:0:0:0"));

        urlParameters.add(new BasicNameValuePair("datePicker$DDD$C", "06/17/2015:06/17/2015"));
        urlParameters.add(new BasicNameValuePair("spanBox_VI", "10"));

        urlParameters.add(new BasicNameValuePair("spanBox", "Frequency: 10 minutes"));
        urlParameters.add(new BasicNameValuePair("grid$DXSelInput", ""));
//        urlParameters.add(new BasicNameValuePair("spanBox_DDDWS", "0:0:-1:-10000:-10000:0:0:0:1:0:0:0"));
        urlParameters.add(new BasicNameValuePair("spanBox_DDDWS", "0:0:12000:198:60:1:178:73:1:0:0:0"));
        // urlParameters.add(new BasicNameValuePair("setpointsPopupWS", "0:0:-1:-10000:-10000:0:810:590:1:0:0:0"));
        urlParameters.add(new BasicNameValuePair("spanBox_DDD_LDeletedItems", ""));
        urlParameters.add(new BasicNameValuePair("spanBox$DDD$L", "10"));

        HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);
        httpPost.setEntity(postParams);

        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

        System.out.println("POST Response Status:: "
                + httpResponse.getStatusLine().getStatusCode());

//        BufferedReader reader = new BufferedReader(new InputStreamReader(
//                httpResponse.getEntity().getContent()));

        HSSFWorkbook workbook = new HSSFWorkbook(httpResponse.getEntity().getContent());

        HSSFSheet sheet = workbook.getSheetAt(0);

        //Iterate through each rows from first sheet
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            //For each row, iterate through each columns
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {

                Cell cell = cellIterator.next();

                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_BOOLEAN:
                        System.out.print(cell.getBooleanCellValue() + "\t\t");
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        System.out.print(cell.getNumericCellValue() + "\t\t");
                        break;
                    case Cell.CELL_TYPE_STRING:
                        System.out.print(cell.getStringCellValue() + "\t\t");
                        break;
                }
            }
            System.out.println("");
        }

        httpClient.close();

    }

    public static void main(String[] args) throws IOException {
        sendPOST4("122D520D472CB339BFEAAE84ED9B59D1286DE3CBAE6CAE6E28C6E0CFA708E3E0AA79863BA24FFE8198DA3DB2FC952B081D2944BBCAE9286F1EDDA313C229570BE7245D82D118A0E11D28EF38663436F1B37D446BBD71659A82AA1D3971C57275DF5FD6526585DC17C708E4C44BCE211605689487888B841E18DCE1D2C4DD9572",
                "", "");
    }


}
