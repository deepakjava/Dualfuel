package com.job;

import com.job.core.MantraJobRegistry;
import com.job.core.base.MantraJob;
import com.job.core.base.MantraJobExecutionResult;
import com.job.core.base.MantraJobParam;

import com.job.html.HtmlFieldType;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

@MantraJobRegistry(jobType = "ERCOT : WS Download Awards")
public class MantraWSDownload extends MantraJob {

	private static Logger log = Logger.getLogger(MantraWSDownload.class);

	@MantraJobParam(displayName = "ISO", fieldType = HtmlFieldType.INPUT, isRequired = true, htmlFieldWidth = 100, readOnlyField = true)
	private String iso = "ERCOT";

	@MantraJobParam(displayName = "Certificate Alias", fieldType = HtmlFieldType.CERTIFICATE, isRequired = true, htmlFieldWidth = 200)
	private String certificateAliasName;

	@MantraJobParam(displayName = "File Download Path", fieldType = HtmlFieldType.INPUT, isRequired = true, htmlFieldWidth = 600)
	private String fileDownloadPath;

	@MantraJobParam(displayName = "Request Type", fieldType = HtmlFieldType.INPUT, isRequired = true, htmlFieldWidth = 400)
	private String requestType;

	@MantraJobParam(displayName = "DAY Adder", fieldType = HtmlFieldType.INPUT, isRequired = true, htmlFieldWidth = 50)
	private int dayAdder = 1;

	@MantraJobParam(displayName = "Email Group", fieldType = HtmlFieldType.INPUT, isRequired = false, htmlFieldWidth = 150)
	private String emailGroup = "ADMIN";

	@MantraJobParam(displayName = "XSL Template Path", fieldType = HtmlFieldType.INPUT, isRequired = false, htmlFieldWidth = 600)
	private String xslTemplate;

	@Override
	public MantraJobExecutionResult execute() throws Exception {
		// TODO Auto-generated method stub

		// set values

//		Calendar startTime, endTime;
//		startTime = createERCOTCalendar(new Date(), 0);
//		endTime = createERCOTCalendar(new Date(), 2);
//		GregorianCalendar processDate = new GregorianCalendar(
//				TimeZone.getTimeZone("CST"));
//		processDate.add(Calendar.DAY_OF_MONTH, dayAdder);
//
//		if (certificateAliasName == null) {
//			throw new Exception("Certificate Alias is not set.");
//		}
//		CertificatesMapping certMapping = SpringBeanLookup
//				.lookupCertificateService().findCertificateMappings(
//						certificateAliasName);
//
//		if (certMapping == null) {
//			throw new Exception("Certificate Alias =" + certificateAliasName
//					+ ", not mapped.");
//		}
//
//		MantraRequest req = null;
//		String xmlResponse = null;
//		try {
//			req = new MantraGetRequest(requestType /* "AwardedEnergyBid" */,
//					RequestTypeMarketType.DAM, startTime, endTime,
//					certMapping.getCertificatePath(),
//					certMapping.getCertiificatePassword());
//			req.getRequest().setTradingDate(processDate.getTime());
//
//			xmlResponse = MantraLLRequestBuilder.requestErcotServiceXML(
//					HardCodedProperties._ERCOT_WSDL_ENDPT, req,
//					certMapping.getCertificatePath(),
//					certMapping.getCertiificatePassword());
//		} catch (Exception e) {
//			return MantraJobExecutionResult
//					.createRetryOpetation(e.getMessage());
//		}
//
//		if (xmlResponse == null) {
//			return MantraJobExecutionResult
//					.createError(xmlResponse + "is NULL");
//		}
//
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM_dd_yyyy");
//		String formatedDate = simpleDateFormat.format(processDate.getTime());
//
//		String fileStr = fileDownloadPath + requestType + "_" + formatedDate
//				+ ".xml";
//		saveFile(fileStr, xmlResponse);
//		System.out.println("Email : " + emailGroup);
//
//		if (emailGroup != null && emailGroup.length() != 0) {
//
//			UserGroup userGroup = SpringBeanLookup.lookupJobGroupService()
//					.getCurrentUser(emailGroup);
//
//			if (userGroup != null) {
//
//				String html = XML_to_HTML_Converter.convert(xmlResponse,
//						xslTemplate);
//				for (User u : userGroup.getUsers()) {
//
//					SpringBeanLookup.lookupMailManager().sendMail(
//							u.getEmailAddress(),
//							requestType + " for " + formatedDate, html,
//							com.spring.mail.MailMessage.Type.HTML);
//				}
//			} else {
//				log.error("Invalid user group = " + emailGroup);
//			}
//		}

		return MantraJobExecutionResult.createSucess("Job Completed.");
	}

	public static GregorianCalendar createERCOTCalendar(Date day, int Hour) {

		GregorianCalendar calendar = new GregorianCalendar(
				TimeZone.getTimeZone("CST"));

		calendar.setTime(day);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, Hour);

		return calendar;
	}

	public void saveFile(String fileName, String content) throws Exception {
		FileOutputStream file = null;
		try {
			file = new FileOutputStream(fileName);
			file.write(content.getBytes());
			file.close();
		} catch (Throwable e) {
			throw new Exception("Cannot save file to path " + fileName, e);
		} finally {
			if (file != null) {
				file.close();
			}
		}
	}

}
