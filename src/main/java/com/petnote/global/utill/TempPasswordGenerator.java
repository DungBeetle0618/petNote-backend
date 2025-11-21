package com.petnote.global.utill;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TempPasswordGenerator {

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*";
    private static final String ALL = UPPER + LOWER + DIGITS + SPECIAL;

    private static final SecureRandom random = new SecureRandom();

    /**
     * 임시 비밀번호 생성
     */
    public static String generate() {
        int length = 8;
        List<Character> chars = new ArrayList<>();

        // 각 종류에서 최소 1글자씩 보장
        chars.add(UPPER.charAt(random.nextInt(UPPER.length())));
        chars.add(LOWER.charAt(random.nextInt(LOWER.length())));
        chars.add(DIGITS.charAt(random.nextInt(DIGITS.length())));
        chars.add(SPECIAL.charAt(random.nextInt(SPECIAL.length())));

        // 나머지 글자는 ALL 에서 랜덤
        for (int i = chars.size(); i < length; i++) {
            chars.add(ALL.charAt(random.nextInt(ALL.length())));
        }

        // 섞기
        Collections.shuffle(chars, random);

        StringBuilder sb = new StringBuilder(length);
        for (char c : chars) {
            sb.append(c);
        }
        return sb.toString();
    }
}
