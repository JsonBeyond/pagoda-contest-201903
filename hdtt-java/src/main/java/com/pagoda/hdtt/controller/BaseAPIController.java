package com.pagoda.hdtt.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 基础父类Controller,用于定义所用controller都通用的方法
 * 2017年4月12日上午9:28:58
 */
public class BaseAPIController extends Controller {

    private String jsonPara = "";
    private boolean isReadData = false;

    /**
     * 获取前端传入的json Obj参数
     * @return obj
     */
    public <T> T getJsonModel(Class<T> clazz) {
        String jsonString = HttpKit.readData(getRequest());
        T t = JSON.parseObject(jsonString, clazz);
        return t;
    }

//    @Override
//    public String getPara(String name) {
//        String strPara = super.getPara(name);
//        if (strPara != null)
//            return strPara;
//
//        if (StringUtils.isEmpty(jsonPara) && isReadData == false) {
//            jsonPara = HttpKit.readData(getRequest());
//            isReadData = true;
//        }
//
//        if (StringUtils.isEmpty(jsonPara)) {
//            return null;
//        }
//        JSONObject paramObject = JSONObject.parseObject(jsonPara);
//        return paramObject.getString(name);
//    }
//
//    @Override
//    public String getPara(String name, String defaultValue) {
//        String strPara = super.getPara(name, defaultValue);
//        if (strPara != null) {
//            return strPara;
//        }
//        if (StringUtils.isEmpty(jsonPara) && isReadData == false) {
//            jsonPara = HttpKit.readData(getRequest());
//            isReadData = true;
//        }
//
//        if (StringUtils.isEmpty(jsonPara)) {
//            return defaultValue;
//        }
//        JSONObject paramObject = JSONObject.parseObject(jsonPara);
//        String strValue = paramObject.getString(name);
//        if (strValue == null) {
//            return defaultValue;
//        }
//        return strValue;
//    }
//
//
//    @Override
//    public Integer getParaToInt(String name) {
//        Integer para = super.getParaToInt(name);
//        if (para != null)
//            return para;
//
//        if (StringUtils.isEmpty(jsonPara) && isReadData == false) {
//            jsonPara = HttpKit.readData(getRequest());
//            isReadData = true;
//        }
//
//        if (StringUtils.isEmpty(jsonPara)) {
//            return null;
//        }
//        JSONObject paramObject = JSONObject.parseObject(jsonPara);
//        return paramObject.getInteger(name);
//    }
//
//    @Override
//    public Date getParaToDate(String name) {
//        Date para = super.getParaToDate(name);
//        if (para != null) {
//            return para;
//        }
//        if (StringUtils.isEmpty(jsonPara) && isReadData == false) {
//            jsonPara = HttpKit.readData(getRequest());
//            isReadData = true;
//        }
//
//        if (StringUtils.isEmpty(jsonPara)) {
//            return null;
//        }
//        JSONObject paramObject = JSONObject.parseObject(jsonPara);
//        return paramObject.getDate(name);
//    }
//
//    @Override
//    public Integer getParaToInt(String name, Integer defaultValue) {
//        Integer para = super.getParaToInt(name, defaultValue);
//        if (para != null) {
//            return para;
//        }
//        if (StringUtils.isEmpty(jsonPara) && isReadData == false) {
//            jsonPara = HttpKit.readData(getRequest());
//            isReadData = true;
//        }
//
//        if (StringUtils.isEmpty(jsonPara)) {
//            return null;
//        }
//        JSONObject paramObject = JSONObject.parseObject(jsonPara);
//        return paramObject.getInteger(name);
//    }
//

    /**
     * 响应标准JSON数据到前端
     *
     * @author谢璐鑫
     */
    public void successResponse(Object result) {
        Map<String, Object> map = new HashMap<>();
        if (result == null) {
            result = "";
        }

        map.put("status", 10000);
        map.put("msg", "成功");
        map.put("result", result);
        renderJson(map);
    }


    /**
     * 相应错误信息到前端
     *
     * @param msg
     */
    public void errorResponse(String msg) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (msg == null) {
            msg = "";
        }
        map.put("status", "10001");//错误代码
        map.put("msg", msg);
        map.put("result", null);
        renderJson(map);
    }

//    /**
//     * 获取前端传来的数组对象并响应成Bean列表(用于批量接收Bean)
//     *
//     * @param beanClass
//     * @param beanName
//     * @return
//     * @author 谢璐鑫
//     */
//    public <T> List<T> getBeans(Class<T> beanClass, String beanName) {
//        List<String> nos = getModelsNoList(beanName);
//        List<T> list = new ArrayList<T>();
//        for (String no : nos) {
//            T bean = getBean(beanClass, beanName + "[" + no + "]");
//            if (bean != null) {
//                list.add(bean);
//            }
//        }
//        return list;
//    }
//
//    /**
//     * 获取前端传来的数组对象并响应成Model列表(用于批量接收Model)
//     *
//     * @param modelClass model类class对象
//     * @param modelName  接收前缀
//     *                   调用示例 : List<SGoodsStore> storeList = getModels(SGoodsStore.class, "goodsStore");
//     *                   前端传: goodsStore[0].字段1;goodsStore[0].字段2;goodsStore[1].字段1;goodsStore[1].字段2.....等
//     * @return
//     * @author xlx
//     */
//    public <T> List<T> getModels(Class<T> modelClass, String modelName) {
//        List<String> nos = getModelsNoList(modelName);
//        List<T> list = new ArrayList<T>();
//        for (String no : nos) {
//            T bean = getModel(modelClass, modelName + "[" + no + "]");
//            if (bean != null) {
//                list.add(bean);
//            }
//        }
//        return list;
//    }
//
//
//    /**
//     * 提取model对象数组的标号
//     *
//     * @param modelName
//     * @return
//     * @author xlx
//     */
//    private List<String> getModelsNoList(String modelName) {
//        // 提取下标
//        List<String> list = new ArrayList<String>();
//        String modelNameAndLeft = modelName + "[";
//        Map<String, String[]> parasMap = getRequest().getParameterMap();
//        for (Map.Entry<String, String[]> e : parasMap.entrySet()) {
//            String paraKey = e.getKey();
//            if (paraKey.startsWith(modelNameAndLeft)) {
//                String no = paraKey.substring(paraKey.indexOf("[") + 1,
//                        paraKey.indexOf("]"));
//                if (!list.contains(no)) {
//                    list.add(no);
//                }
//            }
//        }
//        Collections.sort(list);
//        return list;
//    }

    /**
     * 获取服务器的 URL
     */
    public String getServerUrl() {
        String basePath = this.getRequest().getScheme() + "://" + this.getRequest().getServerName() + ":" + this.getRequest().getServerPort();
        return basePath;
    }
}
