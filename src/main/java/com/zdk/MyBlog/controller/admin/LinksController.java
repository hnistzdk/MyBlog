package com.zdk.MyBlog.controller.admin;

import com.zdk.MyBlog.controller.BaseController;
import com.zdk.MyBlog.service.metas.MetasService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zdk
 * @date 2021/8/13 10:01
 */
@Api("友情链接")
@Controller
@RequestMapping("/admin/links",method = {RequestMethod.POST,RequestMethod.GET})
public class LinksController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LinksController.class);
    @Autowired
    private MetasService metasService;
}
