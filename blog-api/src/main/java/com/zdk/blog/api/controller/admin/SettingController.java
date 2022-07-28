package com.zdk.blog.api.controller.admin;

import com.zdk.blog.api.controller.BaseController;
import com.zdk.blog.api.controller.CommonController;
import com.zdk.blog.common.model.Options;
import com.zdk.blog.common.service.LogsService;
import com.zdk.blog.common.service.OptionsService;
import com.zdk.blog.common.utils.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zdk
 * @date 2021/8/13 10:02
 */
@Api("系统设置")
@Controller
@RequestMapping(value = "/admin/setting")
public class SettingController extends CommonController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SettingController.class);
    @Autowired
    private OptionsService optionsService;

    @Autowired
    private LogsService logsService;

    @ApiOperation("设置页")
    @GetMapping(value = "")
    public String index(Model model){
        List<Options> list = optionsService.list();
        HashMap<String, String> options = new HashMap<>(16);
        list.forEach(option->options.put(option.getName(), option.getValue()));
        model.addAttribute("options",options);
        return "admin/setting";
    }

    @ApiOperation("保存全局设置和个性化设置")
    @PostMapping(value = "/globalAndIndividual")
    @ResponseBody
    public ApiResponse saveGlobal(){
        Map<String, String[]> parameterMap = request.getParameterMap();
        HashMap<String, String> param = new HashMap<>(6);
        parameterMap.forEach((key,value)->{
            if (value[0]!=null){
                param.put(key,value[0]);
            }
        });
        return optionsService.updateGlobalAndIndividualSetting(param);
    }
}
