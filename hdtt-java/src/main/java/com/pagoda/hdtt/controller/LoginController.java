package com.pagoda.hdtt.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.pagoda.hdtt.aotogen.User;
import com.pagoda.hdtt.common.ProjectConstant;
import com.pagoda.hdtt.common.exception.ServiceException;
import com.pagoda.hdtt.common.util.BeanUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @Author xieluxin
 * @Date 2020/1/2 11:41
 * @Version 1.0
 */
public class LoginController extends BaseAPIController {

    /**
     * 注册
     * @param phone 手机
     * @param password 密码
     */
    public void register(String phone,String password){
        User user = getJsonModel(User.class);

        Record oldUser = Db.findFirst("select * from user where phone=?", user.getStr("phone"));
        if(BeanUtil.checkIsNotEmpty(oldUser)){
            throw new ServiceException("该手机号已注册!");
        }
        boolean save = user.save();
        successResponse(save);
    }


    /**
     * 登陆
     */
    public void login(){
        JSONObject jsonObject = getJsonModel(JSONObject.class);
        String phone = jsonObject.getString("phone");
        String password = jsonObject.getString("password");

        Record user = Db.findFirst("select * from user where phone=?", phone);
        if(BeanUtil.checkIsEmpty(user)){
            throw new ServiceException("该账号还未注册!");
        }
        if(!StringUtils.equals(user.getStr("password"),password)){
            throw new ServiceException("密码错误!");
        }
        getRequest().getSession().setAttribute(ProjectConstant.SESSION_USER, user);
        successResponse(user);
    }

    /**
     * 注销
     * @param userId 用户id
     */
    public void logout(String userId){
        Object oldRecord = getRequest().getSession().getAttribute(ProjectConstant.SESSION_USER);
        if(BeanUtil.checkIsEmpty(oldRecord)){
            throw new RuntimeException("还未登陆,不能注销!");
        }
        getRequest().getSession().removeAttribute(ProjectConstant.SESSION_USER);
        successResponse(true);
    }
}