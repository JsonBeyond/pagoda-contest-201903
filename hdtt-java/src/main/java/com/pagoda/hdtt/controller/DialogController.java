package com.pagoda.hdtt.controller;

import com.jfinal.plugin.activerecord.Db;
import com.pagoda.hdtt.aotogen.Question;
import com.pagoda.hdtt.invoke.client.TulingClient;
import com.pagoda.hdtt.model.output.SendMessageOutput;

import java.util.List;

/**
 * 对话咨询 controller
 * @Author xieluxin
 * @Date 2020/1/4 10:23
 * @Version 1.0
 */
public class DialogController extends BaseAPIController{

    public  void sendMessage(String message){
        SendMessageOutput output = new SendMessageOutput();
        //1.调用图灵接口 发送消息并获取回复内容
//        String test = TulingClient.sendTulingMessage(message);
        String test = "为了省着点用,返回模拟回复";
        //封装关联的问题列表
        Db.find("SELECT * FROM question ORDER BY rand() LIMIT 1;");
        List<Question> questionList = Question.dao.find("SELECT * FROM question ORDER BY rand() LIMIT 3");
        output.setReplyMessage(test);
        output.setQuestionList(questionList);
        successResponse(output);
    }
}