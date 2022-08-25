package com.zdk.blog.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    private static final String RSA_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDI/Iz8qNDsLt7QtscWloZAoMwtelO3SDnQn4RzRwQ45Msi101dwd8gixBoKEFCeUOm8aHpXrS43Vkf3BgxZf/lv+oh7gy6Y3Yg1GAySV1kQXewHbkHrop8KrjlH7cdpbtTCETrZqg4aj7OP/sq8yKVPSCkK5zqFXIObxcUF+j+GlmNnM9ubjtw4T6Bx4IZ7MqiFGgFWWtpygGMbIL7Fpze3AsV0OwHNC6h5HO+k6UuHoBqi23HzmYGD2uQ7BpGayPTm2SJtq/y2N0FVcGpDuhMrwylN7qEWgYIDHpMMjLTzlxLyOuLrfgwkx20itAxQDVhKAiEtHt8YeMFs8kQAhRjAgMBAAECggEAfMfPAE4O55Nx4kKX9/4b+6PZH6MCtpewzUU8BhXhGTFyrH7fGYZz8NBlr0TOv2ZqCguaajjSGrPR0WXj30dkSE+OHOKeJFn7zQYNcyr5QHUkycKqTYqfj9c2U4oNNerfEf3gTAXACsZrl8FBPrkUj3MUANjS4sHNvghaW688iATaSE4RdJWksuyHlQ2H2aFoqQQqWwfZGcD8PUboFr2VnT0EfsnNEo25F3ffTwHCU8wTkC+A/R4kSe7OVziA0KLYr9gRQcS0r58s3SIe/OmpJrdsiehj77L5IKvXddr+rSiFV2lABwGTvwF8dCYJQswME951EZw0SWT9hg1VgzOGAQKBgQDlRR3kpeKuF5CYeORVwvjKTHaEJ1xj369Dw15fIkEGZviHJCvySvWmo3U1OS/taCs9u6ZjRrL0b/FIXN/BwXygTQwF+lcgT52sjbMUhBjMNjWIsQyJEVdxGo/Ck+teseIlQWcPPtdZG/IPhSM7FVfWyI124YunSFH6bxrlIwS9UQKBgQDga0ZHrwgFSrXa4C9mFA0NqGLgjNXxrzALnPFQqMeeJtWC9rBGxQkAw2/OO3375A9mvvt7k4j2UT2yA5Vn3THnK6bRdissQCYr9eFRCe2DxiM42MGBOn3YWkdP64Bh7FJgt74GrhL0cZ0obJhsSF6pfnbgQk7x90z4hmwFJJo5cwKBgQCvSYqzV8jZhZa9nCAnGawRLyilnVpb2ZcsXeB3J79DX9K/r/4RfxIVV3+zM4fbIPt+dUufN1x22mdFBgzlHENmY8G/iEi4oWZRmPzDwMMczo1bdtG4shOCm+Yxw9n+Sk42Yayj0U3gCSemigoHkCfaRPczXvng6cATkoKjkYR8IQKBgQCdaYF9bH9Svum6If/9ONlDGDZqzT6P7NLlgmsdqZWDEDg5SXGx4ikDeRJmdQJUec53wkRBlOW/6JI+2BanKNcFEHBNgfacbk6YDTrDDhSklolEKX3peC6nYAOfQk/l386Ueq12oAk5nquenDV51nVXrBYUDU60mENMG3WPUfI/kwKBgHW33OgZQvgpqDUqCzsl06RPpNnWRfp9+R8qAVPFHGERXglzedY2xRan/EDTrIXuZ6m6iQ1eyTTw8bNfOEUC1CK27xZz6HoDJ62Ho1OzCzViGCr/IgrUQ6KRCCwXLIvvcAidbmhzofaKxf+JDHcDQ8KAgYb2Gte07ToZne90+bJq";
    private static final String RSA_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyPyM/KjQ7C7e0LbHFpaGQKDMLXpTt0g50J+Ec0cEOOTLItdNXcHfIIsQaChBQnlDpvGh6V60uN1ZH9wYMWX/5b/qIe4MumN2INRgMkldZEF3sB25B66KfCq45R+3HaW7UwhE62aoOGo+zj/7KvMilT0gpCuc6hVyDm8XFBfo/hpZjZzPbm47cOE+gceCGezKohRoBVlracoBjGyC+xac3twLFdDsBzQuoeRzvpOlLh6Aaottx85mBg9rkOwaRmsj05tkibav8tjdBVXBqQ7oTK8MpTe6hFoGCAx6TDIy085cS8jri634MJMdtIrQMUA1YSgIhLR7fGHjBbPJEAIUYwIDAQAB";
    private static final String USER_INFO_CACHE_KEY_PREFIX = "user:";
    private static final long MAX_EXPIRE_TIME = 3600;
    private static final long MIN_EXPIRE_TIME = 1;

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
                //token过期时间设置 单位分钟
                if (isOk(remember)) {
                    expireTime = MAX_EXPIRE_TIME;
                } else {
                    expireTime = MIN_EXPIRE_TIME;
                }
                // 构造token返回
                String token = JwtUtils.generateToken(user, RSA_PRIVATE_KEY, expireTime, ChronoUnit.MINUTES);
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
