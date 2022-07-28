package com.zdk.blog.api.controller;

import cn.hutool.json.JSONUtil;

import com.zdk.blog.common.constant.WebConst;
import com.zdk.blog.common.model.User;
import com.zdk.blog.common.request.common.SearchCondition;
import com.zdk.blog.common.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.Cookie;

/**
 * @author zdk
 * @date 2021/7/21 9:38
 * Controller层基础封装 基于CommonController
 */
public abstract class BaseController implements ParaValidator {
    protected SearchCondition searchCondition = new SearchCondition();
    protected BaseResponse baseResponse = new BaseResponse();
    protected DataResponse dataResponse = new DataResponse();
    protected PageResponse pageResponse = new PageResponse();

    public SearchCondition getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(SearchCondition searchCondition) {
        this.searchCondition = searchCondition;
    }

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public DataResponse getDataResponse() {
        return dataResponse;
    }

    public void setDataResponse(DataResponse dataResponse) {
        this.dataResponse = dataResponse;
    }

    public PageResponse getPageResponse() {
        return pageResponse;
    }

    public void setPageResponse(PageResponse pageResponse) {
        this.pageResponse = pageResponse;
    }
}
