package com.zdk.blog.common.request.common;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author zhangdikai
 * @date 2022-07-28 14:23
 */
public class BaseSearchRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(
            name = "keywords",
            value = "搜索关键字",
            example = "关键字",
            dataType = "String"
    )
    protected String keywords;

    @ApiModelProperty(
            name = "pageSize",
            value = "每页大小(默认10)",
            example = "10",
            dataType = "Integer"
    )
    protected Integer pageSize = 10;
    @ApiModelProperty(
            name = "currPage",
            value = "当前页码(默认1)",
            example = "1",
            dataType = "Integer"
    )
    protected Integer currPage = 1;
    @ApiModelProperty(
            name = "sort",
            value = "排序字段，默认驼峰法转换字段名",
            example = "1",
            dataType = "String"
    )
    protected String sort;
    @ApiModelProperty(
            name = "order",
            value = "排序方式(desc|asc)",
            example = "1",
            dataType = "String"
    )
    protected String order;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrPage() {
        return currPage;
    }

    public void setCurrPage(Integer currPage) {
        this.currPage = currPage;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseSearchRequest that = (BaseSearchRequest) o;
        return Objects.equals(keywords, that.keywords) && Objects.equals(pageSize, that.pageSize) && Objects.equals(currPage, that.currPage) && Objects.equals(sort, that.sort) && Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keywords, pageSize, currPage, sort, order);
    }

    @Override
    public String toString() {
        return "BaseSearchRequest{" +
                "keywords='" + keywords + '\'' +
                ", pageSize=" + pageSize +
                ", currPage=" + currPage +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                '}';
    }
}
