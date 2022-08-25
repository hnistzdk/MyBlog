package com.zdk.blog.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author zdk
 * @Date 2022/8/25 11:06
 */
public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);


    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj.getClass() == String.class) {
            return (String)obj;
        } else {
            try {
                return OBJECT_MAPPER.writeValueAsString(obj);
            } catch (JsonProcessingException var2) {
                logger.error("json序列化出错：" + obj, var2);
                return null;
            }
        }
    }

    public static <T> T toBean(String json, Class<T> tClass) {
        try {
            return OBJECT_MAPPER.readValue(json, tClass);
        } catch (IOException var3) {
            logger.error("json解析出错：" + json, var3);
            return null;
        }
    }

    public static <E> List<E> toList(String json, Class<E> eClass) {
        try {
            return (List)OBJECT_MAPPER.readValue(json, OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, eClass));
        } catch (IOException var3) {
            logger.error("json解析出错：" + json, var3);
            return null;
        }
    }

    public static <K, V> Map<K, V> toMap(String json, Class<K> kClass, Class<V> vClass) {
        try {
            return (Map)OBJECT_MAPPER.readValue(json, OBJECT_MAPPER.getTypeFactory().constructMapType(Map.class, kClass, vClass));
        } catch (IOException var4) {
            logger.error("json解析出错：" + json, var4);
            return null;
        }
    }

    public static <T> T nativeRead(String json, TypeReference<T> type) {
        try {
            return OBJECT_MAPPER.readValue(json, type);
        } catch (IOException var3) {
            logger.error("json解析出错：" + json, var3);
            return null;
        }
    }
}
