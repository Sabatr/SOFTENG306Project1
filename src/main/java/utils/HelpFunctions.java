package utils;

public class HelpFunctions {
    public static boolean isStringNumericAndPositive(String str) {
        try {
            if (Integer.parseInt(str) > 0) return true;
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return false;
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().startsWith("windows");
    }

    public static String createOutputFileName(String input, String DEFAULT_OUTPUT) {
        if (input.contains("/") || input.contains("\\")) { // This removes the folder part of the file path
            if (!isWindows()) {
                return input.substring(input.lastIndexOf('/') + 1, input.lastIndexOf('.')) + "-" + DEFAULT_OUTPUT;
            } else {
                return input.substring(input.lastIndexOf('\\') + 1, input.lastIndexOf('.')) + "-" + DEFAULT_OUTPUT;
            }
        } else {
            return input.substring(0, input.lastIndexOf('.')) + "-" + DEFAULT_OUTPUT;
        }
    }
}
