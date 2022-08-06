package com.zdk.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdk.blog.model.Logs;
import com.zdk.blog.model.User;
import com.zdk.blog.service.LogsService;
import com.zdk.blog.utils.ParaValidator;
import com.zdk.blog.mapper.LogsMapper;
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
public class LogsServiceImpl extends ServiceImpl<LogsMapper, Logs> implements LogsService, ParaValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogsServiceImpl.class);


    @Override
    public List<Logs> getLogByLoginUser(User loginUser) {
        return lambdaQuery().eq(Logs::getAuthorId, loginUser.getId()).list();
    }

    @Override
    public PageInfo<Logs> getLogPageByLoginUser(User loginUser) {
        PageHelper.startPage(1, 6);
        return new PageInfo<>(lambdaQuery().eq(Logs::getAuthorId, loginUser.getId()).orderByDesc(Logs::getCreateTime).list());
    }
}
