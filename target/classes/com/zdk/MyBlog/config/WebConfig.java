package com.zdk.MyBlog.config;

import com.zdk.MyBlog.interceptor.LoginHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zdk
 * @date 2021/7/6 19:16
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 添加视图控制器
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/","/user/toLogin");
        registry.addRedirectViewController("/admin","/admin/login");
    }

    /**
     * 提前实例化拦截器
     * @return
     */
    @Bean
    public LoginHandlerInterceptor getLoginHandlerInterceptor(){
        return new LoginHandlerInterceptor();
    }

    /**
     * Spring-Security密码加密bean
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    /**
     * 添加拦截器时使用getLoginHandlerInterceptor获取已实例化的拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLoginHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login.html","/","/user/login","/user/toLogin")
                .excludePathPatterns("/admin/login","/admin")
                .excludePathPatterns("/**/*.html", "/**/*.js", "/**/*.css", "/**/*.json", "/**/*.icon","/**/*.jpg","/**/*.png");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
        registry.addResourceHandler("/editor/**").addResourceLocations("classpath:/editor/");
//        registry.addResourceHandler("/upload/**").addResourceLocations("classpath:/static/upload/image");
        registry.addResourceHandler("/static/upload/image/**").addResourceLocations("file:F:\\project\\my\\MyBlog\\src\\main\\resources\\static\\upload\\image\\");
    }
}
