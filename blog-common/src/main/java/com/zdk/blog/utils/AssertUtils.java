package com.zdk.blog.utils;

import com.zdk.blog.exception.BusinessException;
import io.jsonwebtoken.lang.Collections;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * @Description
 * @Author zdk
 * @Date 2022/8/25 15:18
 * 断言工具类
 */
public class AssertUtils {
    public static void notEmpty(String param){
        if (StringUtils.isEmpty(param)){
            throw new BusinessException("10002", "param is empty or null");
        }
    }

    public static void notEmpty(Collection collection){
        if (Collections.isEmpty(collection)){
            throw new BusinessException("10002", "collection is empty or null");
        }
    }

    public static void notNull(Object object){
        if (Objects.isNull(object)){
            throw new BusinessException("10002", "object is null");
        }
    }

}
