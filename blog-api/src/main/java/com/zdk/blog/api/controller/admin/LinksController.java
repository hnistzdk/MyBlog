package com.zdk.blog.api.controller.admin;

import com.github.pagehelper.PageInfo;
import com.zdk.blog.api.controller.CommonController;
import com.zdk.blog.dto.LinkDTO;
import com.zdk.blog.model.Metas;
import com.zdk.blog.service.MetasService;
import com.zdk.blog.utils.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author zdk
 * @date 2021/8/13 10:01
 */
@Api(tags = {"友情链接相关接口"})
@Controller
@RequestMapping(value = "/admin/links")
public class LinksController extends CommonController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MetasService metasService;

    @ApiOperation("后台友链页")
    @GetMapping("")
    public String index(Model model,
                        @RequestParam(name = "page",required = false, defaultValue = "1") Integer pageNumber,
                        @RequestParam(name = "limit",required = false, defaultValue = "10")Integer pageSize){

        PageInfo<Metas> links = metasService.getLinksPage(pageNumber, pageSize);
        model.addAttribute("links",links);
        return "admin/links";
    }

    @ApiOperation("保存友链")
    @PostMapping("/save")
    @ResponseBody
    public ApiResponse save(LinkDTO link){
        return ApiResponse.success(metasService.addLink(link));
    }

    @ApiOperation("删除友链")
    @PostMapping("/delete")
    @ResponseBody
    public ApiResponse delete(Integer id){
        return ApiResponse.result(metasService.removeById(id));
    }
}
