package com.zdk.MyBlog.utils;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageInfo;
import com.vdurmont.emoji.EmojiParser;
import com.zdk.MyBlog.constant.WebConst;
import com.zdk.MyBlog.model.Article;
import com.zdk.MyBlog.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zdk
 * @date 2021/8/13 9:27
 */
@Component
public class Commons {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private HttpServletRequest request;

    public User getLoginUser(){
        Object user = redisUtil.hget(WebConst.USERINFO,TaleUtils.getCookieValue(WebConst.USERINFO, request));
        return JSONUtil.toBean(JSONUtil.parseObj(user), User.class);
    }

    /**
     * 网站链接
     *
     * @return
     */
    public static String getSiteUrl() {
        return getSiteUrl("");
    }

    /**
     * 返回网站链接下的全址
     *
     * @param sub 后面追加的地址
     * @return
     */
    public static String getSiteUrl(String sub) {
        return getSiteOption("site_url") + sub;
    }


    /**
     * 文件上传，为文件重新命名
     **/
    public static String getFileRename(String name) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        String sdfDate = sdf.format(date);
        int pos = name.lastIndexOf(".");
        String suffix = name.substring(pos);
        String rename = sdfDate+suffix;
        return rename;
    }

    /**
     * 获取网站的描述
     * @return
     */
    public static String getSiteDesc(){
        return getSiteOption("site_description");
    }

    public static String getSiteKeywords(){
        return getSiteOption("site_keywords");
    }

    /**
     * 获取网站的备案信息
     * @return
     */
    public static String getSiteRecord() {
        return getSiteOption("site_record");
    }

    /**
     * 获取网站标题
     * @return
     */
    public static String getSiteTitle() {
        return getSiteOption("site_title");
    }

    /**
     * 网站配置项
     *
     * @param key
     * @return
     */
    public static String getSiteOption(String key) {
        return getSiteOption(key, "");
    }

    /**
     * 获取GitHub地址
     * @return
     */
    public static String getSocialGithub(){
        return getSiteOption("social_github");
    }

    /**
     * 获取google网站验证码
     * @return
     */
    public static String getGoogleSiteVerification(){
        return getSiteOption("google_site_verification");
    }

    /**
     * 获取百度网站验证码
     * @return
     */
    public static String getBaiduSiteVerification(){
        return getSiteOption("baidu_site_verification");
    }
    /**
     * 网站配置项
     *
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public static String getSiteOption(String key, String defaultValue) {
        if (StringUtils.isBlank(key)) {
            return "";
        }
        String str = WebConst.initConfig.get(key);
        if (StringUtils.isNotBlank(str)) {
            return str;
        } else {
            return defaultValue;
        }
    }

    /**
     * 格式化unix时间戳为日期
     *
     * @param unixTime
     * @return
     */
    public static String fmtdate(Integer unixTime) {
        return fmtdate(unixTime, "yyyy-MM-dd");
    }

    /**
     * 格式化unix时间戳为日期
     *
     * @param unixTime
     * @param patten
     * @return
     */
    public static String fmtdate(Integer unixTime, String patten) {
        if (null != unixTime && StringUtils.isNotBlank(patten)) {
            return DateKit.formatDateByUnixTime(unixTime, patten);
        }
        return "";
    }

    /**
     * 英文格式的日期
     * @param unixTime
     * @return
     */
    public static String fmtdate_en(Integer unixTime){
        String fmtdate = fmtdate(unixTime, "d,MMM,yyyy");
        String[] dateArr = fmtdate.split(",");
        String rs = "<span>" + dateArr[0] + "</span> " + dateArr[1] + "  " + dateArr[2];
        return rs;
    }


    /**
     * 英文格式的日期-月，日
     * @param unixTime
     * @return
     */
    public static String fmtdate_en_m(Integer unixTime){
        return fmtdate(unixTime,"MMM d");
    }

    /**
     * 日期-年
     * @param unixTime
     * @return
     */
    public static String fmtdate_en_y(Integer unixTime){
        return fmtdate(unixTime,"yyyy");
    }

    /**
     * 将中文的yyyy年MM月 - > yyyy
     * @param date
     * @return
     */
    public static String parsedate_zh_y_m(String date){
        if (StringUtils.isNotBlank(date)){
            Date d = DateKit.dateFormat(date, "yyyy年MM月");
            return DateKit.dateFormat(d, "yyyy");
        }
        return null;
    }

    /**
     * 字符串转Date
     * @param date
     * @return
     */
    public static Date fmtdate_date(String date){
        if (StringUtils.isNotBlank(date)){
            return DateKit.dateFormat(date, "yyyy年MM月");
        }
        return null;
    }

    /**
     * 根据nuix时间戳获取Date
     * @param nuixTime
     * @return
     */
    public static Date fmtdate_unxtime(Integer nuixTime){
        if (null != nuixTime){
            return DateKit.getDateByUnixTime(nuixTime);
        }
        return  null;
    }
    /**
     * 获取社交的链接地址
     *
     * @return
     */
    public static Map<String, String> social() {
        final String prefix = "social_";
        Map<String, String> map = new HashMap<>();
        map.put("csdn", WebConst.initConfig.get(prefix + "csdn"));
        map.put("jianshu", WebConst.initConfig.get(prefix + "jianshu"));
        map.put("resume", WebConst.initConfig.get(prefix + "resume"));
        map.put("weibo", WebConst.initConfig.get(prefix + "weibo"));
        map.put("zhihu", WebConst.initConfig.get(prefix + "zhihu"));
        map.put("github", WebConst.initConfig.get(prefix + "github"));
        map.put("twitter", WebConst.initConfig.get(prefix + "twitter"));
        return map;
    }


    /**
     * An :grinning:awesome :smiley:string &#128516;with a few :wink:emojis!
     * <p>
     * 这种格式的字符转换为emoji表情
     *
     * @param value
     * @return
     */
    public static String emoji(String value) {
        return EmojiParser.parseToUnicode(value);
    }

    /**
     * 获取随机数
     *
     * @param max
     * @param str
     * @return
     */
    public static String random(int max, String str) {
        return UUID.random(1, max) + str;
    }

    public static String random(Long seed, int max, String str){
        if (seed == null) {
            return random(max, str);
        }
        Random random = new Random(seed);
        return random.nextInt(max) + str;
    }

    /**
     * 如果blog没有配图，随机获取一张
     * @return
     */
    public static String randomBlogPic(Long seed){
        return "/site/images/blog-images/blog-" + random( seed,12,".jpg");
    }

    /**
     * 返回github头像地址
     *
     * @param email
     * @return
     */
    public static String gravatar(String email) {
        String avatarUrl = "https://github.com/identicons/";
        if (StringUtils.isBlank(email)) {
            email = "369365576@qq.com";
        }else{
            if (email.equals("369365576@qq.com")){
                return "https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqxGibq4MoTmFPkjjAxvfJKRpVxgF1ywBMzDB00p8ueAlKYvJvVSE0KfYPTwphN3QzKUlIDyk0GaEQ/132";
            }
        }
        String hash = TaleUtils.MD5encode(email.trim().toLowerCase());
        return avatarUrl + hash + ".png";
    }

    /**
     * 显示文章内容，转换markdown为html
     *
     * @param value
     * @return
     */
