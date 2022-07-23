package com.zdk.blog.common.config;

import com.UpYun;
import com.upyun.FormUploader;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author zdk
 * @Date 2022/7/23 13:50
 */
@Configuration
@ConfigurationProperties(prefix = "up-yun")
@EnableConfigurationProperties(UpYunConfig.class)
public class UpYunConfig {
    private String bucketName;
    private String userName;
    private String password;
    private String saveKey;
    private String imageParam;


    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSaveKey() {
        return saveKey;
    }

    public void setSaveKey(String saveKey) {
        this.saveKey = saveKey;
    }

    public String getImageParam() {
        return imageParam;
    }

    public void setImageParam(String imageParam) {
        this.imageParam = imageParam;
    }

    @Bean
    public FormUploader getFormUploader(){
        return new FormUploader(bucketName, userName, password);
    }

    @Bean
    public UpYun getUpYun(){
        return new UpYun(bucketName, userName, password);
    }
}
