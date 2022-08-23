package com.zdk.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdk.blog.constant.ErrorConstant;
import com.zdk.blog.constant.Types;
import com.zdk.blog.exception.GlobalException;
import com.zdk.blog.dto.LinkDTO;
import com.zdk.blog.dto.MetaDTO;
import com.zdk.blog.dto.cond.MetaCond;
import com.zdk.blog.model.Article;
import com.zdk.blog.model.Metas;
import com.zdk.blog.model.Relationships;
import com.zdk.blog.service.MetasService;
import com.zdk.blog.utils.ParaValidator;
import com.zdk.blog.mapper.MetasMapper;
import com.zdk.blog.service.ArticleService;
import com.zdk.blog.service.RelationshipsService;
import com.zdk.blog.response.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zdk
 * @date 2021/8/12 22:36
 */
@Service
public class MetasServiceImpl extends ServiceImpl<MetasMapper, Metas> implements MetasService,ParaValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetasServiceImpl.class);

    @Autowired
    private RelationshipsService relationshipsService;
    @Autowired
    private ArticleService articleService;

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    @Override
    public void addMetas(Integer articleId, String names, String type) {
        if (isOk(articleId)){
            if (StringUtils.isNotBlank(names) && StringUtils.isNotBlank(type)) {
                String[] nameArr = StringUtils.split(names, ",");
                for (String name : nameArr) {
                    saveOrUpdate(articleId, name, type);
                }
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    @Override
    public void saveOrUpdate(Integer articleId, String name, String type) {
        MetaCond metaCond = new MetaCond();
        metaCond.setName(name);
        metaCond.setType(type);
        List<Metas> metas = getMetas(metaCond);

        Metas meta;
        int id;
        if(metas.size() == 1){
            meta = metas.get(0);
            id = meta.getId();
        }else if(metas.size()>1){
            throw GlobalException.withErrorMessage(ErrorConstant.Meta.NOT_ONE_RESULT);
        }else {
            meta = new Metas();
            meta.setSlug(name);
            meta.setName(name);
            meta.setType(type);
            saveOrUpdate(meta);
            id = meta.getId();
        }
        if (id != 0){
            long count = relationshipsService.getCountByCondition(articleId, id);
            if (count == 0){
                relationshipsService.save(new Relationships(articleId,id));
            }
        }
    }

    @Override
    public List<Metas> getMetas(MetaCond metaCond) {
        return lambdaQuery().eq(metaCond.getId() != null, Metas::getId,metaCond.getId())
                .eq(metaCond.getName() != null, Metas::getName, metaCond.getName())
                .eq(metaCond.getType() != null, Metas::getType, metaCond.getType())
                .list();
    }

    @Override
    public List<MetaDTO> getMetaList(String type) {
        return baseMapper.getMetaList(type);
    }


    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    @Override
    public void saveMeta(String type, String name, Integer mid) {
        if (isOk(type)&& isOk(name)){
            List<Metas> metas = getMetas(new MetaCond(null,name, type));
            if (metas==null||metas.isEmpty()){
                Metas meta = new Metas();
                meta.setName(name);
                if(isOk(mid)){
                    Metas entity = lambdaQuery().eq(Metas::getId, mid).list().get(0);
                    if (entity!=null){
                        entity.setName(name);
                        updateById(entity);
                        articleService.updateByCondition(entity.getName(),name);
                    }
                }else{
                    meta.setType(type);
                    baseMapper.insert(meta);
                }
            }
        }else {
            throw new GlobalException(ErrorConstant.Meta.META_IS_EXIST);
        }
    }


    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    @Override
    public void deleteMeta(String type, String name, Integer mid) {
        if (notOk(mid)){
            throw new GlobalException(ErrorConstant.Common.INVALID_PARAM);
        }
        Metas metas = baseMapper.selectById(mid);
        if (metas!=null){
            //将meta表中相关数据删除
            baseMapper.deleteById(mid);
            List<Relationships> relationshipsList = relationshipsService.getByMetaId(mid);
            for (Relationships relationships : relationshipsList) {
                Article article = articleService.getArticleById(relationships.getArticleId());
                Article update = new Article().setId(article.getId());
                if (type.equals(Types.CATEGORY.getType())){
                    update.setCategories(reMeta(name, article.getCategories()));
                }else {
                    update.setTags(reMeta(name, article.getTags()));
                }
                articleService.updateById(update);
            }
            //将关系表中meta_id为mid的数据都删除(最后删)
            relationshipsService.deleteByMetaId(mid);
        }
    }


    private String reMeta(String name, String metas) {
        String[] ms = StringUtils.split(metas, ",");
        StringBuilder stringBuilder = new StringBuilder();
        for (String m : ms) {
            if (!name.equals(m)) {
                stringBuilder.append(",").append(m);
            }
        }
        if (stringBuilder.length() > 0) {
            return stringBuilder.substring(1);
        }
        return "";
    }

    @Override
    public PageInfo<Metas> getLinksPage(Integer pageNumber, Integer pageSize) {
        PageHelper.startPage(pageNumber, pageSize, "sort");
        List<Metas> list = lambdaQuery().eq(Metas::getType, Types.LINK.getType()).list();
        return new PageInfo<>(list);
    }


    @Override
    public List<Metas> getLinks() {
        return lambdaQuery().eq(Metas::getType, Types.LINK.getType()).list();
    }


    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    @Override
    public ApiResponse addLink(LinkDTO link) {
        if (link == null){
            return ApiResponse.fail(ErrorConstant.Common.PARAM_IS_EMPTY);
        }
        Metas metas = new Metas();
        metas.setName(link.getTitle());
        metas.setSlug(link.getUrl());
        metas.setDescription(link.getDescription());
        metas.setSort(link.getSort());
        metas.setType(Types.LINK.getType());
        if (link.getId() != null){
            metas.setId(link.getId());
        }
        return ApiResponse.result(saveOrUpdate(metas), ErrorConstant.Common.ADD_FAIL);
    }
}
