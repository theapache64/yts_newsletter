package com.theah64.webengine.utils;

import com.sun.istack.internal.Nullable;
import com.theah64.webengine.exceptions.RequestException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by theapache64 on 27/1/17.
 */
public class Form {

    public static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static final String KEY_IS_SUBMITTED = "is_submitted";
    private final HttpServletRequest request;
    private final String[] reqParams;

    public Form(HttpServletRequest request) {
        this(request, null);
    }

    public Form(HttpServletRequest request, String[] reqParams) {
        this.request = request;
        this.reqParams = reqParams;
    }

    public boolean isSubmitted() {
        return request.getParameter(KEY_IS_SUBMITTED) != null;
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public String getString(String key, @Nullable String defaultValue) {
        final String value = request.getParameter(key);
        if (value != null && !value.isEmpty()) {
            return value;
        }
        return defaultValue != null ? defaultValue : null;
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

    public boolean isAllRequiredParamsAvailable() throws RequestException {
        if (reqParams != null) {
            final StringBuilder errorBuilder = new StringBuilder();
            for (final String reqParam : reqParams) {
                final String val = request.getParameter(reqParam);
                if (val == null || val.trim().isEmpty()) {
                    errorBuilder.append(reqParam).append(",");
                }
            }

            if (errorBuilder.length() != 0) {
                //some error happened
                errorBuilder.insert(0, "Missing params ");

                throw new RequestException(errorBuilder.substring(0, errorBuilder.length() - 1));
            } else {
                //Has all params
                return true;
            }
        } else {
            throw new RequestException("Required params not set");
        }
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(getString(key, "false"));
    }
}
