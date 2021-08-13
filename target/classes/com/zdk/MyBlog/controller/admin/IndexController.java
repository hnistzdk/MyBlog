package com.zdk.MyBlog.controller.admin;

import com.zdk.MyBlog.controller.BaseController;
import com.zdk.MyBlog.model.dto.StatisticsDto;
import com.zdk.MyBlog.model.pojo.Article;
import com.zdk.MyBlog.model.pojo.Comments;
import com.zdk.MyBlog.model.pojo.Logs;
import com.zdk.MyBlog.service.article.ArticleService;
import com.zdk.MyBlog.service.comments.CommentsService;
import com.zdk.MyBlog.service.logs.LogsService;
import com.zdk.MyBlog.utils.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author zdk
 * @date 2021/8/12 20:53
 */
@Api("后台首页")
@Controller
@RequestMapping("/admin")
public class IndexController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentsService commentsService;
    @Autowired
    private LogsService logsService;

    @ApiOperation("进入首页")
    @GetMapping("/index")
    public String index(Model model){

        //statistics
        StatisticsDto statistics = new StatisticsDto(1L, 2L, 3L,4L);
        //articles
        List<Article> articles = articleService.getAllArticle();
        //comments
        List<Comments> comments = commentsService.list();
        //logs
        List<Logs> logs = logsService.list();

        model.addAttribute("statistics",statistics);
        model.addAttribute("articles",articles);
        model.addAttribute("comments",comments);
        model.addAttribute("logs",logs);
        return "admin/index";
    }
    /**
     * 个人设置页面
     */
    @GetMapping(value = "/profile")
    public String profile() {
        return "admin/profile";
    }

    /**
     * 保存个人信息
     */
    @PostMapping(value = "/profile")
    @ResponseBody
    public ApiResponse saveProfile(@RequestParam String screenName, @RequestParam String email, HttpServletRequest request, HttpSession session) {
        return null;
    }

    /**
     * 修改密码
     */
    @PostMapping(value = "/password")
    @ResponseBody
    public ApiResponse modifyPassword() {
        return null;
    }

}
