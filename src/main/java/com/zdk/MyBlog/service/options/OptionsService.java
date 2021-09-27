package com.zdk.MyBlog.service.options;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zdk.MyBlog.model.pojo.Options;
import com.zdk.MyBlog.utils.ApiResponse;

import java.util.List;
import java.util.Map;

/**
 * @author zdk
 * @date 2021/8/12 22:37
 */
public interface OptionsService extends IService<Options> {

    /**
     * 网站设置分页
     * @param pageNumber
     * @param pageSize
     * @param keywords
     * @return
     */
    PageInfo<Options> getOptionsPage(Integer pageNumber, Integer pageSize, String keywords);

    /**
     * 更新全局设置
     * @param map
     * @return
     */
    ApiResponse updateGlobalAndIndividualSetting(Map<String, String> map);
}
