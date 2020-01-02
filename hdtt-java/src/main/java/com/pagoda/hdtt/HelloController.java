package com.pagoda.hdtt;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.pagoda.hdtt.common.model.MsgTemplate;

import java.util.List;

/**
 * @Author xieluxin
 * @Date 2019/12/31 16:04
 * @Version 1.0
 */
public class HelloController extends Controller {
    /**
     * 该方法会默认自动执行
     */
    public void index(){
		renderText("index.jsp");//返回文本
//        render("index.jsp");//返回逻辑视图
//        List<Record> msg_template = Db.findAll("msg_template");
        MsgTemplate msg_template = MsgTemplate.dao.findById(322);
        renderJson(msg_template);
    }
}