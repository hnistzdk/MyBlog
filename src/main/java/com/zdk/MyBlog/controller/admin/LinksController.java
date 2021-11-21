package com.zdk.MyBlog.controller.admin;

import com.github.pagehelper.PageInfo;
import com.zdk.MyBlog.constant.Types;
import com.zdk.MyBlog.controller.BaseController;
import com.zdk.MyBlog.model.dto.LinkDto;
import com.zdk.MyBlog.model.pojo.Metas;
import com.zdk.MyBlog.service.metas.MetasService;
import com.zdk.MyBlog.utils.ApiResponse;
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
@Api("友情链接")
@Controller
@RequestMapping(value = "/admin/links")
public class LinksController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LinksController.class);
    @Autowired
    private MetasService metasService;

    @ApiOperation("友链页")
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
    public ApiResponse save(LinkDto link){
        return ApiResponse.success(metasService.addLink(link));
    }

    @ApiOperation("删除友链")
    @PostMapping("/delete")
    @ResponseBody
    public ApiResponse delete(Integer id){
        return ApiResponse.result(metasService.removeById(id));
    }
}
