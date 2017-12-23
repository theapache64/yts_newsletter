package com.theah64.webengine.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by theapache64 on 9/4/16.
 */
public class RandomString {


    private static final String randomEngine = "0123456789AaBbCcDdEeFfGgHhIiJjKkLkMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
    private static Random random = new Random();
    ;

    public static String get(final int length) {
        return getRandomString(length);
    }


    private static String getRandomString(final int length) {

        final StringBuilder apiKeyBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            apiKeyBuilder.append(randomEngine.charAt(random.nextInt(randomEngine.length())));
        }
        return apiKeyBuilder.toString();
    }

    public static String getRandomFilename(final int fileNameLength, final String fileExtension) {
        return getRandomString(fileNameLength) + fileExtension;
    }


    public static String getRandomNumber(long length) {
        final StringBuilder numBuilder = new StringBuilder();
        System.out.println(length % 18);
        long loopCount = length / 18;
        for (int i = 0; i < loopCount; i++) {
            numBuilder.append(getRandomNumberLimited(18));
        }
        numBuilder.append(getRandomNumberLimited((int) length % 18));
        return numBuilder.toString();
    }

    private static String getRandomNumberLimited(int length) {

        if (length < 1) {
            throw new IllegalArgumentException("Length must be > 0");
        }

        if (length > 18) {
            throw new IllegalArgumentException("Length must be < 19");
        }


        String sLowerLimit = length == 1 ? "1" : (1 + String.format("%0" + (length - 1) + "d", 0));
        final long lowerLimit = Long.parseLong(sLowerLimit);
        final long upperLimit = Long.parseLong(sLowerLimit.replaceAll("\\d", "9"));
        return String.valueOf(ThreadLocalRandom.current().nextLong(lowerLimit, upperLimit + 1));
    }
}
