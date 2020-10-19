package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utils {

    private final static BufferedReader BUFFERED_READER = new BufferedReader(new InputStreamReader(System.in));

    public Utils() throws IOException {
    }

    public static long parseLong(String s) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static boolean isExit(String s) {
        return s.equals("e");
    }

    public static String inputName(String message) throws IOException {
        System.out.println(message);
        String name = BUFFERED_READER.readLine();
        return name;
    }



}
