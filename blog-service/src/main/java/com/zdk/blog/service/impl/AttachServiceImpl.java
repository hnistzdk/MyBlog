package com.zdk.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.upyun.UpException;
import com.zdk.blog.constant.RoleConst;
import com.zdk.blog.model.Attach;
import com.zdk.blog.model.User;
import com.zdk.blog.service.AttachService;
import com.zdk.blog.utils.ParaValidator;
import com.zdk.blog.mapper.AttachMapper;
import com.zdk.starter.service.UpYunOssService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author zdk
 * @date 2021/8/12 22:35
 */
@Service
public class AttachServiceImpl extends ServiceImpl<AttachMapper, Attach> implements AttachService, ParaValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttachServiceImpl.class);

    @Autowired
    private UpYunOssService upYunOssService;

    @Cacheable(value = "attach",key = "'attachPage'+#pageNum+#pageSize+#loginUser")
    @Override
    public PageInfo<Attach> getAttachPage(Integer pageNum, Integer pageSize, User loginUser) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(lambdaQuery().eq(!Objects.equals(loginUser.getRole(), RoleConst.ADMIN),Attach::getAuthorId, loginUser.getId()).orderByDesc(Attach::getCreateTime).list());
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    @CachePut(value = "attach",key = "'attach'+#attaches")
    @Override
    public Boolean batchSave(List<Attach> attaches) {
        return saveBatch(attaches);
    }


    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    @CacheEvict(value = "attach",key = "'attach'+#id",condition = "#id!=null")
    @Override
    public Boolean deleteAttachById(Integer id) throws UpException, IOException {
        Attach attach = getById(id);
        Boolean result = upYunOssService.deleteFile(attach.getFileKey());
        Boolean result1 = remove(new QueryWrapper<Attach>().eq("id", id));
        return result&&result1;
    }


    @Cacheable(value = "attach",key = "'attachs'+#loginUser")
    @Override
    public List<Attach> getAttachesByUser(User loginUser) {
        return lambdaQuery().eq(!loginUser.getRole().equals(RoleConst.ADMIN),Attach::getAuthorId,loginUser.getId()).list();
    }
}
