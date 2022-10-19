package com.tkachev.adboard.utils;

import java.util.Locale;

public final class Util {
    public Util() {
    }

    public static Long parseLongOrNull(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Double parseDoubleOrNull(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Float round(float value, int precision) {
        String format = "%(." + precision + "f";

        return Float.parseFloat(String.format(Locale.ENGLISH, format, value));
    }
}
