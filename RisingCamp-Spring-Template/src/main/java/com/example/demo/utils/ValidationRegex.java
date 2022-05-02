package com.example.demo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationRegex {
    // 이메일 형식 체크
    public static boolean isRegexEmail(String target) {
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    // 날짜 형식, 전화 번호 형식 등 여러 Regex 인터넷에 검색하면 나옴.
}

