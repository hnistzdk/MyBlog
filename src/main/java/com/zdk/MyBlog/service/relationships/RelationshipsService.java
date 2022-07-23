package com.zdk.MyBlog.service.relationships;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zdk.MyBlog.model.Relationships;

import java.util.List;

/**
 * @author zdk
 * @date 2021/8/12 22:37
 */
public interface RelationshipsService extends IService<Relationships> {
    /**
     * 根据文章id和项目id获取关联信息数量
     * @param articleId
     * @param id 项目id
     * @return
     */
    int getCountByCondition(Integer articleId,Integer id);

    /**
     * 根据文章编号删除其关联的分类和标签
     * @param articleId
     */
    void deleteByArticleId(Integer articleId);

    /**
     * 根据metaid获取关系数据
     * @param metaId
     * @return
     */
    List<Relationships> getByMetaId(Integer metaId);

    /**
     * 根据metaid删除关系数据
     * @param metaId
     * @return
     */
    void deleteByMetaId(Integer metaId);
}