//    public static String article(String value) {
//        if (StringUtils.isNotBlank(value)) {
//            value = value.replace("<!--more-->", "\r\n");
//            value = value.replace("<!-- more -->", "\r\n");
//            return TaleUtils.mdToHtml(value);
//        }
//        return "";
//    }

    /**
     * 返回文章链接地址
     *
     * @param article
     * @return
     */
    public static String permalink(Article article) {
        return permalink(article.getId(), article.getSlug());
    }



    /**
     * 返回文章链接地址
     *
     * @param cid
     * @param slug
     * @return
     */
    public static String permalink(Integer cid, String slug) {
        return getSiteUrl("/article/" + (StringUtils.isNotBlank(slug) ? slug : cid.toString()));
    }

    /**
     * 判断分页中是否有数据
     *
     * @param paginator
     * @return
     */
    public static <T> boolean pageIsEmpty(PageInfo<T> paginator) {
        return paginator == null || (paginator.getList() == null) || (paginator.getList().size() == 0);
    }

    /**
     * 截取字符串
     *
     * @param str
     * @param len
     * @return
     */
    public static String substr(String str, int len) {
        return str.substring(len);
    }


    /**
     * 返回作品文章地址
     * @param cid
     * @return
     */
    public static String photoPermalink(Integer cid) {
//        return getSiteUrl("/photo/article/" + cid.toString());
        return getSiteUrl("/article/toPost/" + cid.toString());
    }

    /**
     * 返回blog文章地址
     * @param cid
     * @return
     */
    public static String blogPermalink(Integer cid) {
        return getSiteUrl("/article/toPost/" + cid.toString());
    }

    /**
     * 获取blog归档地址
     * @param date
     * @return
     */
    public static String archivePermalink(String date){
        return getSiteUrl("/blog/archives/" + date);
    }


    public static String archiveYearPermalink(String date){
        return getSiteUrl("/blog/archives/year/" + date);
    }
    /**
     * 返回blog分类的地址
     * @param categories
     * @return
     */
    public static String categoriesPermalink(String categories){
        return getSiteUrl("/blog/categories/" + categories);
    }

    /**
     * 返回blog标签页的地址
     * @param tag
     * @return
     */
    public static String tagPermalink(String tag){
        return getSiteUrl("/blog/tag/" + tag);
    }
    /**
     * 获取文章第一张图片
     *
     * @return
     */
//    public static String getFirstPicture(String content) {
//        content = TaleUtils.mdToHtml(content);
//        if (content.contains("<img")) {
//            String img = "";
//            String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
//            Pattern p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
//            Matcher m_image = p_image.matcher(content);
//            if (m_image.find()) {
//                img = img + "," + m_image.group();
//                // //匹配src
//                Matcher m = Pattern.compile("src\\s*=\\s*\'?\"?(.*?)(\'|\"|>|\\s+)").matcher(img);
//                if (m.find()) {
//                    return m.group(1);
//                }
//            }
//        }
//        return "";
//    }

    /**
     * 获取文章中的所有图片
     * @param content
     * @return
     */
