package com.theah64.webengine.utils;


import com.theah64.webengine.exceptions.MailException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
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

    @SuppressWarnings("SameParameterValue")
    public static void sendMail(String to, final String subject, String message, String fromName) throws MailException {

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
            mimeMessage.setFrom(new InternetAddress(gmailUsername, fromName));
            if (to.contains(",")) {
                //Bulk mail
                mimeMessage.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(to));
            } else {
                //Single mail
                mimeMessage.setRecipient(Message.RecipientType.TO, InternetAddress.parse(to)[0]);
            }
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(message, "text/html; charset=utf-8");

            Transport.send(mimeMessage);
            System.out.println("Mail sent :" + message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new MailException(e.getMessage());
        }

    }

    private static final String VERIFICATION_EMAIL = "<html style=\"margin: 0 auto;padding: 0;\"> <head style=\"margin: 0 auto;padding: 0;\"> <link href=\"https://fonts.googleapis.com/css?family=Roboto:400,500,700\" rel=\"stylesheet\" style=\"margin: 0 auto;padding: 0;\"> <style style=\"margin: 0 auto;padding: 0;\"> * { margin: 0 auto; padding: 0; } p, a { font-family: 'Roboto', sans-serif; } body { background: #171717; } div#body { text-align: center; color: white; padding: 50px; height: 158px; } a { text-decoration: none; } a#verification_link { background: #6ac045; border-radius: 3px; -webkit-border-radius: 3px; -moz-border-radius: 3px; color: white !important; padding: 8px 34px; font-weight: 800; } div#header { background-color: #1d1d1d; width: 100%; height: 80px; border-bottom: 1px solid #2f2f2f; } div#footer { background-color: #1d1d1d; width: 100%; text-align: center; border-top: 1px solid #2f2f2f; position: absolute; bottom: 0px; } p#credits { padding: 10px; color: #565656; } p#credits a { color: #565656; } img#yts_logo { padding: 22px; } </style> </head> <body style=\"margin: 0 auto;padding: 0;background: #171717;\"> <div id=\"header\" style=\"margin: 0 auto;padding: 0;background-color: #1d1d1d;width: 100%;height: 80px;border-bottom: 1px solid #2f2f2f;\"> <img id=\"yts_logo\" src=\"http://theapache64.com/yts_newsletter/yts_logo.png\" style=\"margin: 0 auto;padding: 22px;\"> </div> <div id=\"body\" style=\"margin: 0 auto;padding: 50px;text-align: center;color: white;height: 158px;\"> <p id=\"verify_instruction\" style=\"margin: 0 auto;padding: 0;font-family: 'Roboto', sans-serif;\">To verify your <b style=\"margin: 0 auto;padding: 0;\">YTS</b> newsletter subscription, please click below button.</p><br style=\"margin: 0 auto;padding: 0;\"><br style=\"margin: 0 auto;padding: 0;\"> <a id=\"verification_link\" href=\"VERIFICATION_LINK\" style=\"margin: 0 auto;padding: 8px 34px;font-family: 'Roboto', sans-serif;text-decoration: none;background: #6ac045;border-radius: 3px;-webkit-border-radius: 3px;-moz-border-radius: 3px;font-weight: 800;color: white !important;\">Verify</a> </div> <div id=\"footer\" style=\"margin: 0 auto;padding: 0;background-color: #1d1d1d;width: 100%;text-align: center;border-top: 1px solid #2f2f2f;position: absolute;bottom: 0px;\"> <p id=\"credits\" style=\"margin: 0 auto;padding: 10px;font-family: 'Roboto', sans-serif;color: #565656;\"><a target=\"_blank\" href=\"http://github.com/theapache64/yts_newsletter\" style=\"margin: 0 auto;padding: 0;font-family: 'Roboto', sans-serif;text-decoration: none;color: #565656;\"> A Github Project</a></p> </div> </body> </html>";

    public static String getVerificationHtml(String verificationLink) {
        return VERIFICATION_EMAIL.replace("VERIFICATION_LINK", verificationLink);
    }
}
