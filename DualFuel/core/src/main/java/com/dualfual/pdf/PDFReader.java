package com.dualfual.pdf;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PDFReader {

    public static List<Address> readPDF(IAddressParser parser) throws IOException {
        URL url = new URL(parser.getUrl());
        return readPDF(url.openConnection().getInputStream(), parser);
    }

    public static List<Address> readPDF(InputStream pdf, IAddressParser parser) throws IOException {
        PdfReader reader = new PdfReader(pdf);
        // String page = PdfTextExtractor.getTextFromPage(reader, 4);
        Map<String, Address> address = new LinkedHashMap<String, Address>();

        for (int i = 1; i < reader.getNumberOfPages(); i++) {
            BufferedReader st = new BufferedReader(new StringReader(PdfTextExtractor.getTextFromPage(reader, i)));
            String line = null;
            String lastLine = st.readLine();
            while ((line = st.readLine()) != null) {
                parser.process(address, line.trim(), lastLine, st);
                lastLine = line.trim();
            }
        }

        return new ArrayList<Address>(address.values());
    }

    public static void main(String[] args) throws IOException {
//        URL url = new URL("http://www.dobbsferry.com/docman/departments/treasurer/486-2015-final-assessment-roll/file.html");
        URL url = new URL("http://www.scarsdale.com/Portals/0/Assessor/Scarsdale%202015%20Tentative.pdf");

        readPDF(url.openConnection().getInputStream(), new ScareDaleParser());
    }

}
