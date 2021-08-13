package com.zdk.MyBlog.service.attach;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zdk.MyBlog.mapper.AttachMapper;
import com.zdk.MyBlog.model.pojo.Attach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @author zdk
 * @date 2021/8/12 22:35
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AttachServiceImpl extends ServiceImpl<AttachMapper, Attach> implements AttachService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttachServiceImpl.class);
    @Autowired
    private AttachMapper attachMapper;

    @Override
    public List<Attach> list() {
        return AttachService.super.list();
    }

    @Override
    public Attach getOne(Wrapper<Attach> queryWrapper) {
        return AttachService.super.getOne(queryWrapper);
    }

    @Override
    public Attach getById(Serializable id) {
        return AttachService.super.getById(id);
    }

    @Override
    public int count() {
        return AttachService.super.count();
    }

    @Override
    public boolean removeById(Serializable id) {
        return AttachService.super.removeById(id);
    }

    @Override
    public boolean save(Attach entity) {
        return AttachService.super.save(entity);
    }
}
