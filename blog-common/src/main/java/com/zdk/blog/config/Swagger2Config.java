package com.zdk.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @Description
 * @Author zdk
 * @Date 2022/7/23 13:50
 */
@Configuration
@EnableOpenApi
@SuppressWarnings("all")
public class Swagger2Config {
    @Bean
    public Docket webApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zdk.blog.api.controller"))
                .build();
    }

    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                .title("MyBlog接口文档")
                .description("博客相关接口文档")
                .version("1.0")
                .contact(new Contact("zdk", "https://github.com/hnistzdk", "369365576@qq.com"))
                .build();
    }

//    http://localhost:8092/swagger-ui/index.html
}
