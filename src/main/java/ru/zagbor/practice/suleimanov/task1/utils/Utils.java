package ru.zagbor.practice.suleimanov.task1.utils;
public class Utils {


    public Utils() {
    }

    public static long parseLong(String s) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }


}
