package com.zdk.blog.request.common;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Description
 * @Author zdk
 * @Date 2022/8/7 12:57
 */
public class MultipleDeleteRequest extends BaseDeleteRequest {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(
            name = "id",
            value = "需要删除的id",
            example = "1002",
            dataType = "long",
            required = true
    )
    private List<Long> ids;

    public List<Long> getIds() {
        return this.ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
