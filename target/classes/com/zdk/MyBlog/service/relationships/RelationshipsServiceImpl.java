package com.zdk.MyBlog.service.relationships;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zdk.MyBlog.mapper.RelationshipsMapper;
import com.zdk.MyBlog.model.pojo.Relationships;
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
public class RelationshipsServiceImpl extends ServiceImpl<RelationshipsMapper, Relationships> implements RelationshipsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RelationshipsServiceImpl.class);
    @Autowired
    private RelationshipsMapper relationshipsMapper;
}
