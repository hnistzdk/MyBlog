package com.zdk.blog.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * @author zdk
 * @date 2021/7/22 15:56
 */
public class MyBeanUtil {
    public static <T> T objectToBean(Object object, Class<T> tClass){
        return JSONUtil.toBean(JSONUtil.parseObj(object), tClass);
    }

    public static <T> T jsonObjectToBean(JSONObject object, Class<T> tClass){
        return JSONUtil.toBean(object,tClass);
    }
}
