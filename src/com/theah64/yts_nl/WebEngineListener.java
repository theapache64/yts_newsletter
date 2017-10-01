package com.theah64.yts_nl;

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
        WebEngineConfig.init("jdbc/yts_newsletterLocal", "jdbc/yts_newsletterRemote");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
