package com.zdk.blog.request.common;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Description
 * @Author zdk
 * @Date 2022/8/7 12:55
 */
public class SingleDeleteRequest extends BaseDeleteRequest {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(
            name = "id",
            value = "需要删除的id",
            example = "1002",
            dataType = "long",
            required = true
    )
    private Long id;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
