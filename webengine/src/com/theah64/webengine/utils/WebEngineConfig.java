package com.theah64.webengine.utils;

/**
 * Created by theapache64 on 1/10/17.
 */
public abstract class WebEngineConfig {

    private static String localConfigName;
    private static String remoteConfigName;
    private static boolean debugMode;

    public static String getLocalConfigName() {
        return localConfigName;
    }

    public static String getRemoteConfigName() {
        return remoteConfigName;
    }

    public static void init(String localConfigName, String remoteConfigName, final boolean debugMode) {
        WebEngineConfig.localConfigName = localConfigName;
        WebEngineConfig.remoteConfigName = remoteConfigName;
        WebEngineConfig.debugMode = debugMode;
    }

    public static boolean isDebugMode() {
        return debugMode;
    }
}
