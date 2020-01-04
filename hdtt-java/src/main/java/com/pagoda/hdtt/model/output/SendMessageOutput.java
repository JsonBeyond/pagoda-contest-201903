package com.pagoda.hdtt.model.output;

import com.pagoda.hdtt.aotogen.Question;
import lombok.Data;

import java.util.List;

/**
 * @Author xieluxin
 * @Date 2020/1/4 13:27
 * @Version 1.0
 */
@Data
public class SendMessageOutput {
    private String replyMessage;
    private List<Question> questionList;
    private String id;
}