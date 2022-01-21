package com.zdk.MyBlog.controller;

import com.zdk.MyBlog.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

/**
 * @Description
 * @Author zdk
 * @Date 2022/1/21 21:14
 */
@RestController
public class WebHookShellController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(WebHookShellController.class);


    private String fileName = "/root/my-notes/notes-start.sh";

    private String token = "my-notes";

    /**
     * 请求
     * @param userAgent
     * @param giteeToken
     * @param giteeEvent
     * @return
     * @throws IOException
     */
    @PostMapping("/webhook/shell/note")
    public ApiResponse auto(@RequestHeader("User-Agent") String userAgent,
                            @RequestHeader("X-Gitee-Token") String giteeToken,
                            @RequestHeader("X-Gitee-Event") String giteeEvent) throws IOException {
        logger.info("User-Agent:{}", userAgent);
        logger.info("X-Gitee-Token:{}", giteeToken);
        logger.info("Gitee-Event:{}", giteeEvent);

        if("git-oschina-hook".equals(userAgent)
                && token.equals(giteeToken)){
            executeShell();
            return ApiResponse.success("调用脚本成功");
        }
        return ApiResponse.fail("非法调用");
    }


    /**
     * 执行脚本
     * @throws IOException
     */
    public void executeShell() throws IOException {
        File file = new File(fileName);
        if(!file.exists()) {
            logger.error("file {} not existed!", fileName);
            return;
        }
        //赋予755权限并调用
        ProcessBuilder processBuilder = new ProcessBuilder("/bin/chmod", "755", fileName);
        Process process = processBuilder.start();
        int runningStatus = 0;
        try {
            runningStatus = process.waitFor();
        } catch (InterruptedException e) {
            logger.error("shell", e);
        }

        if(runningStatus != 0) {
            logger.error("failed.");
        }else {
            logger.info("success.");
        }
    }
}
