package com.theah64.webengine.utils;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by shifar on 15/4/16.
 */
public class MailHelper {

    private static String gmailUsername, gmailPassword;

    public static void init(final String gmailUsername, final String gmailPassword) {
        MailHelper.gmailUsername = gmailUsername;
        MailHelper.gmailPassword = gmailPassword;
    }

    public static boolean sendMail(String to, final String subject, String message) {

        if (gmailUsername == null || gmailPassword == null) {
            throw new IllegalArgumentException("Gmail username and password shouldn't be null");
        }

        System.out.println("Sending email to " + to);

        System.out.println("u:" + gmailUsername);
        System.out.println("p:" + gmailPassword);


        final Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(gmailUsername, gmailPassword);
            }
        });

        Message mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress(gmailUsername));
            if (to.contains(",")) {
                //Bulk mail
                mimeMessage.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(to));
            } else {
                //Single mail
                mimeMessage.setRecipient(Message.RecipientType.TO, InternetAddress.parse(to)[0]);
            }
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);

            Transport.send(mimeMessage);
            System.out.println("Mail sent :" + message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        System.out.println("Failed to send mail");

        return false;
    }

}
