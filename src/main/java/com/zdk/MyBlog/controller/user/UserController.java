package com.zdk.MyBlog.controller.user;

import com.zdk.MyBlog.controller.BaseController;
import com.zdk.MyBlog.model.pojo.Article;
import com.zdk.MyBlog.service.article.ArticleService;
import com.zdk.MyBlog.service.user.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author zdk
 * @date 2021/8/8 10:23
 */
@Api("用户相关接口")
@Controller
@RequestMapping(value ="/user",method = {RequestMethod.POST,RequestMethod.GET})
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;

    @GetMapping(value ="/toLogin")
    public String toLogin(){
        return "blog/login";
    }

    @GetMapping(value = "/toIndex")
    public String toIndex(Model model){
        model.addAttribute("user", getLoginUser());
        List<Article> articles = articleService.getAllArticle();
        model.addAttribute("articles",articles);
        return "blog/index";
    }

    @GetMapping(value = "/toAboutMe")
    public String toAboutMe(Model model){
        model.addAttribute("user", getLoginUser());
        return "blog/about";
    }
}
