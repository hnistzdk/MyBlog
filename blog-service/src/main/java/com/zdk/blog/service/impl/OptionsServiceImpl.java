package com.zdk.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdk.blog.model.Options;
import com.zdk.blog.service.OptionsService;
import com.zdk.blog.response.ApiResponse;
import com.zdk.blog.utils.ParaValidator;
import com.zdk.blog.mapper.OptionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author zdk
 * @date 2021/8/12 22:37
 */
@Service
public class OptionsServiceImpl extends ServiceImpl<OptionsMapper, Options> implements OptionsService, ParaValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(OptionsServiceImpl.class);


    @Override
    public PageInfo<Options> getOptionsPage(Integer pageNumber, Integer pageSize, String keywords){
        PageHelper.startPage(pageNumber, pageSize);
        return new PageInfo<>(lambdaQuery().list());
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    @Override
    public ApiResponse updateGlobalAndIndividualSetting(Map<String, String> map) {
        try {
            map.forEach((name,value)-> lambdaUpdate().eq(Options::getName,name).set(Options::getValue, value).update());
        }catch (Exception e){
            return ApiResponse.fail("修改失败");
        }
        return ApiResponse.success("修改成功");
    }
}
