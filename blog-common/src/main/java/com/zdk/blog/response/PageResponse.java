package com.zdk.blog.response;

import com.github.pagehelper.PageInfo;
import com.zdk.blog.model.BaseModel;
import com.zdk.blog.response.BaseResponse;
import com.zdk.blog.vo.BaseVO;

import java.util.List;

/**
 * @author zhangdikai
 * @date 2022-07-28 14:46
 */
public class PageResponse<T> extends ApiResponse {
    private static final long serialVersionUID = 1L;
    private Integer currPage;
    private Integer pageSize;
    private Integer totalCount;
    private Integer totalPage;
    private List<? extends BaseVO> data;

    public static <T> PageResponse<T> pageResponse(List<? extends BaseVO> data, PageInfo<? extends BaseModel> pageInfo){
        PageResponse<T> response = new PageResponse<>();
        response.setTotalCount((int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize());
        response.data = data;
        return response;
    }

    public static <T> PageResponse<T> pageResponse(List<? extends BaseVO> data){
        PageResponse<T> response = new PageResponse<>();
        response.data = data;
        return response;
    }


    public void setTotalCount(Integer totalCount, Integer currPage, Integer pageSize) {
        this.totalCount = totalCount;
        this.currPage = currPage;
        this.pageSize = pageSize;
        this.totalPage = (totalCount + pageSize - 1) / pageSize;
    }

    public void setTotalCount(Integer totalCount, Integer length) {
        this.totalCount = totalCount;
        this.totalPage = (totalCount + length - 1) / length;
    }

    public Integer getCurrPage() {
        return this.currPage;
    }

    public void setCurrPage(Integer currPage) {
        this.currPage = currPage;
    }

    public Integer getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<? extends BaseVO> getData() {
        return this.data;
    }

    public void setData(List<? extends BaseVO> data) {
        this.data = data;
    }

    public void setData(List<? extends BaseVO> data, PageInfo<? extends BaseModel> pageInfo) {
        this.setTotalCount(Long.valueOf(pageInfo.getTotal()).intValue(), pageInfo.getPageNum(), pageInfo.getPageSize());
        this.data = data;
    }

    public Integer getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}

