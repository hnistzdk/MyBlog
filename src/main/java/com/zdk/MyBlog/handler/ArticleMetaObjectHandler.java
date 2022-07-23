package com.zdk.MyBlog.handler;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @author zdk
 * @date 2021/8/11 20:22
 * mybatis-plus插入填充处理类
 */
@Slf4j
@Component
public class ArticleMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("插入时开始填充--------");
        setFieldValByName("publishTime", DateUtil.now(),metaObject);
        setFieldValByName("updateTime",DateUtil.now(),metaObject);
        setFieldValByName("createTime",DateUtil.now(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("更新时开始填充--------");
        setFieldValByName("updateTime",DateUtil.now(),metaObject);
    }
}
