package com.theah64.webengine.utils;

/**
 * Created by theapache64 on 1/10/17.
 */
public abstract class WebEngineConfig {

    private static String localConfigName;
    private static String remoteConfigName;
    private static boolean debugMode;
    private static String localBaseUrl;
    private static String remoteBaseUrl;

    public static String getLocalConfigName() {
        return localConfigName;
    }

    public static String getRemoteConfigName() {
        return remoteConfigName;
    }

    public static void init(String localConfigName, String remoteConfigName, final boolean debugMode, final String localBaseUrl, final String remoteBaseUrl) {
        WebEngineConfig.localConfigName = localConfigName;
        WebEngineConfig.remoteConfigName = remoteConfigName;
        WebEngineConfig.debugMode = debugMode;
        WebEngineConfig.localBaseUrl = localBaseUrl;
        WebEngineConfig.remoteBaseUrl = remoteBaseUrl;
    }

    public static boolean isDebugMode() {
        return debugMode;
    }

    public static String getBaseURL() {
        return (WebEngineConfig.isDebugMode() ? localBaseUrl : remoteBaseUrl);
    }
}
