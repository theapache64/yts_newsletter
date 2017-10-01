package com.theah64.webengine.utils;

import javax.naming.Name;

/**
 * Created by theapache64 on 1/10/17.
 */
public abstract class WebEngineConfig {

    private static String localConfigName;
    private static String remoteConfigName;

    public static String getLocalConfigName() {
        return localConfigName;
    }

    public static String getRemoteConfigName() {
        return remoteConfigName;
    }

    public static void init(String localConfigName, String remoteConfigName) {
        WebEngineConfig.localConfigName = localConfigName;
        WebEngineConfig.remoteConfigName = remoteConfigName;
    }
}
