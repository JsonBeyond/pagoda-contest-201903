package com.pagoda.hdtt.invoke.dto;

import lombok.Data;

/**
 * @Author xieluxin
 * @Date 2020/1/4 10:27
 * @Version 1.0
 */
@Data
public class TulingDialogInputDTO {
    /**
     * 输入类型:0-文本(默认)、1-图片、2-音频
     */
    private Integer reqType;
    private PerceptionDTO perception;
    private UserInfoDTO userInfo;
//    private String selfInfo;
}