//    public static List<String> getAllPicture(String content) {
//        List<String> rs = new LinkedList();
//        content = TaleUtils.mdToHtml(content);
//        if (content.contains("<img")) {
//            String img = "";
//            String regEx_img = "<[a-zA-Z]+.*?>([\\s\\S]*?)</[a-zA-Z]*>";
//            Pattern p_image = Pattern.compile(regEx_img, Pattern.MULTILINE);
//            Matcher m_image = p_image.matcher(content);
//            while (m_image.find()) {
//                String data = m_image.group(1).trim();
//                if(!"".equals(data) && data.contains("<img")) {
//                    System.out.println(data);
//                    // //匹配src
//                    Matcher m = Pattern.compile("src\\s*=\\s*\'?\"?(.*?)(\'|\"|>|\\s+)").matcher(data);
//                    while (m.find()){
//				 //  if (m.find()) {
//                        rs.add(m.group(1));
//                    }
//                }
//
//            }
//        }
//        return rs;
//    }

    /**
     * 获取文章的文字预览
     * @param content
     * @return
     */
//    public static String getTextPreview(String content){
//        String result = "";
//        content = TaleUtils.mdToHtml(content);
//        String reg = "<[a-zA-Z]+.*?>([\\s\\S]*?)</[a-zA-Z]*>";
//
//        Pattern p = Pattern.compile(reg, Pattern.MULTILINE);
//        content = content.replace("&nbsp;", "");
//        Matcher m = p.matcher(content);
//        if (m.find()) {
//            String data = m.group(1).trim();
//            if(!"".equals(data) && !data.contains("<img")) {
//                System.out.println(data);
//                result = data;
//            }
//        }
//        result = result.replace("<img>","");
//        result = result.replace("</img>","");
//        result = result.replace("<p>","");
//        result = result.replace("</p>","");
//        if (result.length() > 20) {
//            result = result.substring(0, 20);
//        }
//        return result;
//    }

    /**
     * 获取文章中所有的文字
     * @param content
     * @return
     */
//    public static List<String> getAllTextInArticle(String content){
//        List<String> rs = new LinkedList();
//        content = TaleUtils.mdToHtml(content);
//        String reg = "<[a-zA-Z]+.*?>([\\s\\S]*?)</[a-zA-Z]*>";
//
//        Pattern p = Pattern.compile(reg, Pattern.MULTILINE);
//        content = content.replace("&nbsp;", "");
//        Matcher m = p.matcher(content);
//        while(m.find()) {
//            String data = m.group(1).trim();
//            if(!"".equals(data) && !data.contains("<img")) {
//                System.out.println(data);
//                data = "<p>" + data + "</p>";
//                rs.add(data);
//            }
//        }
//        return rs;
//    }

    /**
     * 显示分类
     *
     * @param categories
     * @return
     */
    public static String showCategories(String categories) throws UnsupportedEncodingException {
        if (StringUtils.isNotBlank(categories)) {
            String[] arr = categories.split(",");
            StringBuffer sbuf = new StringBuffer();
            for (String c : arr) {
                sbuf.append("<a class=\"article-category-link\" href=\"/blog/category/" + URLEncoder.encode(c, "UTF-8") + "\">" + c + "</a>");
            }
            return sbuf.toString();
        }
        return showCategories("默认分类");
    }

    /**
     * 显示标签
     *
     * @param tags
     * @return
     */
    public static String showTags(String tags) throws UnsupportedEncodingException {
        if (StringUtils.isNotBlank(tags)) {
            String[] arr = tags.split(",");
            StringBuffer sbuf = new StringBuffer();
            for (String c : arr) {
                sbuf.append("<li class=\"article-tag-list-item\"><a href=\"/blog/tag/" + URLEncoder.encode(c, "UTF-8") + "\">#" + c + "</a></li>");
            }
            return sbuf.toString();
        }
        return "";
    }

    /**
     * 截取文章摘要
     *
     * @param value 文章内容
     * @param len   要截取文字的个数
     * @return
     */
//    public static String getIntro(String value, int len) {
//        Integer pos = value.indexOf("<!--more-->");
//        if (pos == 0 || pos == -1) {
//            pos = value.indexOf("<!-- more -->");
//        }
//        if (pos != -1) {
//            String html = value.substring(0, pos);
//            return TaleUtils.mdToHtml(TaleUtils.mdToHtml(html));
//        } else {
//            String text = TaleUtils.mdToHtml(TaleUtils.mdToHtml(value));
//            if (text.length() > len) {
//                return text.substring(0, len);
//            }
//            return text;
//        }
//    }

    /**
     * 根据后缀判断是不是图片
     * @param suffix
     * @return
     */
    public static Boolean isImage(String suffix){
        List<String> imageList = Arrays.asList("bmp", "dib", "gif",
                "jfif", "jpe", "4", "jpeg",
                "jpg", "png", "7", "tif",
                "tiff", "ico");
        return imageList.contains(suffix);
    }


}
