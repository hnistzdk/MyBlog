package com.zdk.MyBlog.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zdk.MyBlog.constant.WebConst;
import com.zdk.MyBlog.model.pojo.User;
import com.zdk.MyBlog.utils.ControllerKit;
import com.zdk.MyBlog.utils.ParaValidator;
import com.zdk.MyBlog.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zdk
 * @date 2021/7/21 9:38
 */
public class BaseController {

    @Autowired
    RedisUtil redisUtil;

    public BaseController() {
    }

    public User getLoginUser(){
        Object user = redisUtil.hget(WebConst.USERINFO,WebConst.LOGIN_SESSION_KEY);
        return JSONUtil.toBean(JSONUtil.parseObj(user), User.class);
    }
    /**
     * 判断Object参数有效性
     * @param param
     */
    public boolean isOk(Object param){
        return ParaValidator.isOk(param);
    }
    /**
     * 判断Object参数是否无效
     */
    public boolean notOk(Object param){
        return ParaValidator.notOk(param);
    }

    /**
     * 判断上传文件是图片
     * @param contentType
     */
    public boolean isImage(String contentType){
        return ParaValidator.isImage(contentType);
    }
    /**
     * 判断上传文件不是图片
     * @param contentType
     */
    public boolean notImage(String contentType){
        return ParaValidator.notImage(contentType);
    }

    /**
     * 判断Object[]数组类型数据是否正确
     * @param param
     * @return
     */
    public boolean isOk(Object[] param){
        return ParaValidator.isOk(param);
    }
    /**
     * 判断Object[]数组类型数据不正确
     * @param param
     * @return
     */
    public boolean notOk(Object[] param){
        return ParaValidator.notOk(param);
    }


    /**
     * 获取Json数据
     * @return
     */
    public JSONObject getJSONObject(HttpServletRequest request) {
        return ControllerKit.getJSONObject(request);
    }
    /**
     * 获取Json数据转为JSonArray
     * @return
     */
    public JSONArray getJSONArray(HttpServletRequest request) {
        return ControllerKit.getJSONArray(request);
    }

    /**
     * 获取Json数据转为JSonArray
     * @return
     */
    public List<JSONObject> getJSONObjectList(HttpServletRequest request) {
        return ControllerKit.getJSONObjectList(request);
    }
    /**
     * 获取Json数据 转为Java List
     * @return
     */
    public <T> List<T> getJSONList(HttpServletRequest request,Class<T> clazz) {
        return ControllerKit.getJSONList(request,clazz);
    }
    /**
     * 根据key获取json数据 转为JSONObject
     * @param key
     * @return
     */

    public JSONObject getJSONObject(HttpServletRequest request,String key) {
        return ControllerKit.getJSONObject(request,key);
    }

    /**
     * 根据key获取json数据 转为JSONArray
     * @param key
     * @return
     */

    public JSONArray getJSONArray(HttpServletRequest request,String key) {
        return ControllerKit.getJSONArray(request,key);
    }
    /**
     * 根据key获取json数据 转为List<JSONObject>
     * @param key
     * @return
     */

    public List<JSONObject> getJSONObjectList(HttpServletRequest request,String key) {
        return ControllerKit.getJSONObjectList(request, key);
    }
    /**
     * 根据key获取json数据 转为java List
     * @param key
     * @return
     */

    public <T> List<T> getJSONList(HttpServletRequest request,String key,Class<T> clazz) {
        return ControllerKit.getJSONList(request,key,clazz);
    }
}
