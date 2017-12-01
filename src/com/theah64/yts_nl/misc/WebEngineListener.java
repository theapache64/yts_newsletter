package com.theah64.yts_nl.misc;

import com.theah64.webengine.utils.MailHelper;
import com.theah64.webengine.utils.WebEngineConfig;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by theapache64 on 1/10/17.
 */
@WebListener
public class WebEngineListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("WebEngine config initialized");
        WebEngineConfig.init(
                "jdbc/yts_newsletter",
                "jdbc/yts_newsletter",
                false,
                "http://localhost:8080/yts_newsletter",
                "http://theapache64.cf:8080/yts_newsletter"
        );
        MailHelper.init(SecretConstants.GMAIL_USERNAME, SecretConstants.GMAIL_PASSWORD);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
