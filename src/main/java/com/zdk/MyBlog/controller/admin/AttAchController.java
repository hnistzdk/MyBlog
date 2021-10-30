package com.zdk.MyBlog.controller.admin;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageInfo;
import com.upyun.Result;
import com.upyun.UpException;
import com.zdk.MyBlog.constant.ErrorConstant;
import com.zdk.MyBlog.constant.SuccessConstant;
import com.zdk.MyBlog.controller.BaseController;
import com.zdk.MyBlog.model.dto.UpYunDto;
import com.zdk.MyBlog.model.pojo.Attach;
import com.zdk.MyBlog.service.attach.AttachService;
import com.zdk.MyBlog.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zdk
 * @date 2021/8/13 9:27
 */
@Api("附件")
@Controller
@RequestMapping("/admin/attach")
public class AttAchController extends BaseController {
    public static final String CLASSPATH = TaleUtils.getUplodFilePath();
    private static final Logger LOGGER = LoggerFactory.getLogger(AttAchController.class);
    private static final String URL_PREFIX = "http://zdk-blog-image.test.upcdn.net";
    @Autowired
    private AttachService attachService;
    @Autowired
    private UpYunUtil upYunUtil;

    @ApiOperation("附件管理首页")
    @GetMapping("")
    public String index(Model model,
                        @RequestParam(name = "page",required = false, defaultValue = "1") Integer pageNum,
                        @RequestParam(name = "limit",required = false, defaultValue = "5")Integer pageSize){

        PageInfo<Attach> attaches = attachService.getAttachPage(pageNum, pageSize, getLoginUser());
        System.out.println("attaches.getList().size() = " + attaches.getList().size());
        model.addAttribute("attaches", attaches);
        return "admin/attach";
    }

    @ApiOperation("多文件上传")
    @PostMapping("/upload")
    @ResponseBody
    public ApiResponse upload(@RequestParam(name = "file")MultipartFile[] files) throws IOException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        List<byte[]> uploadFiles = new ArrayList<>();
        List<String> uploadFilesName = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = UUID.captchaChar(10, true) +file.getOriginalFilename();
            uploadFiles.add(file.getBytes());
            uploadFilesName.add(fileName);
        }
        List<Result> results = upYunUtil.uploadFiles(uploadFiles, uploadFilesName);
        List<Attach> attaches = new ArrayList<>();
        Attach attach = new Attach();
        for (Result result : results) {
            UpYunDto upYunDto = MyBeanUtil.jsonObjectToBean(JSONUtil.parseObj(result.getMsg()), UpYunDto.class);
            attach.setId(null).setFileName(upYunDto.getUrl().substring(upYunDto.getUrl().lastIndexOf("/"))).
                    setFileKey(URL_PREFIX + upYunDto.getUrl()).setFileType(upYunDto.getUrl().substring(upYunDto.getUrl().lastIndexOf(".")+1))
                    .setAuthorId(getLoginUser().getId());
            attaches.add(attach);
        }
        return ApiResponse.result(attachService.batchSave(attaches), SuccessConstant.Att.UPLOAD_FILE_SUCCESS,ErrorConstant.Att.UPLOAD_FILE_FAIL);
    }

    @ApiOperation("删除附件")
    @PostMapping("/delete")
    @ResponseBody
    public ApiResponse delete(Integer id) throws UpException, IOException {
        Boolean result = attachService.deleteAttachById(id);
        return ApiResponse.result(result,SuccessConstant.Att.DELETE_ATT_SUCCESS,ErrorConstant.Common.DELETE_FAIL);
    }


}
