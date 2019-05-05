package com.uiload.util;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.uiload.data.TestOutput;
import com.uiload.data.TestUrls;

public class Send_Mail {
	public void sendMail(TestOutput tOutput,String to_mail) throws IOException {
		StringBuilder htmlContent = new StringBuilder();

		final TestUrls tUrl = new TestUrls();
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(tUrl.from_email,
								tUrl.from_email_password);
					}
				});

		try {

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(tUrl.from_email));

			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to_mail));
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			Timestamp myTimeStamp = timestamp;
			message.setSubject("**" + tUrl.project_name
					+ ": UI Load Testing Report on "
					+ myTimeStamp.toString().substring(0, 19));

			htmlContent
					.append("<head>"
							+ "<title></title>"
							+ "<meta name=viewport content=width=device-width, initial-scale=1.0/>"
							+ "</head>"
							+ "</html>"
							+ "<body style=margin: 0; padding: 0;>"
							+ "<table align=center border=1 cellpadding=0 cellspacing=0 width=800>"
							+ "<tr>"
							+ "<td align=center bgcolor=#FFFF00 style=padding: 40px 0 30px 0;>"
							+ "<h2>"
							+ tUrl.project_name
							+ ": UI Load Testing Report on "
							+ myTimeStamp.toString().substring(0, 19)
							+ "</h2>"
							+ "</td>"
							+ "</tr>"
							+ "<tr>"
							+ "<td bgcolor=#ffffff>"
							+ "<table border=1 cellpadding=0 cellspacing=0 width=100%>"
							+ "<tr>"
							+ "<td style=padding: 20px 0 30px 0;>"
							+ "<h4>Team,"
							+ "<br><br><br>"
							+ "&nbsp;&nbsp;&nbsp;&nbsp;"
							+ "This is a auto generated mail after <b>"
							+ tUrl.project_name
							+ "</b> Ui load testing completion. To generate this report Firebug, NetExport, Java and Selenium is used. This mail is sent to a testing team of a "
							+ tUrl.project_name
							+ ". For more information about testing result visit link of <a href="+tUrl.har_storage_url+">HarStorage</a>. You can also compare previous Ui load testing reports at HarStorage."
							+ "<br><br>"
							+ "Thanks,"
							+ "<br>"
							+ tUrl.project_name
							+ "-QA"
							+ "</td>"
							+ "</tr>"
							+ "<tr>"
							+ "<td>"
							+ "<table border=1 cellpadding=0 cellspacing=0 width=100%>"
							+ "<tr><th>Test Url</th><th>View</th><th>Full Load Time</th><th>First Byte</th><th>On Load Time</th>"
							+ "<th>Page Size</th><th>Text Size</th><th>Media Size</th>"
							+ "<th>Cache Size</th>" + "<th>More Info</th></tr>");

			for (int i = 0; i < tOutput.full_load_time.size(); i++) {

				htmlContent.append("<tr>" + "<td width=500 valign=top>"
						+ tUrl.urls.get(i).toString() + "</td>"
						+ "<td width=500 valign=top>" + tUrl.view[i].toString()
						+ "</td>" + "<td width=500 valign=top>"
						+ tOutput.full_load_time.get(i).toString() + "</td>"
						+ "<td width=500 valign=top>"
						+ tOutput.time_to_load_first_byte.get(i).toString()
						+ "</td>" + "<td width=500 valign=top>"
						+ tOutput.on_load_time.get(i).toString() + "</td>"
						+ "<td width=500 valign=top>"
						+ tOutput.total_page_size.get(i).toString() + "</td>"
						+ "<td width=500 valign=top>"
						+ tOutput.text_size.get(i).toString() + "</td>"
						+ "<td width=500 valign=top>"
						+ tOutput.media_size.get(i).toString() + "</td>"
						+ "<td width=500 valign=top>"
						+ tOutput.cache_size.get(i).toString() + "</td>"
						+ "<td width=500 valign=top>" + "<a href="
						+ tOutput.more_info.get(i).toString()
						+ ">click here</a></td>" + "</tr>");

			}

			htmlContent.append("</table>" + "</td>" + "</tr>" + "</table>"

			+ "</td>" + "</tr>" + "<tr>" + "<td bgcolor=#FFFF00>"
					+ "Kindly submit your feedback." + "</td>" + "</tr>"
					+ "</table>");
			message.setContent(htmlContent.toString(), "text/html");

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
