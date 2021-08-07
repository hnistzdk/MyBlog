package com.zdk.MyBlog.utils;

import java.io.File;

/**
 * @author zdk
 * @date 2021/8/7 20:27
 */
public class UploadUtils {

    // 项目根路径下的目录  -- SpringBoot static 目录相当于是根路径下（SpringBoot 默认）
    public final static String IMG_PATH_PREFIX = "static/upload/image";

    public static File getImgDirFile(){

        // 构建上传文件的存放 "文件夹" 路径
        String fileDirPath = "src/main/resources/" + IMG_PATH_PREFIX;

        File fileDir = new File(fileDirPath);
        if(!fileDir.exists()){
            // 递归生成文件夹
            fileDir.mkdirs();
        }
        return fileDir;
    }
}
