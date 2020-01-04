package com.pagoda.hdtt.invoke.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pagoda.hdtt.common.HttpHelper;
import com.pagoda.hdtt.invoke.dto.InputTextDTO;
import com.pagoda.hdtt.invoke.dto.PerceptionDTO;
import com.pagoda.hdtt.invoke.dto.TulingDialogInputDTO;
import com.pagoda.hdtt.invoke.dto.UserInfoDTO;
import lombok.extern.slf4j.Slf4j;
import sun.reflect.generics.reflectiveObjects.LazyReflectiveObjectGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author xieluxin
 * @Date 2020/1/4 10:24
 * @Version 1.0
 */
@Slf4j
public class TulingClient {
    private static final String apiKey = "ed37911b03c14225bda284e742bef501";
    private static final String userId = "543806";
    private static final String url = "http://openapi.tuling123.com/openapi/api/v2";

    /**
     * 发送消息给图灵机器人
     * @param inputMessage
     * @return
     */
    public static String sendTulingMessage(String inputMessage){

        TulingDialogInputDTO inputDTO = new TulingDialogInputDTO();
        InputTextDTO textDTO = new InputTextDTO();
        textDTO.setText(inputMessage);
        PerceptionDTO perceptionDTO = new PerceptionDTO();
        perceptionDTO.setInputText(textDTO);
        inputDTO.setPerception(perceptionDTO);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setApiKey(apiKey);
        userInfoDTO.setUserId(userId);
        inputDTO.setUserInfo(userInfoDTO);
        inputDTO.setReqType(0);

        String callResult = HttpHelper.sendPost(url, JSON.toJSONString(inputDTO));
//        EhrBaseResultDTO baseResultDTO = JSON.parseObject(callResult, EhrBaseResultDTO.class);
//        if(!ProjectConstant.EHR_INTEFACE_SUCCESS_CODE.equals(baseResultDTO.getResultCode())){
//            throw new InvokeException("调用ehr接口返回错误,错误消息"+baseResultDTO.getErrorMsg());
//        }
//        List<EhrDeptVO> deptVOList = JSON.parseArray(JSON.toJSONString(baseResultDTO.getData()), EhrDeptVO.class);
        log.info("返回结果={}",callResult);
        JSONObject jsonObject = JSON.parseObject(callResult);
//        String string = jsonObject.getJSONArray("results").get(0).getJSONObject("values").getString("text");
        JSONArray jsonArray = jsonObject.getJSONArray("results");
        JSONObject object = (JSONObject) jsonArray.get(0);
        String string = object.getJSONObject("values").getString("text");
        return string;
    }
}