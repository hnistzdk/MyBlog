package com.zdk.blog.request.common;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Description
 * @Author zdk
 * @Date 2022/8/7 12:55
 */
public abstract class BaseDeleteRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;
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

    public String getUpdateBy() {
        return this.updateBy;
    }

    public Date getUpdateDt() {
        return this.updateDt;
    }

    public String getIsDelete() {
        return this.isDelete;
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
}
