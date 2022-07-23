package com.zdk.blog.common.config;

import com.zdk.blog.common.interceptor.AuthInterceptor;
import com.zdk.blog.common.interceptor.LoginHandlerInterceptor;
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
 * @Description
 * @Author zdk
 * @Date 2022/7/23 14:55
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 添加视图控制器
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/","/user/toIndex");
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

    @Bean
    public AuthInterceptor getAuthInterceptor(){
        return new AuthInterceptor();
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
        //登录拦截器
        registry.addInterceptor(getLoginHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login.html","/","/user/login","/user/toLogin","/user/toIndex","/user/links")
                .excludePathPatterns("/admin/login","/admin")
                .excludePathPatterns("/**/*.html", "/**/*.js", "/**/*.css", "/**/*.json", "/**/*.icon","/**/*.jpg","/**/*.png");

        //权限拦截器
        registry.addInterceptor(getAuthInterceptor())
                .addPathPatterns("/api/*")
                .excludePathPatterns("/login.html","/","/user/login","/user/toLogin","/user/toIndex","/user/links")
                .excludePathPatterns("/admin/login","/admin")
                .excludePathPatterns("/**/*.html", "/**/*.js", "/**/*.css", "/**/*.json", "/**/*.icon","/**/*.jpg","/**/*.png");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
        registry.addResourceHandler("/editor/**").addResourceLocations("classpath:/editor/");
    }
}
