package com.zachm.buisness_demo.util;

import javafx.util.StringConverter;

public class BetterIntegerStringConverter extends StringConverter<Integer> {

    public BetterIntegerStringConverter() {
    }

    /**
     * Added a way to test if the string only contained numbers
     * This allows us to get a null in the controller for more control
     */
    @Override
    public Integer fromString(String var1) {
        if(var1 == null) {
            return null;
        }
        else {
            if(isOnlyDigit(var1)) {
                System.out.println(isOnlyDigit(var1));
                var1 = var1.trim();
                return var1.length() < 1 ? null : Integer.parseInt(var1);
            }
            else {
                return null;
            }
        }
    }

    /**
     * Part of the params of String Converter
     * Copy & Pasted the Original
     */
    @Override
    public String toString(Integer var1) {
        return var1 == null ? "" : Integer.toString(var1);
    }

    /**
     * Runs a for each to check each character of the string
     * You can run a regex for this but for loops are more readable
     */
    public static boolean isOnlyDigit(String str) {
        for(char c : str.toCharArray()) {
            if(Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
}
