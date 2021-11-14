package com.zdk.MyBlog.service.metas;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdk.MyBlog.constant.ErrorConstant;
import com.zdk.MyBlog.constant.Types;
import com.zdk.MyBlog.exception.MyGlobalException;
import com.zdk.MyBlog.mapper.MetasMapper;
import com.zdk.MyBlog.model.dto.MetaDto;
import com.zdk.MyBlog.model.dto.cond.MetaCond;
import com.zdk.MyBlog.model.pojo.Article;
import com.zdk.MyBlog.model.pojo.Metas;
import com.zdk.MyBlog.model.pojo.Relationships;
import com.zdk.MyBlog.service.article.ArticleService;
import com.zdk.MyBlog.service.relationships.RelationshipsService;
import com.zdk.MyBlog.utils.ParaValidatorUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Autowired
    private RelationshipsService relationshipsService;
    @Autowired
    private ArticleService articleService;

    @Autowired
    private ParaValidatorUtil paraValidatorUtil;

    @Override
    public void addMetas(Integer articleId, String names, String type) {
        if (paraValidatorUtil.isOk(articleId)){
            if (StringUtils.isNotBlank(names) && StringUtils.isNotBlank(type)) {
                String[] nameArr = StringUtils.split(names, ",");
                for (String name : nameArr) {
                    saveOrUpdate(articleId, name, type);
                }
            }
        }
    }
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
            throw MyGlobalException.withErrorMessage(ErrorConstant.Meta.NOT_ONE_RESULT);
        }else {
            meta = new Metas();
            meta.setSlug(name);
            meta.setName(name);
            meta.setType(type);
            saveOrUpdate(meta);
            id = meta.getId();
        }
        if (id != 0){
            int count = relationshipsService.getCountByCondition(articleId, id);
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
    public List<MetaDto> getMetaList(String type) {
        return metasMapper.getMetaList(type);
    }

    @Override
    public void saveMeta(String type, String name, Integer mid) {
        if (paraValidatorUtil.isOk(type)&& paraValidatorUtil.isOk(name)){
            List<Metas> metas = getMetas(new MetaCond(null,name, type));
            if (metas==null||metas.size()==0){
                Metas meta = new Metas();
                meta.setName(name);
                if(paraValidatorUtil.isOk(mid)){
                    Metas entity = lambdaQuery().eq(Metas::getId, mid).list().get(0);
                    if (entity!=null){
                        entity.setName(name);
                        updateById(entity);
                        articleService.updateByCondition(entity.getName(),name);
                    }
                }else{
                    meta.setType(type);
                    metasMapper.insert(meta);
                }
            }
        }else {
            throw new MyGlobalException(ErrorConstant.Meta.META_IS_EXIST);
        }
    }

    @Override
    public void deleteMeta(String type, String name, Integer mid) {
        if (paraValidatorUtil.notOk(mid)){
            throw new MyGlobalException(ErrorConstant.Common.INVALID_PARAM);
        }
        Metas metas = metasMapper.selectById(mid);
        if (metas!=null){
            //将meta表中相关数据删除
            int delete = metasMapper.deleteById(mid);
            List<Relationships> relationshipsList = relationshipsService.getByMetaId(mid);
            for (Relationships relationships : relationshipsList) {
                Article article = articleService.getArticleById(relationships.getArticleId());
                Article update = new Article().setId(article.getId());
                if (type.equals(Types.CATEGORY.getType())){
                    update.setCategories(reMeta(name, article.getCategories()));
                }else {
                    System.out.println("type = " + type);
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
        List<Metas> list = lambdaQuery().eq(Metas::getType, "link").list();
        return new PageInfo<>(list);
    }
}
