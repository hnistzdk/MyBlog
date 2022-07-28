package com.zdk.blog.common.request.common;

import java.io.Serializable;

/**
 * @author zhangdikai
 * @date 2022-07-28 14:52
 */
public class Pager implements Serializable {
    private static final long serialVersionUID = 1L;
    private int draw;
    private int pageSize = 10;
    private int totalCount;
    private Integer start = 0;
    private Integer end = 10;
    private int currPage = 1;
    private int totalPage;

    public void setCurrPage(int currPage) {
        if (currPage < 1) {
            currPage = 1;
        }

        this.start = (currPage - 1) * this.pageSize;
        this.end = this.start + this.pageSize;
        this.currPage = currPage;
    }

    public int getCurrPage() {
        return this.currPage;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        this.totalPage = (totalCount + this.pageSize - 1) / this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStart() {
        return this.start;
    }

    public Integer getEnd() {
        return this.end;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setStart(Integer start) {
        this.end = start + this.pageSize;
        this.start = start;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public int getDraw() {
        return this.draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
