package com.zdk.MyBlog.service.logs;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zdk.MyBlog.model.pojo.Logs;
import com.zdk.MyBlog.model.pojo.User;

import java.util.List;

/**
 * @author zdk
 * @date 2021/8/12 22:36
 */
public interface LogsService extends IService<Logs> {
    /**
     * 获取当前登录用户的日志
     * @param loginUser
     * @return
     */
    List<Logs> getLogByLoginUser(User loginUser);

    /**
     * 分页获取当前登录用户日志
     * @param loginUser
     * @return
     */
    PageInfo<Logs> getLogPageByLoginUser(User loginUser);
}
