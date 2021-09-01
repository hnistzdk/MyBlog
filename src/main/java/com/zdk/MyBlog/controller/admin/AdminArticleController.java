package com.zdk.MyBlog.controller.admin;

import com.github.pagehelper.PageInfo;
import com.zdk.MyBlog.constant.Types;
import com.zdk.MyBlog.controller.BaseController;
import com.zdk.MyBlog.model.dto.MetaDto;
import com.zdk.MyBlog.model.pojo.Article;
import com.zdk.MyBlog.service.article.ArticleService;
import com.zdk.MyBlog.service.metas.MetasService;
import com.zdk.MyBlog.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zdk
 * @date 2021/7/22 17:02
 */
@Api("管理端文章接口")
@Controller
@RequestMapping(value = "/admin/article",method = {RequestMethod.POST,RequestMethod.GET})
public class AdminArticleController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminArticleController.class);

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private MetasService metasService;

    @ApiOperation("跳转到发布文章页面")
    @GetMapping(value = "/publish")
    public String index(Model model){
        List<MetaDto> categories = metasService.getMetaList(Types.CATEGORY.getType());
        model.addAttribute("user", getLoginUser());
        model.addAttribute("categories", categories);
        return "admin/article_edit";
    }

    @ApiOperation("跳转到文章列表页面")
    @GetMapping("")
    public String article(Model model,
                          @RequestParam(name = "page",required = false, defaultValue = "1") Integer pageNum,
                          @RequestParam(name = "limit",required = false, defaultValue = "5")Integer pageSize){
        PageInfo<Article> articlePage = articleService.getArticlePage(pageNum, pageSize,getLoginUser());
        model.addAttribute("articles",articlePage);
        return "admin/article_list";
    }

    @ApiOperation("跳转到文章编辑页")
    @GetMapping(value = "/{id}")
    public String toPost2(Model model, @PathVariable Integer id){
        Article article = articleService.getArticleById(id);
        List<MetaDto> categories = metasService.getMetaList(Types.CATEGORY.getType());
        model.addAttribute("article",article);
        model.addAttribute("user",getLoginUser());
        model.addAttribute("categories",categories);
        return "admin/article_edit";
    }
}
