package com.zdk.blog.interceptor;

import com.zdk.blog.exception.BusinessException;
import com.zdk.blog.model.User;
import com.zdk.blog.security.Payload;
import com.zdk.blog.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description
 * @Author zdk
 * @Date 2022/8/25 14:06
 */
public class JwtInterceptor implements HandlerInterceptor {
    private static final String RSA_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDI/Iz8qNDsLt7QtscWloZAoMwtelO3SDnQn4RzRwQ45Msi101dwd8gixBoKEFCeUOm8aHpXrS43Vkf3BgxZf/lv+oh7gy6Y3Yg1GAySV1kQXewHbkHrop8KrjlH7cdpbtTCETrZqg4aj7OP/sq8yKVPSCkK5zqFXIObxcUF+j+GlmNnM9ubjtw4T6Bx4IZ7MqiFGgFWWtpygGMbIL7Fpze3AsV0OwHNC6h5HO+k6UuHoBqi23HzmYGD2uQ7BpGayPTm2SJtq/y2N0FVcGpDuhMrwylN7qEWgYIDHpMMjLTzlxLyOuLrfgwkx20itAxQDVhKAiEtHt8YeMFs8kQAhRjAgMBAAECggEAfMfPAE4O55Nx4kKX9/4b+6PZH6MCtpewzUU8BhXhGTFyrH7fGYZz8NBlr0TOv2ZqCguaajjSGrPR0WXj30dkSE+OHOKeJFn7zQYNcyr5QHUkycKqTYqfj9c2U4oNNerfEf3gTAXACsZrl8FBPrkUj3MUANjS4sHNvghaW688iATaSE4RdJWksuyHlQ2H2aFoqQQqWwfZGcD8PUboFr2VnT0EfsnNEo25F3ffTwHCU8wTkC+A/R4kSe7OVziA0KLYr9gRQcS0r58s3SIe/OmpJrdsiehj77L5IKvXddr+rSiFV2lABwGTvwF8dCYJQswME951EZw0SWT9hg1VgzOGAQKBgQDlRR3kpeKuF5CYeORVwvjKTHaEJ1xj369Dw15fIkEGZviHJCvySvWmo3U1OS/taCs9u6ZjRrL0b/FIXN/BwXygTQwF+lcgT52sjbMUhBjMNjWIsQyJEVdxGo/Ck+teseIlQWcPPtdZG/IPhSM7FVfWyI124YunSFH6bxrlIwS9UQKBgQDga0ZHrwgFSrXa4C9mFA0NqGLgjNXxrzALnPFQqMeeJtWC9rBGxQkAw2/OO3375A9mvvt7k4j2UT2yA5Vn3THnK6bRdissQCYr9eFRCe2DxiM42MGBOn3YWkdP64Bh7FJgt74GrhL0cZ0obJhsSF6pfnbgQk7x90z4hmwFJJo5cwKBgQCvSYqzV8jZhZa9nCAnGawRLyilnVpb2ZcsXeB3J79DX9K/r/4RfxIVV3+zM4fbIPt+dUufN1x22mdFBgzlHENmY8G/iEi4oWZRmPzDwMMczo1bdtG4shOCm+Yxw9n+Sk42Yayj0U3gCSemigoHkCfaRPczXvng6cATkoKjkYR8IQKBgQCdaYF9bH9Svum6If/9ONlDGDZqzT6P7NLlgmsdqZWDEDg5SXGx4ikDeRJmdQJUec53wkRBlOW/6JI+2BanKNcFEHBNgfacbk6YDTrDDhSklolEKX3peC6nYAOfQk/l386Ueq12oAk5nquenDV51nVXrBYUDU60mENMG3WPUfI/kwKBgHW33OgZQvgpqDUqCzsl06RPpNnWRfp9+R8qAVPFHGERXglzedY2xRan/EDTrIXuZ6m6iQ1eyTTw8bNfOEUC1CK27xZz6HoDJ62Ho1OzCzViGCr/IgrUQ6KRCCwXLIvvcAidbmhzofaKxf+JDHcDQ8KAgYb2Gte07ToZne90+bJq";
    private static final String RSA_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyPyM/KjQ7C7e0LbHFpaGQKDMLXpTt0g50J+Ec0cEOOTLItdNXcHfIIsQaChBQnlDpvGh6V60uN1ZH9wYMWX/5b/qIe4MumN2INRgMkldZEF3sB25B66KfCq45R+3HaW7UwhE62aoOGo+zj/7KvMilT0gpCuc6hVyDm8XFBfo/hpZjZzPbm47cOE+gceCGezKohRoBVlracoBjGyC+xac3twLFdDsBzQuoeRzvpOlLh6Aaottx85mBg9rkOwaRmsj05tkibav8tjdBVXBqQ7oTK8MpTe6hFoGCAx6TDIy085cS8jri634MJMdtIrQMUA1YSgIhLR7fGHjBbPJEAIUYwIDAQAB";
    private static final String AUTHORIZATION = "Authorization";

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(AUTHORIZATION);
        if (token == null) {
            throw new BusinessException("token为空");
        }
        // 1.校验JWT字符串
        try {
            JwtUtils.checkToken(token, RSA_PUBLIC_KEY);
        }catch (SignatureException e){
            throw new BusinessException("token验证失败");
        }catch (ExpiredJwtException e){
            throw new BusinessException("token已过期");
        }catch (Exception e){
            throw new BusinessException("token无效");
        }
        // 2.取出JWT字符串载荷中的随机token，获取用户信息
        Payload<User> userPayload = JwtUtils.getInfoFromToken(token, RSA_PUBLIC_KEY, User.class);
        User userInfo = userPayload.getUserInfo();
        return true;
    }
}
