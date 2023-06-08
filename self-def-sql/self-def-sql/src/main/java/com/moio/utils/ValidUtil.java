package com.moio.utils;

/**
 * @author molinchang
 *
 * @description valid util
 */
public final class ValidUtil {

    /**
     * validate a string contain number.
     *
     * @param numStr number string
     * @return true - false
     */
    public static boolean isContainNumber(String numStr) {

        for (int i = 0; i < 10; i++) {
            if (numStr.contains(i + "")) {
                return true;
            }
        }
        return false;
    }

}
