package com.zdk.blog.api.controller.admin;

import com.github.pagehelper.PageInfo;
import com.zdk.blog.api.controller.CommonController;
import com.zdk.blog.model.Comments;
import com.zdk.blog.service.CommentsService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author zdk
 * @date 2021/8/13 9:59
 */
@Api(tags = {"评论相关接口"})
@Controller
@RequestMapping(value = "/admin/comments")
public class CommentsController extends CommonController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommentsService commentsService;

    @GetMapping(value = "")
    public String index(Model model,
                        @RequestParam(name = "page",required = false, defaultValue = "1") Integer pageNum,
                        @RequestParam(name = "limit",required = false, defaultValue = "5")Integer pageSize){
        PageInfo<Comments> comments = commentsService.getCommentsPage(pageNum, pageSize,getLoginUser());
        model.addAttribute("comments", comments);
        return "admin/comment_list";
    }
}
