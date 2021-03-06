package com.zdk.MyBlog.controller.admin;

import com.zdk.MyBlog.constant.Types;
import com.zdk.MyBlog.controller.BaseController;
import com.zdk.MyBlog.exception.GlobalException;
import com.zdk.MyBlog.dto.MetaDTO;
import com.zdk.MyBlog.dto.cond.MetaCond;
import com.zdk.MyBlog.model.Metas;
import com.zdk.MyBlog.service.metas.MetasService;
import com.zdk.MyBlog.utils.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zdk
 * @date 2021/8/13 9:57
 */
@Api("分类和标签")
@Controller
@RequestMapping(value = "/admin/category")
public class CategoryController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private MetasService metasService;

    @ApiOperation("进入标签分类页")
    @GetMapping(value = "")
    public String index(Model model){
        List<MetaDTO> categories = metasService.getMetaList(Types.CATEGORY.getType());
        List<MetaDTO> tags = metasService.getMetaList(Types.TAG.getType());
        model.addAttribute("categories",categories);
        model.addAttribute("tags",tags);
        return "admin/category";
    }


    @ApiOperation("保存分类")
    @PostMapping(value = "/save")
    @ResponseBody
    public ApiResponse save(
            @ApiParam(name = "cname", value = "分类名", required = true)
            @RequestParam(name = "cname", required = false)
                    String cname,
            @ApiParam(name = "mid", value = "meta编号", required = false)
            @RequestParam(name = "mid", required = false)
                    Integer mid
    ){
        try {
            metasService.saveMeta(Types.CATEGORY.getType(), cname, mid);
        }catch (Exception e){
            String msg = "分类保存失败";
            if (e instanceof GlobalException){
                GlobalException ex = (GlobalException)e;
                msg = ex.getMessage();
            }
            return ApiResponse.fail(msg);
        }
        return ApiResponse.success("分类保存成功");
    }

    @ApiOperation("删除分类")
    @PostMapping(value = "/delete")
    @ResponseBody
    public ApiResponse delete(
            @ApiParam(name = "mid", value = "meta编号", required = false)
            @RequestParam(name = "mid", required = false)
                    Integer mid
    ){
        try {
            Metas metas = metasService.getMetas(new MetaCond().setId(mid)).get(0);
            metasService.deleteMeta(metas.getType(), metas.getName(), mid);
        }catch (Exception e){
            String msg = "分类删除失败";
            if (e instanceof GlobalException){
                GlobalException ex = (GlobalException)e;
                msg = ex.getMessage();
            }
            return ApiResponse.fail(msg);
        }
        return ApiResponse.success("分类删除成功");
    }
}
