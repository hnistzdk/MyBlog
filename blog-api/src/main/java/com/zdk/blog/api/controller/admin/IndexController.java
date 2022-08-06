package com.zdk.blog.api.controller.admin;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageInfo;
import com.zdk.blog.api.controller.CommonController;
import com.zdk.blog.constant.LogActions;
import com.zdk.blog.constant.WebConst;
import com.zdk.blog.dto.StatisticsDTO;
import com.zdk.blog.model.*;
import com.zdk.blog.service.ArticleService;
import com.zdk.blog.service.AttachService;
import com.zdk.blog.service.CommentsService;
import com.zdk.blog.service.LogsService;
import com.zdk.blog.service.UserService;
import com.zdk.blog.utils.ApiResponse;
import com.zdk.blog.utils.IpKit;
import com.zdk.blog.utils.RedisUtil;
import com.zdk.blog.utils.TaleUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zdk
 * @date 2021/8/12 20:53
 */
@Api(tags = {"管理端首页相关接口"})
@Controller
@RequestMapping(value = "/admin")
public class IndexController extends CommonController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private AttachService attachService;

    @Autowired
    private LogsService logsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisUtil redisUtil;


    @ApiOperation("进入管理端登录页面")
    @GetMapping(value = "/login")
    public String login(){
        return "admin/login";
    }

    @ApiOperation("进入首页")
    @GetMapping(value = "/index")
    public String index(Model model){

        //articles
        PageInfo<Article> articlePageInfo = articleService.getArticlePage(1,6,getLoginUser());
        //comments
        PageInfo<Comments> commentsPageInfo = commentsService.getCommentsPage(1,6, getLoginUser());
        //logs
        PageInfo<Logs> logsPageInfo = logsService.getLogPageByLoginUser(getLoginUser());

        List<Attach> attaches = attachService.getAttachesByUser(getLoginUser());

        //statistics
        StatisticsDTO statistics = new StatisticsDTO((long) articlePageInfo.getList().size(), (long) commentsPageInfo.getList().size(), 3L,(long)attaches.size());
        model.addAttribute("statistics",statistics);
        model.addAttribute("articles",articlePageInfo.getList());
        model.addAttribute("comments",commentsPageInfo.getList());
        model.addAttribute("logs",logsPageInfo.getList());
        return "admin/index";
    }
    /**
     * 个人设置页面
     */
    @ApiOperation("进入个人设置")
    @GetMapping("/profile")
    public String profile(Model model) {
        User user = userService.getById(getLoginUser().getId());
        model.addAttribute("user", user);
        return "admin/profile";
    }

    /**
     * 保存个人信息
     */
    @ApiOperation("保存个人信息")
    @PostMapping("/profile")
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
    @ApiOperation("修改密码")
    @PostMapping(value = "/password")
    @ResponseBody
    public ApiResponse modifyPassword(String oldPassword,String password,String repass) {
        User user = userService.getById(getLoginUser().getId());
        if(!passwordEncoder.matches(oldPassword,user.getPassword())){
            return ApiResponse.fail("旧密码错误");
        }
        if(notOk(password)||notOk(repass)){
            return ApiResponse.fail("请输入正确的密码格式");
        }
        if (!password.equals(repass)){
            return ApiResponse.fail("请输入相同的新密码");
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

    @ApiOperation("注销登录")
    @GetMapping (value = "/logout")
    public String logout() {
        redisUtil.hdel(WebConst.USERINFO, TaleUtils.getCookieValue(WebConst.USERINFO, request));
        Logs logs = new Logs().setAction(LogActions.LOGOUT.getAction()).setAuthorId(getLoginUser().getId())
                .setCreateTime(DateUtil.now()).setIp(IpKit.getIpAddressByRequest(request));
        logsService.save(logs);
        return "admin/login";
    }
}
