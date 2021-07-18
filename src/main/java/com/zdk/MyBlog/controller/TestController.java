package com.zdk.MyBlog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zdk
 * @date 2021/7/6 17:24
 */
@Slf4j
@Controller
public class TestController {
    @RequestMapping("/test")
    public ModelAndView test(Model model){
        model.addAttribute("zdk", "张迪凯");
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("test");
        return modelAndView;
    }
    @RequestMapping("/index")
    public String index(Model model){
        return "index";
    }

    @RequestMapping("/blog")
    public String blog(Model model){
        return "blog-post";
    }

    @RequestMapping("/blogList")
    public String blogList(){
        return "blog-list";
    }

    @RequestMapping("/about")
    public String about(){
        return "about";
    }

}
