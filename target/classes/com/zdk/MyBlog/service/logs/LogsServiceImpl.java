package com.zdk.MyBlog.service.logs;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zdk.MyBlog.mapper.LogsMapper;
import com.zdk.MyBlog.model.pojo.Logs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zdk
 * @date 2021/8/12 22:36
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LogsServiceImpl extends ServiceImpl<LogsMapper, Logs> implements LogsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogsServiceImpl.class);
    @Autowired
    private LogsMapper logsMapper;
}
