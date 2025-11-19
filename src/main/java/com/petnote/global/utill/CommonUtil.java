package com.petnote.global.utill;

public class CommonUtil {

    public static String randomUpper6() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt((int) (Math.random() * 26)));
        }
        return sb.toString();
    }

    public static <T> T nvl(T a, T b){
        return a == null ? b : a;
    }
}
