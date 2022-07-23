package com.zdk.MyBlog.service.metas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zdk.MyBlog.dto.LinkDTO;
import com.zdk.MyBlog.dto.MetaDTO;
import com.zdk.MyBlog.dto.cond.MetaCond;
import com.zdk.MyBlog.model.Metas;
import com.zdk.MyBlog.utils.ApiResponse;

import java.util.List;

/**
 * @author zdk
 * @date 2021/8/12 22:36
 */
public interface MetasService extends IService<Metas> {
    /**
     * 添加文章分类和标签信息
     * @param articleId
     * @param name
     * @param type
     * @return
     */
    void addMetas(Integer articleId, String name, String type);

    /**
     * 添加文章分类和标签信息
     * @param articleId
     * @param name
     * @param type
     * @return
     */
    void saveOrUpdate(Integer articleId, String name, String type);

    /**
     * 保存分类信息
     * @param type 保存的类型(分类或标签)
     * @param name (分类或标签名)
     * @param mid (项目id)
     * @return
     */
    void saveMeta(String type,String name,Integer mid);

    /**
     * 根据metas查询条件获取metas
     * @param metaCond
     * @return
     */
    List<Metas> getMetas(MetaCond metaCond);


    /**
     * 获取分类、标签及关联的文章数量
     * @param type
     * @return
     */
    List<MetaDTO> getMetaList(String type);

    /**
     * 删除分类信息
     * @param type 保存的类型(分类或标签)
     * @param name (分类或标签名)
     * @param mid (项目id)
     * @return
     */
    void deleteMeta(String type,String name,Integer mid);

    /**
     * 友链分页
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageInfo<Metas> getLinksPage(Integer pageNumber,Integer pageSize);

    /**
     * 获取友链
     * @return
     */
    List<Metas> getLinks();

    /**
     * 添加友链
     * @param link
     * @return
     */
    ApiResponse addLink(LinkDTO link);
}
