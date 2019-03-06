package com.theah64.webengine.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by theapache64 on 1/10/17.
 */
public class CommonUtils {
    public static String getProper(int size, String singular, String plural) {
        return size <= 1 ? singular : plural;
    }

    public static boolean isJSONValid(String jsonInString, String message) throws Request.RequestException {

        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(jsonInString);
            return true;
        } catch (IOException e) {
            throw new Request.RequestException(message + ":" + e.getMessage());
        }
    }
}
