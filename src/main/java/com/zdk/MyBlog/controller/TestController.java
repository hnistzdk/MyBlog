package com.zdk.MyBlog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zdk
 * @date 2021/7/6 17:24
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/test")
    public String test(){
        return "success";
    }
}
