package com.theah64.yts_nl.servlets;

import com.theah64.webengine.servlets.AdvancedBaseServlet;
import com.theah64.webengine.utils.RequestException;
import com.theah64.yts_nl.database.Subscriptions;
import org.json.JSONException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by theapache64 on 6/10/17.
 */
@WebServlet(urlPatterns = {"/unsubscribe"})
public class UnSubscribeServlet extends AdvancedBaseServlet {

    @Override
    protected String[] getRequiredParameters() {
        return new String[]{Subscriptions.COLUMN_EMAIL, Subscriptions.COLUMN_VERIFICATION_CODE};
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doAdvancedPost() throws JSONException, SQLException, RequestException, IOException, ServletException, RequestException {

        System.out.println("Hit!");

        final String email = getStringParameter(Subscriptions.COLUMN_EMAIL);
        final String verificationCode = getStringParameter(Subscriptions.COLUMN_VERIFICATION_CODE);

        if (Subscriptions.getInstance().delete(Subscriptions.COLUMN_EMAIL, email, Subscriptions.COLUMN_VERIFICATION_CODE, verificationCode)) {
            //Removed
            getHttpServletResponse().sendRedirect("result.jsp?title=UnSubscribed&message=You're no part of YTS newsletter anymore");
        } else {
            //Failed to removed
            getHttpServletResponse().sendRedirect("result.jsp?title=Failed&message=Something went wrong. Please contact admin.");
        }
    }


}
