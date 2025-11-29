package com.smart.service;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	public boolean sendEmail(String subject, String message, String to) {

		boolean f = false;

		// Must match the authenticated Gmail account
		String from = "gaire.ramprasad98@gmail.com";

		// Hard-coded credentials (NOT recommended for production)
		final String username = "gaire.ramprasad98@gmail.com";
		final String password = "@p@che@200";   // <-- your hard-coded password here

		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		// Creating session
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		session.setDebug(true);

		try {
			MimeMessage m = new MimeMessage(session);

			m.setFrom(new InternetAddress(from));
			m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			m.setSubject(subject);
			m.setContent(message, "text/html; charset=utf-8");

			Transport.send(m);

			System.out.println("Email Sent Successfully...");
			f = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return f;
	}
}
