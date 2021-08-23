package com.zdk.MyBlog.controller.admin;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageInfo;
import com.zdk.MyBlog.constant.LogActions;
import com.zdk.MyBlog.constant.WebConst;
import com.zdk.MyBlog.controller.BaseController;
import com.zdk.MyBlog.model.dto.StatisticsDto;
import com.zdk.MyBlog.model.pojo.Article;
import com.zdk.MyBlog.model.pojo.Comments;
import com.zdk.MyBlog.model.pojo.Logs;
import com.zdk.MyBlog.model.pojo.User;
import com.zdk.MyBlog.service.article.ArticleService;
import com.zdk.MyBlog.service.comments.CommentsService;
import com.zdk.MyBlog.service.logs.LogsService;
import com.zdk.MyBlog.service.user.UserService;
import com.zdk.MyBlog.utils.ApiResponse;
import com.zdk.MyBlog.utils.IpKit;
import com.zdk.MyBlog.utils.RedisUtil;
import com.zdk.MyBlog.utils.TaleUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentsService commentsService;
    @Autowired
    private LogsService logsService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RedisUtil redisUtil;


    @ApiOperation("进入管理端登录页面")
    @GetMapping("/login")
    public String login(){
        return "admin/login";
    }

    @ApiOperation("进入首页")
    @GetMapping("/index")
    public String index(Model model){

        //articles
        PageInfo<Article> articlePageInfo = articleService.getArticlePage(1,6,getLoginUser());
        //comments
        PageInfo<Comments> commentsPageInfo = commentsService.getCommentsPage(1,6, getLoginUser());
        //logs
        PageInfo<Logs> logsPageInfo = logsService.getLogPageByLoginUser(getLoginUser());
        //statistics
        StatisticsDto statistics = new StatisticsDto((long) articlePageInfo.getList().size(), (long) commentsPageInfo.getList().size(), 3L,4L);
        model.addAttribute("statistics",statistics);
        model.addAttribute("articles",articlePageInfo.getList());
        model.addAttribute("comments",commentsPageInfo.getList());
        model.addAttribute("logs",logsPageInfo.getList());
        return "admin/index";
    }
    /**
     * 个人设置页面
     */
    @GetMapping(value = "/profile")
    public String profile(Model model) {
        User user = userService.getById(getLoginUser().getId());
        model.addAttribute("user", user);
        return "admin/profile";
    }

    /**
     * 保存个人信息
     */
    @PostMapping(value = "/profile")
    @ResponseBody
    public ApiResponse saveProfile(@RequestParam String nickName, @RequestParam String email) {
        User user = getLoginUser();
        user.setNickname(nickName).setEmail(email);
        Boolean updateUserInfo = userService.updateUserInfo(user);
        Logs logs = new Logs().setAction(LogActions.UP_INFO.getAction()).setAuthorId(getLoginUser().getId())
                .setCreateTime(DateUtil.now()).setIp(IpKit.getIpAddressByRequest(request));
        logsService.save(logs);
        if(updateUserInfo){
            redisUtil.hset(WebConst.USERINFO, TaleUtils.getCookieValue(WebConst.USERINFO, request), user);
            return ApiResponse.success("保存成功");
        }
        return ApiResponse.fail("保存失败");
    }

    /**
     * 修改密码
     */
    @PostMapping(value = "/password")
    @ResponseBody
    public ApiResponse modifyPassword(String oldPassword,String password,String repass) {
        User user = userService.getById(getLoginUser().getId());
        if(!passwordEncoder.matches(oldPassword,user.getPassword())){
            return ApiResponse.fail("旧密码错误");
        }
        if(notOk(password)||notOk(repass)||!password.equals(repass)){
            return ApiResponse.fail("请输入正确的密码格式");
        }
        if(userService.updateUserInfo(user.setPassword(passwordEncoder.encode(password)))){
            redisUtil.hset(WebConst.USERINFO, TaleUtils.getCookieValue(WebConst.USERINFO, request), user);
            Logs logs = new Logs().setAction(LogActions.UP_PWD.getAction()).setAuthorId(user.getId())
                    .setCreateTime(DateUtil.now()).setIp(IpKit.getIpAddressByRequest(request));
            logsService.save(logs);
            return ApiResponse.success("修改密码成功");
        }
        return ApiResponse.fail("修改密码失败");
    }


    @GetMapping ("/logout")
    public String logout() {
        redisUtil.hdel(WebConst.USERINFO, TaleUtils.getCookieValue(WebConst.USERINFO, request));
        Logs logs = new Logs().setAction(LogActions.LOGOUT.getAction()).setAuthorId(getLoginUser().getId())
                .setCreateTime(DateUtil.now()).setIp(IpKit.getIpAddressByRequest(request));
        logsService.save(logs);
        return "admin/login";
    }
}
