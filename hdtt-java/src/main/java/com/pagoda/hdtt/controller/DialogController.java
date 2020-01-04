package com.pagoda.hdtt.controller;

import com.google.common.base.Joiner;
import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.pagoda.hdtt.aotogen.Dialog;
import com.pagoda.hdtt.aotogen.DialogRecord;
import com.pagoda.hdtt.aotogen.Question;
import com.pagoda.hdtt.common.util.BeanUtil;
import com.pagoda.hdtt.invoke.client.TulingClient;
import com.pagoda.hdtt.model.output.SendMessageOutput;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import sun.rmi.runtime.Log;
import com.pagoda.hdtt.websocket.ChannelSupervise;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 对话咨询 controller
 * @Author xieluxin
 * @Date 2020/1/4 10:23
 * @Version 1.0
 */
@Slf4j
public class DialogController extends BaseAPIController{

    public  void sendMessage(String message,Integer userId){
        insertHistory(message,userId,"user",null);
        String replyMessage = "";
        if(BeanUtil.checkIsEmpty(message)){
            replyMessage = "我是机器人果果，恭候您多时，很高兴为您服务！您可以发送文字告诉果果您要咨询的问题喔~";
        }else{
            //1.调用图灵接口 发送消息并获取回复内容
            replyMessage = TulingClient.sendTulingMessage(message);
        }
        replyMessage = "为了省着点用,返回模拟回复";

        SendMessageOutput output = new SendMessageOutput();
        //封装关联的问题列表
        List<Question> questionList = Question.dao.find("SELECT * FROM question ORDER BY rand() LIMIT 3");
        List<Integer> relationIdList = questionList.stream().map(question -> question.getId()).collect(Collectors.toList());
        insertHistory(replyMessage,userId,"tuling", relationIdList);
        output.setReplyMessage(replyMessage);
        output.setQuestionList(questionList);
        successResponse(output);
    }


    /**
     * 查询当前会话的历史记录
     * @param userId
     */
    public void queryHistory(Integer userId){
        List<Record> dialogList = Db.find("select * from dialog_record where creator=? order by createDate asc", userId);
        for (Record record : dialogList) {
            List<Question> questionList = Question.dao.find("SELECT q.* from dialog_record dr JOIN question q ON FIND_IN_SET(q.id,dr.relationQuestionId ) where dr.id=?", record.getInt("id"));
            record.set("questionList",questionList);
        }
        successResponse(dialogList);
    }

    /**
     * 插入聊天记录
     * @param message
     * @param creator
     * @param createType
     */
    public static void insertHistory(String message,Integer creator,String createType,List<Integer> relationIdList){

        if(BeanUtil.checkIsEmpty(message)){
            return;
        }
        DialogRecord record = new DialogRecord();
        record.setCreateType(createType);
        record.setCreator(creator);
        record.setMessage(message);

        if(BeanUtil.checkIsNotEmpty(relationIdList)){
            String relationQuestionId = Joiner.on(",").join(relationIdList);
            record.setRelationQuestionId(relationQuestionId);
        }

        boolean save = record.save();
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