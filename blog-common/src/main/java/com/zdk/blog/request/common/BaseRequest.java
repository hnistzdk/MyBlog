package com.zdk.blog.request.common;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description
 * @Author zdk
 * @Date 2022/8/7 13:21
 */
public abstract class BaseRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(
            name = "appKey",
            value = "请求序号(nonce机制防重放攻击,建议使用UUID+时间戳)",
            dataType = "String"
    )
    private String appKey;
    @ApiModelProperty(
            hidden = true
    )
    private String appSecret;
    @ApiModelProperty(
            name = "requestId",
            value = "请求序号(nonce机制防重放攻击,建议使用UUID+时间戳)",
            dataType = "String"
    )
    private String requestId;
    @ApiModelProperty(
            name = "timestamp",
            value = "请求时间戳(timestamp机制防重放攻击,默认300s)",
            dataType = "Long"
    )
    private Long timestamp;
    @ApiModelProperty(
            name = "signature",
            value = "SHA256withRSA参数签名(此参数不作为源进行验签)",
            dataType = "String"
    )
    private String signature;

    public String getAppKey() {
        return this.appKey;
    }

    public String getAppSecret() {
        return this.appSecret;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }

    public String getSignature() {
        return this.signature;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
