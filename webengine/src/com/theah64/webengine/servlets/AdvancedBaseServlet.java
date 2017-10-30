package com.theah64.webengine.servlets;


import com.theah64.webengine.utils.Request;
import com.theah64.webengine.utils.RequestException;
import com.theah64.webengine.utils.Response;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by shifar on 16/9/16.
 */
public abstract class AdvancedBaseServlet extends HttpServlet {

    public static final String VERSION_CODE = "/v1";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String ERROR_GET_NOT_SUPPORTED = "GET method not supported";
    private static final String ERROR_POST_NOT_SUPPORTED = "POST method not supported";
    private Request request;
    private PrintWriter out;
    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;

    public HttpServletResponse getHttpServletResponse() {
        return httpServletResponse;
    }

    protected static void setGETMethodNotSupported(HttpServletResponse response) throws IOException {
        notSupported(ERROR_GET_NOT_SUPPORTED, response);
    }

    protected static void POSTMethodNotSupported(HttpServletResponse response) throws IOException {
        notSupported(ERROR_POST_NOT_SUPPORTED, response);
    }

    private static void notSupported(String methodErrorMessage, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENT_TYPE_JSON);
        final PrintWriter out = response.getWriter();

        //GET Method not supported
        out.write(new Response(methodErrorMessage).getResponse());
    }

    protected PrintWriter getWriter() {
        return out;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(getContentType());
        this.httpServletRequest = req;
        this.httpServletResponse = resp;
        out = resp.getWriter();

        try {

            if (getRequiredParameters() != null) {
                request = new Request(req, getRequiredParameters());
            }

            doAdvancedPost();

        } catch (Exception e) {
            e.printStackTrace();
            out.write(new Response(e.getMessage()).toString());
        }
    }

    protected String getContentType() {
        return CONTENT_TYPE_JSON;
    }


    protected abstract String[] getRequiredParameters();

    protected abstract void doAdvancedPost() throws SQLException, RequestException, IOException, ServletException, RequestException;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setGETMethodNotSupported(resp);
    }

    public String getStringParameter(String key) {
        final String value = request.getStringParameter(key);
        if (value != null && !value.trim().isEmpty()) {
            return value;
        }
        return null;
    }


    public boolean getBooleanParameter(String key) {
        return request.getBooleanParameter(key);
    }

    public boolean has(String key) {
        return request.has(key);
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public long getLongParameter(String key) {
        return request.getLongParameter(key);
    }


    public String getStringParameters(String key) {
        final String[] params = request.getStringParameters(key);
        StringBuilder sb = null;
        if (params != null) {
            sb = new StringBuilder();
            for (final String value : params) {
                sb.append(value).append(",");
            }
            return sb.substring(0, sb.length() - 1);
        }
        return null;
    }
}
