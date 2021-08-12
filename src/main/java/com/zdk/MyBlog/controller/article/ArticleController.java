package com.zdk.MyBlog.controller.article;

import com.zdk.MyBlog.controller.BaseController;
import com.zdk.MyBlog.model.pojo.Article;
import com.zdk.MyBlog.model.pojo.User;
import com.zdk.MyBlog.service.article.ArticleService;
import com.zdk.MyBlog.utils.ApiResponse;
import com.zdk.MyBlog.utils.RedisUtil;
import com.zdk.MyBlog.utils.UploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author zdk
 * @date 2021/7/22 17:02
 */
@Slf4j
@Controller
@RequestMapping(value = "/article",method = {RequestMethod.POST,RequestMethod.GET})
public class ArticleController extends BaseController {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    ArticleService articleService;

    @GetMapping(value = "/toPost")
    public String toPost(Model model, Integer id){
        Article article = articleService.getArticleById(id);
        articleService.updateById(article.setReadCount(article.getReadCount()+1));
        model.addAttribute("article",article);
        model.addAttribute("user",getLoginUser());
        return "blog/blog-post";
    }

    @GetMapping(value = "/toBlogList")
    public String blogList(Model model){
        model.addAttribute("user", getLoginUser());
        model.addAttribute("articles", articleService.getAllArticle());
        return "blog/blog-list";
    }

    @GetMapping(value = "/toWriteBlog")
    public String toWriteBlog(Model model){
        model.addAttribute("user", getLoginUser());
        return "blog/writeBlog";
    }

    @PostMapping(value = "/addArticle")
    @ResponseBody
    public ApiResponse addArticle(Article article){
        User loginUser = getLoginUser();
        article.setUserId(loginUser.getUsername()).setAuthorName(loginUser.getNickname());
        if(articleService.addArticle(article)){
            return ApiResponse.success("成功");
        }
        return ApiResponse.fail("失败");
    }

    @PostMapping(value = "/file/upload")
    @ResponseBody
    public Map<String, Object> imageUpload(@RequestParam(value = "editormd-image-file") MultipartFile file) throws URISyntaxException {
        String filename = UUID.randomUUID()+file.getOriginalFilename();
        File fileDir = UploadUtils.getImgDirFile();
        System.out.println("fileDir.getAbsolutePath() = " + fileDir.getAbsolutePath());
        try {
            File newFile = new File(fileDir.getAbsolutePath() + File.separator + filename);
            System.out.println("newFile.getAbsolutePath() = " + newFile.getAbsolutePath());
            file.transferTo(newFile);
        }catch (IOException e){
            e.printStackTrace();
        }
        HashMap<String, Object> result = new HashMap<>(3);
        result.put("success", 1);
        result.put("message", "上传成功");
        //通过以下URL即可访问到图片
        //http://localhost:8088/static/upload/image/fb2117c8-9227-4e33-8da9-af73e3d8900e%E6%90%AD%E9%85%8D.png
        result.put("link", "http://localhost:8088/static/upload/image/"+filename);
        result.put("url", "/static/upload/image/"+filename);
        return result;
    }
}
