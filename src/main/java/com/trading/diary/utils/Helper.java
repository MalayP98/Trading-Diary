package com.trading.diary.utils;

/**
 * Miscellaneous console helpers used by the terminal application.
 */
public class Helper {

    private Helper() {
    }

    /**
     * Converts the UI's y/n style input into a boolean value using a trimmed, case-insensitive comparison.
     */
    public static boolean booleanInputConverter(String input) {
        String normalizedInput = input.trim().toLowerCase();
        return normalizedInput.equals("y");
    }

    /**
     * Prints blank lines to create spacing in console output.
     */
    public static void skipLines(int lines) {
        System.out.println("\n".repeat(Math.max(0, lines)));
    }


}
