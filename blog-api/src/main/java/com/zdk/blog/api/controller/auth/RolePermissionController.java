package com.zdk.blog.api.controller.auth;


import com.zdk.blog.api.controller.CommonController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zdk
 * @since 2021-10-28
 */
@RestController
@RequestMapping(value = "/genCode/role-permission")
public class RolePermissionController extends CommonController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RolePermissionController.class);

}

