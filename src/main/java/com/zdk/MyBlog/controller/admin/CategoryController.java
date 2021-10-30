package com.zdk.MyBlog.controller.admin;

import com.zdk.MyBlog.constant.Types;
import com.zdk.MyBlog.controller.BaseController;
import com.zdk.MyBlog.exception.MyGlobalException;
import com.zdk.MyBlog.model.dto.MetaDto;
import com.zdk.MyBlog.model.dto.cond.MetaCond;
import com.zdk.MyBlog.model.pojo.Metas;
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
@RequestMapping(value = "/admin/category",method = {RequestMethod.POST,RequestMethod.GET})
public class CategoryController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private MetasService metasService;

    @ApiOperation("进入标签分类页")
    @GetMapping(value = "")
    public String index(Model model){
        List<MetaDto> categories = metasService.getMetaList(Types.CATEGORY.getType());
        List<MetaDto> tags = metasService.getMetaList(Types.TAG.getType());
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
            if (e instanceof MyGlobalException){
                MyGlobalException ex = (MyGlobalException)e;
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
            if (e instanceof MyGlobalException){
                MyGlobalException ex = (MyGlobalException)e;
                msg = ex.getMessage();
            }
            return ApiResponse.fail(msg);
        }
        return ApiResponse.success("分类删除成功");
    }
}
