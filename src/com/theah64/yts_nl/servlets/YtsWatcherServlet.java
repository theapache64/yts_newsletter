package com.theah64.yts_nl.servlets;

import com.theah64.yts_nl.webengine.RequestException;
import com.theah64.yts_nl.webengine.servlets.AdvancedBaseServlet;
import org.json.JSONException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by theapache64 on 26/9/17.
 */
@WebServlet(urlPatterns = {"/yts_watcher"})
public class YtsWatcherServlet extends AdvancedBaseServlet {

    @Override
    protected String[] getRequiredParameters() {
        return new String[0];
    }

    @Override
    protected void doAdvancedPost() throws JSONException, SQLException, RequestException, IOException, ServletException, RequestException {

    }
}
