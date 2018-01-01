package com.theah64.webengine.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by theapache64 on 1/10/17.
 */
public class CommonUtils {
    public static String getProper(int size, String singular, String plural) {
        return size <= 1 ? singular : plural;
    }

    public static boolean isJSONValid(String test) throws Request.RequestException {
        String error = null;
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                throw new Request.RequestException(ex1.getMessage());
            }
        }
        return true;
    }
}
