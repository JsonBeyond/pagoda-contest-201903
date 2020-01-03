package com.pagoda.hdtt.model.enums;

/**
 * 外部第三方接口枚举类
 * @Author xieluxin
 * @Date 2020/1/3 9:47
 * @Version 1.0
 */
public enum  HttpInterfaceEnum {
    //例:
    GET_TOKEN("http://erp-g2.erptest.pagoda.com.cn/erp_intfc/token/getToken", "查询token");

    private String url;
    private String desc;

    HttpInterfaceEnum(String url, String desc) {
        this.url = url;
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public String getDesc() {
        return desc;
    }
}