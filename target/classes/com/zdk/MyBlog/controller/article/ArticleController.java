package com.zdk.MyBlog.controller.article;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.upyun.Result;
import com.zdk.MyBlog.constant.Types;
import com.zdk.MyBlog.controller.BaseController;
import com.zdk.MyBlog.model.dto.MetaDto;
import com.zdk.MyBlog.model.pojo.Article;
import com.zdk.MyBlog.model.pojo.User;
import com.zdk.MyBlog.service.article.ArticleService;
import com.zdk.MyBlog.service.metas.MetasService;
import com.zdk.MyBlog.utils.ApiResponse;
import com.zdk.MyBlog.utils.UpYunUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author zdk
 * @date 2021/7/22 17:02
 */
@Api("博客端文章接口")
@Controller
@RequestMapping(value = "/article",method = {RequestMethod.POST,RequestMethod.GET})
public class ArticleController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);
    private static final String URL_PREFIX = "http://zdk-blog-image.test.upcdn.net";

    @Autowired
    ArticleService articleService;
    @Autowired
    MetasService metasService;

    @ApiOperation("进入博客")
    @GetMapping(value = "/toPost")
    public String toPost(Model model, Integer id){
        Article article = articleService.getArticleById(id);
        articleService.updateById(article.setReadCount(article.getReadCount()+1));
        model.addAttribute("article",article);
        model.addAttribute("user",getLoginUser());
        return "blog/blog-post";
    }

    @ApiOperation("进入博客详情页")
    @GetMapping(value = "/toPost/{id}")
    public String toPost2(Model model, @PathVariable Integer id){
        Article article = articleService.getArticleById(id);
        articleService.updateById(article.setReadCount(article.getReadCount()+1));
        model.addAttribute("article",article);
        model.addAttribute("user",getLoginUser());
        return "blog/blog-post";
    }

    @ApiOperation("进入博客列表")
    @GetMapping(value = "/toBlogList")
    public String blogList(Model model){
        model.addAttribute("user", getLoginUser());
        model.addAttribute("articles", articleService.getAllArticle());
        return "blog/blog-list";
    }

    @ApiOperation("进入发布文章页面")
    @GetMapping(value = "/toWriteBlog")
    public String toWriteBlog(Model model){
        List<MetaDto> categories = metasService.getMetaList(Types.CATEGORY.getType());
        model.addAttribute("user", getLoginUser());
        model.addAttribute("categories", categories);
        return "blog/writeBlog";
    }

    @ApiOperation("发布文章")
    @PostMapping(value = "/addArticle")
    @ResponseBody
    public ApiResponse addArticle(Article article){
        User loginUser = getLoginUser();
        article.setAuthorId(loginUser.getId()).setAuthorName(loginUser.getNickname());
        //去除文章储存时的多余逗号
        article.setContent(article.getContent().replaceAll("^,", ""));
        article.setCategories(article.getCategories().replaceAll("^,", ""));
        if(articleService.addArticle(article)){
            metasService.addMetas(article.getId(),article.getCategories(), Types.CATEGORY.getType());
            metasService.addMetas(article.getId(),article.getTags(), Types.TAG.getType());
            return ApiResponse.success("发布成功");
        }
        return ApiResponse.fail("发布失败");
    }

    @ApiOperation("修改文章")
    @PostMapping("/modify")
    @ResponseBody
    public ApiResponse modifyArticle(Article article){
        //去除文章储存时的多余逗号
        article.setContent(article.getContent().replaceAll("^,", ""));
        article.setCategories(article.getCategories().replaceAll("^,", ""));
        boolean update = articleService.modifyArticle(article);
        if(update){
            return ApiResponse.success("保存成功");
        }
        return ApiResponse.fail("保存失败");
    }

    @ApiOperation("删除文章")
    @GetMapping("/delete")
    @ResponseBody
    public ApiResponse deleteArticle(Integer id){
        Boolean result = articleService.deleteArticleById(id);
        if(result){
            return ApiResponse.success("删除成功");
        }
        return ApiResponse.fail("删除失败");
    }

    @ApiOperation("图片上传")
    @PostMapping(value = "/file/imageUpload")
    @ResponseBody
    public Map<String, Object> imageUpload(@RequestParam(value = "editormd-image-file") MultipartFile file) throws URISyntaxException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, IOException {
        request.setCharacterEncoding( "utf-8" );
        response.setHeader( "Content-Type" , "text/html" );
        //上传的文件名
        String filename=file.getOriginalFilename();
        //获取文件名的后缀
        String suffixName=filename.substring(filename.lastIndexOf("."));
        filename=UUID.randomUUID()+suffixName;

        //上传又拍云
        Result uploadResult = UpYunUtil.uploadImage(file.getBytes(), filename);
        JSONObject photoMsg = JSONUtil.parseObj(uploadResult.getMsg());
        String url=URL_PREFIX+photoMsg.getStr("url");

        HashMap<String, Object> result = new HashMap<>(3);
        result.put("success", 1);
        result.put("message", "上传成功");
        result.put("link", url);
        result.put("url", url);
        return result;
    }
}
