package com.zdk.blog.common.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author zhangdikai
 * @date 2022-07-28 14:47
 */
public abstract class BaseModel {
    private static final long serialVersionUID = 1L;
    private String createBy;
    @JSONField(
            format = "yyyy-MM-dd HH:mm:ss"
    )
    private Date createDt;
    private String updateBy;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JSONField(
            format = "yyyy-MM-dd HH:mm:ss"
    )
    private Date updateDt;
    @TableLogic
    private String isDelete;
    private Long sortOrder;
    @Version
    private Long rowVersion;

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
