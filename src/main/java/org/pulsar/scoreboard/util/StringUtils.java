package org.pulsar.scoreboard.util;

public final class StringUtils {

    private StringUtils() {}

    public static boolean isNullOrBlank(String str) {
        return (str == null) || str.isBlank();
    }
}
