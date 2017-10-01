package com.theah64.webengine.utils;

/**
 * Created by theapache64 on 1/10/17.
 */
public class CommonUtils {
    public static String getProper(int size, String singular, String plural) {
        return size <= 1 ? singular : plural;
    }
}
