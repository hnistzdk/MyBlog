package com.zdk.MyBlog.service.options;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdk.MyBlog.mapper.OptionsMapper;
import com.zdk.MyBlog.model.pojo.Options;
import com.zdk.MyBlog.model.pojo.User;
import com.zdk.MyBlog.utils.ApiResponse;
import com.zdk.MyBlog.utils.ParaValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author zdk
 * @date 2021/8/12 22:37
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OptionsServiceImpl extends ServiceImpl<OptionsMapper, Options> implements OptionsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OptionsServiceImpl.class);
    @Autowired
    private OptionsMapper optionsMapper;

    @Override
    public PageInfo<Options> getOptionsPage(Integer pageNumber, Integer pageSize, String keywords){
        PageHelper.startPage(pageNumber, pageSize);
        return new PageInfo<>(lambdaQuery().list());
    }

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
