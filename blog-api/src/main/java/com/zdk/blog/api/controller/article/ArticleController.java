package com.zdk.blog.api.controller.article;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.upyun.Result;
import com.zdk.blog.api.controller.CommonController;
import com.zdk.blog.constant.ErrorConstant;
import com.zdk.blog.constant.Types;
import com.zdk.blog.constant.WebConst;
import com.zdk.blog.dto.CommentsDTO;
import com.zdk.blog.dto.MetaDTO;
import com.zdk.blog.model.Article;
import com.zdk.blog.model.Comments;
import com.zdk.blog.model.User;
import com.zdk.blog.request.article.ArticleCreateRequest;
import com.zdk.blog.response.DataResponse;
import com.zdk.blog.response.PageResponse;
import com.zdk.blog.service.ArticleService;
import com.zdk.blog.service.CommentsService;
import com.zdk.blog.service.MetasService;
import com.zdk.blog.response.ApiResponse;
import com.zdk.blog.utils.IpKit;
import com.zdk.blog.utils.RedisUtil;
import com.zdk.blog.vo.article.ArticleVO;
import com.zdk.starter.service.UpYunOssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.*;

/**
 * @author zdk
 * @date 2021/7/22 17:02
 */
@Api(tags = {"博客端文章相关接口"})
@RestController
@RequestMapping(value = "/article")
@Validated
//@Uncheck
public class ArticleController extends CommonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);

    private static final String URL_PREFIX = "http://zdk-blog-image.test.upcdn.net";

    @Autowired
    private ArticleService articleService;

    @Autowired
    private MetasService metasService;

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private UpYunOssService upYunOssService;

    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation("进入博客详情页")
    @GetMapping(value = "/toPost/{id}")
    public ApiResponse toPost(@PathVariable Integer id){
        Article article = articleService.getArticleById(id);
        articleService.update(new UpdateWrapper<Article>().eq("id", id).set("read_count", article.getReadCount()+1));
        List<Comments> commentsList = commentsService.getCommentsByArticleId(id);
        ArticleVO vo = new ArticleVO();
        BeanUtils.copyProperties(article, vo);
        vo.setCommentsList(commentsList);
        return DataResponse.dataResponse(vo);
    }


    @ApiOperation("返回博客列表")
    @GetMapping(value = "/list")
    public ApiResponse blogList(Model model,
                           @RequestParam(name = "page",required = false, defaultValue = "1") Integer pageNumber,
                           @RequestParam(name = "limit",required = false, defaultValue = "5")Integer pageSize,
                           @RequestParam(name = "keywords",required = false) String keywords,
                           @RequestParam(name = "tag",required = false) String tag){
        PageInfo<Article> articlePageInfo = articleService.getArticlePageByKeywords(pageNumber, pageSize, keywords, tag);
        List<ArticleVO> voList = new ArrayList<>(articlePageInfo.getSize());
        for (Article article : articlePageInfo.getList()) {
            ArticleVO vo = new ArticleVO();
            BeanUtils.copyProperties(article, vo);
            voList.add(vo);
        }
        return PageResponse.pageResponse(voList,articlePageInfo);
    }


    @ApiOperation("发布文章")
    @PostMapping(value = "/add")
    public ApiResponse add(@RequestBody ArticleCreateRequest createRequest){
        articleService.add(createRequest,getLoginUser());
        return ApiResponse.success();
    }

    @ApiOperation("修改文章")
    @PostMapping(value = "/update")
    public ApiResponse update(Article article){
        //去除文章储存时的多余逗号
        article.setContent(article.getContent().replaceAll("^,", ""));
        article.setCategories(article.getCategories().replaceAll("^,", ""));
        boolean update = articleService.modifyArticle(article);
        return ApiResponse.result(update, ErrorConstant.Article.UPDATE_ARTICLE_FAIL);
    }

    @ApiOperation("删除文章")
    @PostMapping(value = "/delete")
    public ApiResponse delete(Integer id){
        Boolean result = articleService.deleteArticleById(id);
        return ApiResponse.result(result,ErrorConstant.Common.DELETE_FAIL);
    }

    @ApiOperation("文章详情")
    @GetMapping(value = "/detail/{id}")
//    @Uncheck
    public ApiResponse detail(@NotNull(message = "id不能为空") @PathVariable("id") Integer id){
        Article article = articleService.getArticleById(id);
        return ApiResponse.success(article);
    }

    @ApiOperation("图片上传")
    @PostMapping(value = "/file/imageUpload")
    public Map<String, Object> imageUpload(@RequestParam(value = "editormd-image-file") MultipartFile file) throws URISyntaxException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, IOException {
        request.setCharacterEncoding( "utf-8" );
        response.setHeader( "Content-Type" , "text/html" );
        //上传的文件名
        String filename=file.getOriginalFilename();
        //获取文件名的后缀
        String suffixName=filename.substring(filename.lastIndexOf("."));
        filename=UUID.randomUUID()+suffixName;

        //上传又拍云
        Result uploadResult = upYunOssService.uploadFile(filename, null, file.getBytes());
        JSONObject photoMsg = JSONUtil.parseObj(uploadResult.getMsg());
        String url=URL_PREFIX+photoMsg.getStr("url");

        HashMap<String, Object> result = new HashMap<>(3);
        result.put("success", 1);
        result.put("message", "上传成功");
        result.put("link", url);
        result.put("url", url);
        return result;
    }

    @ApiOperation("添加评论")
    @PostMapping("/comment")
    public ApiResponse comment(CommentsDTO comments){
        User loginUser = getLoginUser();
        //当前用户未登录 跳转或弹出登录界面
        if (notOk(loginUser.getId())){
            return ApiResponse.failWidthDiyCode("1001");
        }
        String key = WebConst.COMMENT_INTERVAL+":"+IpKit.getIpAddressByRequest(request)+":"+getLoginUser().getUsername();
        //如果存在上次评论记录 证明两次评论间隔时间小于一分钟,拒绝再次评论
        if (redisUtil.hasKey(key)){
            return ApiResponse.fail(ErrorConstant.Comment.COMMENT_FREQUENTLY);
        }
        ApiResponse res = commentsService.comment(comments, request,getLoginUser());
        redisUtil.set(key, String.valueOf(System.currentTimeMillis()), 60);
        return res;
    }

    @ApiOperation("删除评论")
    @PostMapping("/deleteComment")
    public ApiResponse deleteComments(Integer id) {
        return ApiResponse.result(commentsService.removeById(id));
    }

    @ApiOperation("审核或撤销评论")
    @PostMapping("/commentStatus")
    public ApiResponse commentStatus(Integer id,String status) {
        if (notOk(id) || notOk(status)){
            return ApiResponse.fail(ErrorConstant.Common.INVALID_PARAM);
        }
        return ApiResponse.result(commentsService.updateById(new Comments().setId(id).setStatus(status)));
    }

}
