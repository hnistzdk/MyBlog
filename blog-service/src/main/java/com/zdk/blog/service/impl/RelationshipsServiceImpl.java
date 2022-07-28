package com.zdk.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zdk.blog.model.Relationships;
import com.zdk.blog.service.RelationshipsService;
import com.zdk.blog.utils.ParaValidator;
import com.zdk.blog.mapper.RelationshipsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zdk
 * @date 2021/8/12 22:37
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RelationshipsServiceImpl extends ServiceImpl<RelationshipsMapper, Relationships> implements RelationshipsService,ParaValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(RelationshipsServiceImpl.class);
    @Autowired
    private RelationshipsMapper relationshipsMapper;

    @Override
    public int getCountByCondition(Integer articleId, Integer id) {
        return lambdaQuery().eq(Relationships::getArticleId, articleId)
                .eq(Relationships::getMetaId, id).count();
    }

    @Override
    public void deleteByArticleId(Integer articleId) {
        UpdateWrapper<Relationships> wrapper = new UpdateWrapper<>();
        wrapper.eq("article_id", articleId);
        relationshipsMapper.delete(wrapper);
    }

    @Override
    public List<Relationships> getByMetaId(Integer metaId) {
        if (isOk(metaId)){
            return lambdaQuery().eq(Relationships::getMetaId,metaId).list();
        }
        return null;
    }

    @Override
    public void deleteByMetaId(Integer metaId) {
        if (isOk(metaId)){
            relationshipsMapper.delete(new QueryWrapper<Relationships>().eq("meta_id", metaId));
        }
    }
}
