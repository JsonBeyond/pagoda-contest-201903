package com.pagoda.hdtt.controller;

import com.jfinal.core.ActionKey;
import com.jfinal.plugin.activerecord.Page;
import com.pagoda.hdtt.aotogen.MsgTemplate;
import com.pagoda.hdtt.common.exception.ServiceException;
import com.pagoda.hdtt.common.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author xieluxin
 * @Date 2019/12/31 16:04
 * @Version 1.0
 */
@Slf4j
public class HelloController extends BaseAPIController {
    /**
     * 新增消息模板
     */
    public void saveTempldate(){
        MsgTemplate template = getJsonModel(MsgTemplate.class);
        boolean result = template.save();
        successResponse(result);
    }
    /**
     * 更新消息模板
     */
    public void updateTempldate(){
        MsgTemplate template = getJsonModel(MsgTemplate.class);
        boolean result = template.update();

        successResponse(result);
    }

    /**
     * 删除消息模板
     */
    public void deleteTempldate(Integer id, String str, Date nowDate){
        log.info("id={},str={},nowData={}",id,str,nowDate);
        MsgTemplate template = MsgTemplate.dao.findById(id);
        if(BeanUtil.checkIsEmpty(template)){
            throw new ServiceException("未找到对应模板!");
        }
        boolean result = template.delete();
//        boolean result = MsgTemplate.dao.deleteById(id);
        successResponse(result);
    }

    /**
     * 查询消息模板详情
     */
    public void queryTemplateById(Integer id){
        MsgTemplate template = MsgTemplate.dao.findById(id);
        if(BeanUtil.checkIsEmpty(template)){
            throw new ServiceException("未找到对应模板!");
        }
        successResponse(template);
    }

    /**
     * 查询模板列表
     */
    public void queryTemplateList(Integer page,Integer pageSize, String templateName){
        String sqlExceptSelect = "from  msg_template where 1=1 ";
        List<String> params = new ArrayList<>();
        if(BeanUtil.checkIsNotEmpty(templateName)){
            sqlExceptSelect += " and content like ?";
            params.add( "%" +templateName +"%");
        }
        sqlExceptSelect += " order by templateID desc";

        Page<MsgTemplate> paginate = MsgTemplate.dao.paginate(page, pageSize, "select *", sqlExceptSelect, params.toArray());
        successResponse(paginate);
    }
}