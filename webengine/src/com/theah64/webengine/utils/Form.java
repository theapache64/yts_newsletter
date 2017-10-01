package com.theah64.webengine.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by theapache64 on 27/1/17.
 */
public class Form {

    public static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static final String KEY_IS_SUBMITTED = "is_submitted";
    private final HttpServletRequest request;

    public Form(HttpServletRequest request) {
        this.request = request;
    }

    public boolean isSubmitted() {
        return request.getParameter(KEY_IS_SUBMITTED) != null;
    }

    public String getString(String key) {
        final String value = request.getParameter(key);
        if (value != null && !value.isEmpty()) {
            return value;
        }
        return null;
    }

    public int getInt(String key) {
        final String value = getString(key);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
