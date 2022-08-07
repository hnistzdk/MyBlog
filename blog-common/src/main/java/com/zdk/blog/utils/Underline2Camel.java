package com.zdk.blog.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author zdk
 * @Date 2022/8/7 13:30
 * 下划线转驼峰工具类
 */
public class Underline2Camel {

    private static Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
    private static Pattern pattern1 = Pattern.compile("[A-Z]([a-z\\d]+)?");

    public static String underline2Camel(String line, boolean smallCamel) {
        if (line != null && !"".equals(line)) {

            StringBuilder sb = new StringBuilder();
            Matcher matcher = pattern.matcher(line);

            while(matcher.find()) {
                String word = matcher.group();
                sb.append(smallCamel && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0)) : Character.toUpperCase(word.charAt(0)));
                int index = word.lastIndexOf(95);
                if (index > 0) {
                    sb.append(word.substring(1, index).toLowerCase());
                } else {
                    sb.append(word.substring(1).toLowerCase());
                }
            }

            return sb.toString();
        } else {
            return "";
        }
    }

    public static String camel2Underline(String line) {
        if (line != null && !"".equals(line)) {
            line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
            StringBuilder sb = new StringBuilder();
            Matcher matcher = pattern1.matcher(line);

            while(matcher.find()) {
                String word = matcher.group();
                sb.append(word.toUpperCase());
                sb.append(matcher.end() == line.length() ? "" : "_");
            }

            return sb.toString();
        } else {
            return "";
        }
    }
}
