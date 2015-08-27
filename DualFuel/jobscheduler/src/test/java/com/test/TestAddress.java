package com.test;


import com.dto.AddressDAO;
import com.dualfual.pdf.*;
import com.test.datasource.TestDataSource;
import org.junit.Ignore;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class TestAddress {

    @Test
//    @Ignore
    public void saveAddress() throws IOException, SQLException {

//        URL url = new URL("http://www.dobbsferry.com/docman/departments/treasurer/486-2015-final-assessment-roll/file.html");
//        List<Address> address = PDFReader.readPDF(url.openConnection().getInputStream(), new DobbsFerryParser());



//        URL url = new URL("http://www.scarsdale.com/Portals/0/Assessor/Scarsdale%202015%20Tentative.pdf");
//        List<Address> address = PDFReader.readPDF(url.openConnection().getInputStream(), new ScareDaleParser());
        List<Address> address = PDFReader.readPDF(new PortChesterParser());
        DataSource dsMysql = TestDataSource.createMySqlDataSource();

       // AddressDAO.saveAddress(dsMysql, address);
    }

}
