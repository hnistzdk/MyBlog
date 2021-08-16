package com.zdk.MyBlog.utils;

import com.upyun.FormUploader;
import com.upyun.Params;
import com.upyun.Result;
import com.zdk.MyBlog.constant.UpYunConst;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zdk
 * @date 2021/8/16 16:03
 */
public class UpYunUtil {
    public static Result testSync(byte[] file, String filename) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, SignatureException, InvalidKeyException {
        //初始化uploader
        FormUploader uploader = new FormUploader(UpYunConst.BUCKET_NAME, UpYunConst.USERNAME, UpYunConst.PASSWORD);

        //初始化参数组 Map
        final Map<String, Object> paramsMap = new HashMap<>();

        //添加 SAVE_KEY 参数
        //filename为文件名(例如：12345.jpg)
        paramsMap.put(Params.SAVE_KEY, UpYunConst.SAVE_KEY + filename);

        //添加同步上传作图参数 X_GMKERL_THUMB
        //限定图片宽度为 300px、锐化、压缩质量 80、存储为 png 格式（参数不区分先后顺序）

        paramsMap.put(Params.X_GMKERL_THUMB, UpYunConst.IMAGE_PARAMS);

        return uploader.upload(paramsMap, file);
    }
}
