package com.zdk.MyBlog.controller.user;

import com.zdk.MyBlog.constant.Types;
import com.zdk.MyBlog.controller.BaseController;
import com.zdk.MyBlog.model.dto.MetaDto;
import com.zdk.MyBlog.model.pojo.Article;
import com.zdk.MyBlog.service.article.ArticleService;
import com.zdk.MyBlog.service.metas.MetasService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author zdk
 * @date 2021/8/8 10:23
 */
@Api("用户相关接口")
@Controller
@RequestMapping(value ="/user")
public class UserController extends BaseController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private MetasService metasService;

    @GetMapping(value ="/toLogin")
    public String toLogin(){
        return "blog/login";
    }

    @GetMapping(value = "/toIndex")
    public String toIndex(Model model,
                          @RequestParam(name = "page",required = false, defaultValue = "1") Integer pageNumber,
                          @RequestParam(name = "limit",required = false, defaultValue = "5")Integer pageSize,
                          @RequestParam(name = "keywords",required = false) String keywords,
                          @RequestParam(name = "tag",required = false) String tag){
        model.addAttribute("articles", articleService.getArticlePageByKeywords(pageNumber,pageSize,keywords,tag));
        List<MetaDto> categories = metasService.getMetaList(Types.CATEGORY.getType());
        List<MetaDto> tags = metasService.getMetaList(Types.TAG.getType());
        model.addAttribute("categories",categories);
        model.addAttribute("tags",tags);

        List<Article> latestArticle = articleService.getLatestArticle();
        model.addAttribute("latestArticle",latestArticle);

        List<Article> clickMostArticle = articleService.getClickMostArticle();
        model.addAttribute("clickMostArticle",clickMostArticle);
        return "blog/index";
    }

    @GetMapping(value = "/toAboutMe")
    public String toAboutMe(Model model){
        model.addAttribute("user", getLoginUser());
        return "blog/about";
    }
}
