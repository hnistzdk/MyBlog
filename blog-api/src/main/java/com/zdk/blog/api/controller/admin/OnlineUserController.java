package com.zdk.blog.api.controller.admin;

import com.zdk.blog.api.controller.BaseController;
import com.zdk.MyBlog.service.onlineUser.OnlineUserService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author zdk
 * @date 2021/8/13 10:03
 */
@Api("在线用户")
@Controller
@RequestMapping(value = "/admin/onlineUser")
public class OnlineUserController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnlineUserController.class);
    @Autowired
    private OnlineUserService onlineUserService;
}
