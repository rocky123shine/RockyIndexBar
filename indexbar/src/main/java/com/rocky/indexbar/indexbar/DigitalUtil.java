package com.rocky.indexbar.indexbar;

import java.util.regex.Pattern;

public class DigitalUtil {

    static Pattern p = Pattern.compile("[0-9]*");

    public static boolean isDigital(String str) {
        return p.matcher(str).matches();
    }
}