package com.zdk.MyBlog.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zdk
 * @date 2021/7/23 10:47
 */
public class ControllerKit {


    public static String getRawData(HttpServletRequest request){
        String rawData = HttpKit.readData(request);
        return rawData;
    }
    /**
     * 获取Json数据
     * @return
     */
    public static JSONObject getJSONObject(HttpServletRequest request) {
        String json=getRawData(request);
        if(StringUtils.isBlank(json)) {return null;}
        return JSON.parseObject(json);
    }
    /**
     * 获取Json数据转为JSonArray
     * @return
     */
    public static JSONArray getJSONArray(HttpServletRequest request) {
        String json=getRawData(request);
        if(StringUtils.isBlank(json)) {return null;}
        return JSON.parseArray(json);
    }

    /**
     * 获取Json数据转为JSonArray
     * @return
     */
    public static List<JSONObject> getJSONObjectList(HttpServletRequest request) {
        String json=getRawData(request);
        if(StringUtils.isBlank(json)) {return null;}
        JSONArray jsonArray=JSON.parseArray(json);
        if(jsonArray==null) {return null;}

        return jsonArrayToObjectList(jsonArray);
    }
    /**
     * 将jsonarray转为List<JSONObject>
     * @param jsonArray
     * @return
     */
    private static List<JSONObject> jsonArrayToObjectList(JSONArray jsonArray) {
        int size=jsonArray.size();
        List<JSONObject> jsonObjects=new ArrayList<JSONObject>();
        for (int i = 0; i < size; i++) {
            jsonObjects.add(jsonArray.getJSONObject(i));
        }
        return jsonObjects;
    }
    /**
     * 获取Json数据 转为Java List
     * @return
     */
    public static <T> List<T> getJSONList(HttpServletRequest request,Class<T> clazz) {
        String json=getRawData(request);
        if(StringUtils.isBlank(json)) {return null;}
        return JSON.parseArray(json, clazz);
    }
    /**
     * 根据key获取json数据 转为JSONObject
     * @param key
     * @return
     */
    public static JSONObject getJSONObject(HttpServletRequest request, String key) {
        if(StringUtils.isBlank(key)) {return null;}
        JSONObject jsonObject=getJSONObject(request);
        if(jsonObject==null) {return null;}
        return jsonObject.getJSONObject(key);
    }

    /**
     * 根据key获取json数据 转为JSONArray
     * @param key
     * @return
     */
    public static JSONArray getJSONArray(HttpServletRequest request,String key) {
        if(StringUtils.isBlank(key)) {return null;}
        JSONObject jsonObject=getJSONObject(request);
        if(jsonObject==null) {return null;}
        return jsonObject.getJSONArray(key);
    }
    /**
     * 根据key获取json数据 转为List<JSONObject>
     * @param key
     * @return
     */
    public static List<JSONObject> getJSONObjectList(HttpServletRequest request, String key) {
        if(StringUtils.isBlank(key)) {return null;}
        JSONArray jsonArray=getJSONArray(request,key);
        if(jsonArray==null) {return null;}
        return jsonArrayToObjectList(jsonArray);
    }
    /**
     * 根据key获取json数据 转为java List
     * @param key
     * @return
     */
    public static <T> List<T> getJSONList(HttpServletRequest request,String key,Class<T> clazz) {
        if(StringUtils.isBlank(key)) {return null;}
        JSONArray jsonArray=getJSONArray(request,key);
        if(jsonArray==null) {return null;}
        return jsonArray.toJavaList(clazz);
    }
}
