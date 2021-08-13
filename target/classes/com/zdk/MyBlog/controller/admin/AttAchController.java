package com.zdk.MyBlog.controller.admin;

import com.zdk.MyBlog.qiniu.QiniuCloudService;
import com.zdk.MyBlog.service.attach.AttachService;
import com.zdk.MyBlog.utils.TaleUtils;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zdk
 * @date 2021/8/13 9:27
 */
@Api("附件")
@Controller
@RequestMapping("/admin/attach")
public class AttAchController {
    public static final String CLASSPATH = TaleUtils.getUplodFilePath();
    private static final Logger LOGGER = LoggerFactory.getLogger(AttAchController.class);
    @Autowired
    private AttachService attachService;
    @Autowired
    private QiniuCloudService qiniuCloudService;
}
