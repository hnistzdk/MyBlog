package com.zdk.MyBlog.controller.article;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.zdk.MyBlog.controller.BaseController;
import com.zdk.MyBlog.model.pojo.Article;
import com.zdk.MyBlog.model.pojo.User;
import com.zdk.MyBlog.service.article.ArticleService;
import com.zdk.MyBlog.utils.ApiResponse;
import com.zdk.MyBlog.utils.FileUtil;
import com.zdk.MyBlog.utils.HttpKit;
import com.zdk.MyBlog.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author zdk
 * @date 2021/7/22 17:02
 */
@Slf4j
@Controller
@RequestMapping(value = "/article",method = {RequestMethod.POST,RequestMethod.GET})
public class ArticleController extends BaseController {

    @Value("/static/upload/image")
    private String filePath;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    ArticleService articleService;

    public static final String URL = "http://localhost:8088/";

    @GetMapping(value = "/toPost")
    public String toPost(Model model, Integer id){
        System.out.println("id = " + id);
        Article article = articleService.getArticleById(id);
        model.addAttribute("article",article);
        return "blog-post";
    }

    @GetMapping(value = "/toWriteBlog")
    public String toWriteBlog(){
        return "writeBlog";
    }

    @PostMapping(value = "/addArticle")
    @ResponseBody
    public ApiResponse addArticle(Article article){
        System.out.println("article = " + article);
        User loginUser = getLoginUser();
        article.setUserId(loginUser.getUsername()).setAuthorName(loginUser.getNickname()).setTime(DateUtil.now());
        if(articleService.addArticle(article)){
            return ApiResponse.successMsg("成功");
        }
        return ApiResponse.fail("失败");
    }


    @PostMapping(value = "/file/upload")
    @ResponseBody
    public ApiResponse imageUpload(@RequestParam("editormd-image-file") MultipartFile file, HttpServletRequest request) throws URISyntaxException {
        System.out.println("file.getOriginalFilename() = " + file.getOriginalFilename());
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //生成文件名称通用方法
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Random r = new Random();
        StringBuilder tempName = new StringBuilder();
        tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffixName);
        String newFileName = tempName.toString();
        File fileDirectory = new File("E:/upload/");
        //创建文件
        File destFile = new File("E:/upload/" + newFileName);
        try {
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdir()) {
                    return ApiResponse.fail("文件夹创建失败,路径为：" + fileDirectory);
                }
            }
            file.transferTo(destFile);
            return ApiResponse.successMsg(HttpKit.getHost(new URI(request.getRequestURL() + "")) + "/upload/" + newFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return ApiResponse.fail("文件上传失败");
        }
    }
}
