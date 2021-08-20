package com.zdk.MyBlog.controller.article;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.upyun.Result;
import com.zdk.MyBlog.controller.BaseController;
import com.zdk.MyBlog.model.pojo.Article;
import com.zdk.MyBlog.model.pojo.User;
import com.zdk.MyBlog.service.article.ArticleService;
import com.zdk.MyBlog.utils.ApiResponse;
import com.zdk.MyBlog.utils.UpYunUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    ArticleService articleService;
    @Autowired
    HttpServletRequest request;

    @GetMapping(value = "/toPost")
    public String toPost(Model model, Integer id){
        Article article = articleService.getArticleById(id);
        articleService.updateById(article.setReadCount(article.getReadCount()+1));
        model.addAttribute("article",article);
        model.addAttribute("user",getLoginUser());
        return "blog/blog-post";
    }

    @GetMapping(value = "/toPost/{id}")
    public String toPost2(Model model, @PathVariable Integer id){
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
        article.setAuthorId(loginUser.getId()).setAuthorName(loginUser.getNickname());
        if(articleService.addArticle(article)){
            return ApiResponse.success("发布成功");
        }
        return ApiResponse.fail("发布失败");
    }

    @PostMapping("/modify")
    @ResponseBody
    public ApiResponse modifyArticle(Article article){
        boolean update = articleService.updateById(article);
        if(update){
            return ApiResponse.success("保存成功");
        }
        return ApiResponse.fail("保存失败");
    }

    @GetMapping("/delete")
    @ResponseBody
    public ApiResponse deleteArticle(Integer id){
        Boolean result = articleService.deleteArticleById(id);
        if(result){
            return ApiResponse.success("删除成功");
        }
        return ApiResponse.fail("删除失败");
    }

    @PostMapping(value = "/file/imageUpload")
    @ResponseBody
    public Map<String, Object> imageUpload(@RequestParam(value = "editormd-image-file") MultipartFile file) throws URISyntaxException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, IOException {
        //上传的文件名
        String filename=file.getOriginalFilename();
        //获取文件名的后缀
        String suffixName=filename.substring(filename.lastIndexOf("."));
        filename=UUID.randomUUID()+suffixName;

        //上传又拍云
        Result uploadResult = UpYunUtil.testSync(file.getBytes(), filename);
        JSONObject photoMsg = JSONUtil.parseObj(uploadResult.getMsg());
        String url="http://zdk-blog-image.test.upcdn.net/"+photoMsg.getStr("url");

        HashMap<String, Object> result = new HashMap<>(3);
        result.put("success", 1);
        result.put("message", "上传成功");
        result.put("link", url);
        result.put("url", url);
        return result;
    }
}
