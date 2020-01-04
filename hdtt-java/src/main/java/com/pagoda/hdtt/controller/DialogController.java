package com.pagoda.hdtt.controller;

import com.pagoda.hdtt.invoke.client.TulingClient;

/**
 * 对话咨询 controller
 * @Author xieluxin
 * @Date 2020/1/4 10:23
 * @Version 1.0
 */
public class DialogController extends BaseAPIController{

    public  void sendMessage(String message){
        Object test = TulingClient.sendTulingMessage(message);
        successResponse(test);
    }
}