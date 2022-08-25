package com.zdk.blog.utils;

import com.zdk.blog.exception.GlobalException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author zdk
 * @date 2021/8/13 9:27
 */
public class TaleUtils {


    private static final Logger LOGGER = LoggerFactory.getLogger(TaleUtils.class);

    /**
     * 一个月
     */
    private static final int A_MONTH = 30 * 24 * 60 * 60;
    /**
     * 匹配邮箱正则
     */
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static final Pattern SLUG_REGEX = Pattern.compile("^[A-Za-z0-9_-]{5,100}$", Pattern.CASE_INSENSITIVE);
    /**
     * 使用双重检查锁的单例方式需要添加 volatile 关键字
     */
    private static volatile DataSource newDataSource;

    /**
     * 获取文件所在目录
     */
    private static String location = TaleUtils.class.getClassLoader().getResource("").getPath();

    /**
     * 判断是否是邮箱
     *
     * @param emailStr
     * @return
     */
    public static boolean isEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    /**
     * @param fileName 获取jar外部的文件
     * @return 返回属性
     */
    private static Properties getPropFromFile(String fileName) {
        Properties properties = new Properties();
        try {
//            默认是classPath路径
            InputStream resourceAsStream = new FileInputStream(fileName);
            properties.load(resourceAsStream);
        } catch (GlobalException | IOException e) {
            LOGGER.error("get properties file fail={}", e.getMessage());
        }
        return properties;
    }

    /**
     * md5加密
     *
     * @param source 数据源
     * @return 加密字符串
     */
    public static String MD5encode(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] encode = messageDigest.digest(source.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte anEncode : encode) {
                String hex = Integer.toHexString(0xff & anEncode);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException ignored) {
        }
        return "";
    }

    /**
     * 获取新的数据源
     *
     * @return
     */
    public static DataSource getNewDataSource() {
        if (newDataSource == null) {
            synchronized (TaleUtils.class) {
                if (newDataSource == null) {
                    Properties properties = TaleUtils.getPropFromFile("application-default.properties");
                    if (properties.size() == 0) {
                        return newDataSource;
                    }
                    DriverManagerDataSource managerDataSource = new DriverManagerDataSource();
                    managerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
                    managerDataSource.setPassword(properties.getProperty("spring.datasource.password"));
                    String str = "jdbc:mysql://" + properties.getProperty("spring.datasource.url") + "/" + properties.getProperty("spring.datasource.dbname") + "?useUnicode=true&characterEncoding=utf-8&useSSL=false";
                    managerDataSource.setUrl(str);
                    managerDataSource.setUsername(properties.getProperty("spring.datasource.username"));
                    newDataSource = managerDataSource;
                }
            }
        }
        return newDataSource;
    }


    /**
     * 从cookies中获取指定cookie
     *
     * @param name    名称
     * @param request 请求
     * @return cookie
     */
    public static Cookie getCookie(String name, HttpServletRequest request) {
        Cookie[] servletCookies = request.getCookies();
        if (servletCookies == null) {
            return null;
        }
        for (Cookie c : servletCookies) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    /**
     * 从cookies中获取指定cookie的value
     *
     * @param name    名称
     * @param request 请求
     * @return cookie
     */
    public static String getCookieValue(String name, HttpServletRequest request) {
        Cookie[] servletCookies = request.getCookies();
        if (servletCookies == null) {
            return null;
        }
        for (javax.servlet.http.Cookie c : servletCookies) {
            if (c.getName().equals(name)) {
                return c.getValue();
            }
        }
        return null;
    }


    /**
     * 提取html中的文字
     *
     * @param html
     * @return
     */
    public static String htmlToText(String html) {
        if (StringUtils.isNotBlank(html)) {
            return html.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
        }
        return "";
    }


    /**
     * 替换HTML脚本
     *
     * @param value
     * @return
     */
    public static String cleanXSS(String value) {
        //You'll need to remove the spaces from the html entities below
        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        return value;
    }

    /**
     * 过滤XSS注入
     *
     * @param value
     * @return
     */
    public static String filterXSS(String value) {
        String cleanValue = null;
        if (value != null) {
            cleanValue = Normalizer.normalize(value, Normalizer.Form.NFD);
            // Avoid null characters
            cleanValue = cleanValue.replaceAll("\0", "");

            // Avoid anything between script tags
            Pattern scriptPattern;
            scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Avoid anything in a src='...' type of expression
            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Remove any lonesome </script> tag
            scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Remove any lonesome <script ...> tag
            scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Avoid eval(...) expressions
            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Avoid expression(...) expressions
            scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Avoid javascript:... expressions
            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Avoid vbscript:... expressions
            scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Avoid onload= expressions
            scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
        }
        return cleanValue;
    }

    /**
     * 获取保存文件的位置,jar所在目录的路径
     *
     * @return
     */
    public static String getUplodFilePath() {
        String path = TaleUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = path.substring(1, path.length());
        try {
            path = java.net.URLDecoder.decode(path, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int lastIndex = path.lastIndexOf("/") + 1;
        path = path.substring(0, lastIndex);
        File file = new File("");
        return file.getAbsolutePath() + "/";
    }
}
