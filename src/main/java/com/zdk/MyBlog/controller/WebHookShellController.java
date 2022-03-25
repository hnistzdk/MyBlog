package com.zdk.MyBlog.controller;

import com.zdk.MyBlog.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

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
     */
    public void executeShell() {
        try {
            ProcessBuilder pb = new ProcessBuilder(fileName);
            Process ps = pb.start();
            InputStream is = ps.getErrorStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                logger.error(line);
            }
            InputStream is1 = ps.getInputStream();
            BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));
            String line1;
            while ((line1 = br1.readLine()) != null) {
                logger.debug(line1);
            }
            int exitCode = ps.waitFor();
            logger.debug("exitCode->{}",exitCode);
        } catch (Exception e) {
            logger.error("调用发生异常");
            e.printStackTrace();
        }
    }
}
