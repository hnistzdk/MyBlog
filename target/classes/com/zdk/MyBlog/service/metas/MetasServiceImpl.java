package com.zdk.MyBlog.service.metas;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zdk.MyBlog.mapper.MetasMapper;
import com.zdk.MyBlog.model.pojo.Metas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zdk
 * @date 2021/8/12 22:36
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MetasServiceImpl extends ServiceImpl<MetasMapper, Metas> implements MetasService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MetasServiceImpl.class);
    @Autowired
    private MetasMapper metasMapper;
}
