package com.zdk.blog.request.common;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Description
 * @Author zdk
 * @Date 2022/8/7 12:56
 */
public class BaseCreateRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(
            hidden = true
    )
    protected String createBy;
    @ApiModelProperty(
            hidden = true
    )
    protected Date createDt;
    @ApiModelProperty(
            hidden = true
    )
    protected String updateBy;
    @ApiModelProperty(
            hidden = true
    )
    protected Date updateDt;
    @ApiModelProperty(
            hidden = true
    )
    protected String isDelete;
    @ApiModelProperty(
            hidden = true
    )
    protected Long sortOrder;
    @ApiModelProperty(
            hidden = true
    )
    protected Long rowVersion;

    public BaseCreateRequest() {
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public Date getCreateDt() {
        return this.createDt;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public Date getUpdateDt() {
        return this.updateDt;
    }

    public String getIsDelete() {
        return this.isDelete;
    }

    public Long getSortOrder() {
        return this.sortOrder;
    }

    public Long getRowVersion() {
        return this.rowVersion;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setRowVersion(Long rowVersion) {
        this.rowVersion = rowVersion;
    }
}
