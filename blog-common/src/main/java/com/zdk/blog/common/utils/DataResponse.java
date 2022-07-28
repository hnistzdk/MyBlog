package com.zdk.blog.common.utils;

import com.zdk.blog.common.vo.BaseVO;

/**
 * @author zhangdikai
 * @date 2022-07-28 14:45
 */
public class DataResponse extends BaseResponse {
    private static final long serialVersionUID = 1L;
    private BaseVO data;

    public BaseVO getData() {
        return this.data;
    }

    public void setData(BaseVO data) {
        this.data = data;
    }
}

