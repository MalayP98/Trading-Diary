package com.trading.diary.utils;

public class Helper {

    private Helper() {
    }

    public static boolean booleanInputConverter(String input) {
        String normalizedInput = input.trim().toLowerCase();
        return normalizedInput.equals("y");
    }

    public static void skipLines(int lines) {
        System.out.println("\n".repeat(Math.max(0, lines)));
    }


}
