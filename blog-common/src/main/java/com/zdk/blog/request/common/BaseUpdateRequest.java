package com.zdk.blog.request.common;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Description
 * @Author zdk
 * @Date 2022/8/7 12:57
 */
public class BaseUpdateRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(
            hidden = true
    )
    protected String updateBy;
    @ApiModelProperty(
            hidden = true
    )
    protected Date updateDt;
    protected Long rowVersion;

    public String getUpdateBy() {
        return this.updateBy;
    }

    public Date getUpdateDt() {
        return this.updateDt;
    }

    public Long getRowVersion() {
        return this.rowVersion;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

    public void setRowVersion(Long rowVersion) {
        this.rowVersion = rowVersion;
    }
}
