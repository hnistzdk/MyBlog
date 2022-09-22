package com.zdk.blog.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdk.blog.config.JwtConfig;
import com.zdk.blog.constant.LogActions;
import com.zdk.blog.constant.WebConst;
import com.zdk.blog.model.Logs;
import com.zdk.blog.model.User;
import com.zdk.blog.request.login.LoginRequest;
import com.zdk.blog.response.ApiResponse;
import com.zdk.blog.service.LogsService;
import com.zdk.blog.service.UserService;
import com.zdk.blog.utils.*;
import com.zdk.blog.vo.UserInfoVO;
import com.zdk.blog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author zdk
 * @date 2021/7/20 18:24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService,ParaValidator {

    private static final Integer ERROR_NUMBER = 4;

    private static final String USER_INFO_CACHE_KEY_PREFIX = "user:";

    @Autowired
    private JwtConfig jwtConfig;


    @Autowired
    private HttpServletRequest request;

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LogsService logsService;


    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    @Override
    public ApiResponse login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        String remember = loginRequest.getRemember();
        String ip = IpKit.getIpAddressByRequest(request);
        //构造登录错误次数唯一缓存key
        String userCountKey = WebConst.LOGIN_ERROR_COUNT+":" + ip +":" + username;
        Integer loginErrorCount = redisUtil.getNumber(userCountKey);
        //检查密码错误次数
        if (isOk(loginErrorCount) && loginErrorCount >= ERROR_NUMBER) {
            return ApiResponse.fail("您输入密码已经错误超过4次，请10分钟后尝试");
        }
        User user = getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                this.updateUserInfo(user.setLoginDate(DateUtil.now()).setLoginTimes(user.getLoginTimes()+1));
                Logs logs = new Logs().setAction(LogActions.LOGIN.getAction()).setAuthorId(user.getId())
                        .setCreateTime(DateUtil.now()).setIp(IpKit.getIpAddressByRequest(request));
                logsService.save(logs);
                //登录成功 删除错误次数缓存
                redisUtil.del(userCountKey);
                long expireTime;
                //token过期时间设置 单位默认分钟
                if (isOk(remember)) {
                    expireTime = jwtConfig.getMaxExpireTime();
                } else {
                    expireTime = jwtConfig.getMinExpireTime();
                }
                // 构造token返回
                String token = JwtUtils.generateToken(user, jwtConfig.getRsaPrivateKey(), expireTime, jwtConfig.getTimeUnit());
                redisUtil.hset(WebConst.USERINFO, USER_INFO_CACHE_KEY_PREFIX+token, user, expireTime);
                return ApiResponse.success(token,"登录成功");
            } else {
                loginErrorCount = redisUtil.getNumber(userCountKey);
                if (notOk(loginErrorCount)) {
                    redisUtil.setNumber(userCountKey, 1, 600);
                } else {
                    redisUtil.incr(userCountKey, 1);
                    if (redisUtil.getNumber(userCountKey) >= ERROR_NUMBER) {
                        redisUtil.expire(userCountKey, 600);
                    }
                }
                return ApiResponse.fail("账号或密码错误,您还有" + (ERROR_NUMBER - redisUtil.getNumber(userCountKey)) + "次机会");
            }
        } else {
            return ApiResponse.fail("该用户不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    @Override
    public Boolean updateUserInfo(User user) {
        if(notOk(user)|| notOk(user.getId())){
            return false;
        }
        return baseMapper.updateById(user)>0;
    }

    @Override
    public PageInfo<User> getUserPage(Integer pageNumber, Integer pageSize, String keywords) {
        PageHelper.startPage(pageNumber, pageSize, "username");
        List<User> userList = lambdaQuery().like(isOk(keywords), User::getUsername, keywords)
                .or().like(isOk(keywords), User::getNickname, keywords)
                .or().like(isOk(keywords), User::getTrueName, keywords).list();
        return new PageInfo<>(userList);
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    @Override
    public Boolean editUserInfo(UserInfoVO userInfoVo) {
        if (userInfoVo==null|| notOk(userInfoVo.getId())){
            return false;
        }
        User user = new User();
        user.setId(userInfoVo.getId()).setNickname(userInfoVo.getNickname())
                .setGender(userInfoVo.getGender()).setTrueName(userInfoVo.getTrueName())
                .setEmail(userInfoVo.getEmail());
        return updateById(user);
    }
}
