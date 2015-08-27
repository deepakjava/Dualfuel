package com.common;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class XML_to_HTML_Converter {

	public static String convert(String xmlValue, String xslPath) {
		
		String htmlString = null;
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();

			Source xslDoc = new StreamSource(xslPath);
			Source xmlDoc = new StreamSource(new ByteArrayInputStream(xmlValue.getBytes()));
			ByteArrayOutputStream htmlFile = new ByteArrayOutputStream();

			Transformer transformer = tFactory.newTransformer(xslDoc);
			transformer.transform(xmlDoc, new StreamResult(htmlFile));
			
			htmlString = new String(htmlFile.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return htmlString;
	}
}
