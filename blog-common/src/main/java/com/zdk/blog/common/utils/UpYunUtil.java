package com.zdk.blog.common.utils;

import com.UpYun;
import com.upyun.FormUploader;
import com.upyun.Params;
import com.upyun.Result;
import com.upyun.UpException;
import com.zdk.blog.common.config.UpYunConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zdk
 * @date 2021/8/16 16:03
 */
@Component
public class UpYunUtil {
    @Autowired
    private UpYunConfig upYunConfig;
    public Result uploadImage(byte[] file, String filename) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, SignatureException, InvalidKeyException {
        //初始化uploader
        FormUploader uploader = upYunConfig.getFormUploader();
        //初始化参数组 Map
        final Map<String, Object> paramsMap = new HashMap<>(4);

        //添加 SAVE_KEY 参数
        //filename为文件名(例如：12345.jpg)
        paramsMap.put(Params.SAVE_KEY, upYunConfig.getSaveKey() + filename);

        //添加同步上传作图参数 X_GMKERL_THUMB
        //限定图片宽度为 300px、锐化、压缩质量 80、存储为 png 格式（参数不区分先后顺序）

        paramsMap.put(Params.X_GMKERL_THUMB, upYunConfig.getImageParam());

        return uploader.upload(paramsMap, file);
    }

    public List<Result> uploadFiles(List<byte[]> fileList,List<String> fileName) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        List<Result> results = new ArrayList<>();
        FormUploader uploader = upYunConfig.getFormUploader();
        for (int i = 0; i < fileList.size(); i++) {
            Map<String, Object> paramsMap = new HashMap<>(4);
            paramsMap.put(Params.SAVE_KEY, upYunConfig.getSaveKey() + fileName.get(i));
            Result result = uploader.upload(paramsMap, fileList.get(i));
            results.add(result);
        }
        return results;
    }

    public Boolean deleteFile(String filePath) throws UpException, IOException {
        UpYun upYun = upYunConfig.getUpYun();
        //正确文件路径应该是zdk-blog-image(服务名称 bucketName)/(文件夹名 如果有的话)/文件名
        String trueDeletePath = filePath.substring(filePath.lastIndexOf("zdk"));
        return upYun.deleteFile(trueDeletePath,null );
    }
}
