package com.spring.mail;

import javax.mail.internet.AddressException;
import java.io.Serializable;

public interface IMailManager extends Serializable {
	
	public void sendMail(String mailTo, String subject,
                         String mailBody, MailMessage.Type mailType)
			throws AddressException, Exception;

}
