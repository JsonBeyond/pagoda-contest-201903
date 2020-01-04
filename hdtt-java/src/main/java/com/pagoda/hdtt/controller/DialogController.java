package com.pagoda.hdtt.controller;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.pagoda.hdtt.aotogen.Question;
import com.pagoda.hdtt.common.util.BeanUtil;
import com.pagoda.hdtt.invoke.client.TulingClient;
import com.pagoda.hdtt.model.output.SendMessageOutput;
import com.pagoda.hdtt.websocket.ChannelSupervise;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;

/**
 * 对话咨询 controller
 * @Author xieluxin
 * @Date 2020/1/4 10:23
 * @Version 1.0
 */
public class DialogController extends BaseAPIController{

    public void sendMessage(String message){
        String test = "";
        if(BeanUtil.checkIsEmpty(message)){
            test = "我是机器人互动果果，恭候您多时，很高兴为您服务！您可以发送文字告诉果果您要咨询的问题喔~";
        }else{
            //1.调用图灵接口 发送消息并获取回复内容
//        test = TulingClient.sendTulingMessage(message);
        }
        test = "为了省着点用,返回模拟回复";
        SendMessageOutput output = new SendMessageOutput();
        //封装关联的问题列表
        Db.find("SELECT * FROM question ORDER BY rand() LIMIT 1;");
        List<Question> questionList = Question.dao.find("SELECT * FROM question ORDER BY rand() LIMIT 3");
        output.setReplyMessage(test);
        output.setQuestionList(questionList);
        successResponse(output);
    }

    public void sendAll(String message){
        SendMessageOutput output = new SendMessageOutput();
        output.setReplyMessage(message);
        TextWebSocketFrame tws = new TextWebSocketFrame(JSON.toJSONString(output));
        // 群发
        ChannelSupervise.send2All(tws);
        successResponse("success");
    }
}