package com.zdk.MyBlog.service.attach;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zdk.MyBlog.model.pojo.Attach;

import java.io.Serializable;
import java.util.List;

/**
 * @author zdk
 * @date 2021/8/12 22:35
 */
public interface AttachService extends IService<Attach> {
    /**
     * 附件列表
     * @return
     */
    @Override
    default List<Attach> list() {
        return IService.super.list();
    }

    /**
     * 获取一个附件
     * @param queryWrapper
     * @return
     */
    @Override
    default Attach getOne(Wrapper<Attach> queryWrapper) {
        return IService.super.getOne(queryWrapper);
    }

    /**
     * 根据id获取一个附件信息
     * @param id
     * @return
     */
    @Override
    default Attach getById(Serializable id) {
        return IService.super.getById(id);
    }

    /**
     * 获取附件总数
     * @return
     */
    @Override
    default int count() {
        return IService.super.count();
    }

    /**
     * 根据附件id删除附件
     * @param id
     * @return
     */
    @Override
    default boolean removeById(Serializable id) {
        return IService.super.removeById(id);
    }

    /**
     * 添加附件
     * @param entity
     * @return
     */
    @Override
    default boolean save(Attach entity) {
        return IService.super.save(entity);
    }
}
