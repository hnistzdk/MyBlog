package com.zdk.MyBlog.controller.admin;

import com.zdk.MyBlog.controller.BaseController;
import com.zdk.MyBlog.service.onlineUser.OnlineUserService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zdk
 * @date 2021/8/13 10:03
 */
@Api("在线用户")
@Controller
@RequestMapping("/admin/onlineUser",method = {RequestMethod.POST,RequestMethod.GET})
public class OnlineUserController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnlineUserController.class);
    @Autowired
    private OnlineUserService onlineUserService;
}
