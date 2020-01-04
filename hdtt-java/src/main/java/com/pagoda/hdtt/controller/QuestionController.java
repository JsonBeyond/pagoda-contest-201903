package com.pagoda.hdtt.controller;

import com.jfinal.plugin.activerecord.Page;
import com.pagoda.hdtt.aotogen.HdOrder;

/**
 * @ClassName QuestionController
 * @Description TODO
 * @Author Alex
 * @CreateDate 1/4/2020 4:36 PM
 * @Version 1.0
 */
public class QuestionController extends BaseAPIController {

    /**
     * 热门问题
     */
    public void listTopQuestions(Integer page, Integer pageSize) {
        String sqlExceptSelect = "FROM question ORDER BY weight DESC limit 8";
        Page<HdOrder> paginate = HdOrder.dao.paginate(page, pageSize, "select *", sqlExceptSelect);
        successResponse(paginate);
    }
}
