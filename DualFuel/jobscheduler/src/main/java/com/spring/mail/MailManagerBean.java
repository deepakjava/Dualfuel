package com.spring.mail;

import com.common.HardCodedProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service("mailManager")
public class MailManagerBean implements IMailManager {

	@Value("${mail.smtp.from}")
	private String fromEmail;

	private JavaMailSender mailSender;

	@Autowired
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendMail(String mailTo, String subject, String mailBody,
			MailMessage.Type mailType) throws AddressException, Exception {

        String[] toStr = mailTo.split(",");
        InternetAddress[] to = new InternetAddress[toStr.length];
        for(int i=0; i<toStr.length; i++){
            to[i] = new InternetAddress(toStr[i].trim());
        }

		this.sendMail(new InternetAddress(
				HardCodedProperties._FROM_EMAIL_ADDESS), to, subject, mailBody, mailType);
	}

	public void sendMail(final InternetAddress sender,
			final InternetAddress[] recipient, final String subject,
			final String body, final MailMessage.Type mailType)
			throws Exception {
		try {
			mailSender.send(new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws Exception {
					MimeMessageHelper message = new MimeMessageHelper(
							mimeMessage, false, "UTF-8");
					message.setFrom(fromEmail);
					message.setTo(recipient);
					message.setSubject(subject);
					switch (mailType) {
					case HTML:
						message.setText(body, true);
						break;
					default:
						message.setText(body);
						break;
					}
				}
			});
		} catch (MailSendException e) {
			throw new Exception(e);
		}
	}
}
