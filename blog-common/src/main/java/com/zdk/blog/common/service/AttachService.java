package com.zdk.blog.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.upyun.UpException;
import com.zdk.blog.common.model.Attach;
import com.zdk.blog.common.model.User;

import java.io.IOException;
import java.util.List;

/**
 * @author zdk
 * @date 2021/8/12 22:35
 */
public interface AttachService extends IService<Attach> {

    /**
     * 获取附件信息分页
     * @param pageNum
     * @param pageSize
     * @param loginUser
     * @return
     */
    PageInfo<Attach> getAttachPage(Integer pageNum, Integer pageSize, User loginUser);

    /**
     * 批量保存文件信息
     * @param attaches
     * @return
     */
    Boolean batchSave(List<Attach> attaches);

    /**
     * 根据id删除文件或图片
     * @param id
     * @return
     * @throws UpException
     * @throws IOException
     */
    Boolean deleteAttachById(Integer id) throws UpException, IOException;


    /**
     * 根据登录用户获取上传的文件
     * @param loginUser
     * @return
     */
    List<Attach> getAttachesByUser(User loginUser);
}
