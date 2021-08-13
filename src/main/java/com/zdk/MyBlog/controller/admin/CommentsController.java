package com.zdk.MyBlog.controller.admin;

import com.zdk.MyBlog.controller.BaseController;
import com.zdk.MyBlog.service.comments.CommentsService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zdk
 * @date 2021/8/13 9:59
 */
@Api("评论")
@Controller
@RequestMapping("/admin/comments")
public class CommentsController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentsController.class);
    @Autowired
    private CommentsService commentsService;
}
