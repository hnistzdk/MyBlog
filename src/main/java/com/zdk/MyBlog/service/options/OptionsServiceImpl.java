package com.zdk.MyBlog.service.options;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zdk.MyBlog.mapper.OptionsMapper;
import com.zdk.MyBlog.model.pojo.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zdk
 * @date 2021/8/12 22:37
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OptionsServiceImpl extends ServiceImpl<OptionsMapper, Options> implements OptionsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OptionsServiceImpl.class);
    @Autowired
    private OptionsMapper optionsMapper;
}