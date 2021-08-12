package com.zdk.MyBlog.service.relationships;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zdk.MyBlog.mapper.RelationshipsMapper;
import com.zdk.MyBlog.model.pojo.Relationships;
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
    @Autowired
    private RelationshipsMapper relationshipsMapper;
}
