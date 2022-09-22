package com.zdk.blog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.temporal.ChronoUnit;

/**
 * @Description
 * @Author zdk
 * @Date 2022/9/22 18:56
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
@EnableConfigurationProperties(JwtConfig.class)
public class JwtConfig {
    /**
     * rsa私钥
     */
    private String rsaPrivateKey;
    /**
     * rsa公钥
     */
    private String rsaPublicKey;

    /**
     * 记住我到期时间
     */
    private Long maxExpireTime = 300L;
    /**
     * 非记住我时间到期
     */
    private Long minExpireTime = 1L;

    /**
     * 时间单位
     */
    private ChronoUnit timeUnit = ChronoUnit.MINUTES;

    public String getRsaPrivateKey() {
        return rsaPrivateKey;
    }

    public void setRsaPrivateKey(String rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
    }

    public String getRsaPublicKey() {
        return rsaPublicKey;
    }

    public void setRsaPublicKey(String rsaPublicKey) {
        this.rsaPublicKey = rsaPublicKey;
    }

    public Long getMaxExpireTime() {
        return maxExpireTime;
    }

    public void setMaxExpireTime(Long maxExpireTime) {
        this.maxExpireTime = maxExpireTime;
    }

    public Long getMinExpireTime() {
        return minExpireTime;
    }

    public void setMinExpireTime(Long minExpireTime) {
        this.minExpireTime = minExpireTime;
    }

    public ChronoUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(ChronoUnit timeUnit) {
        this.timeUnit = timeUnit;
    }
}
