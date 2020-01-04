package com.pagoda.hdtt.controller;

import com.jfinal.plugin.activerecord.Page;
import com.pagoda.hdtt.aotogen.HdOrder;
import com.pagoda.hdtt.common.exception.ServiceException;
import com.pagoda.hdtt.common.util.BeanUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author xieluxin
 * @Date 2020/1/4 13:26
 * @Version 1.0
 */
public class OrderController extends BaseAPIController {

    /**
     * 查询订单详情
     */
    public void queryOrderById(Integer id){
        HdOrder order = HdOrder.dao.findById(id);
        if(BeanUtil.checkIsEmpty(order)){
            throw new ServiceException("未找到对应订单!");
        }
        successResponse(order);
    }

    /**
     * 查询订单列表
     */
    public void queryOrderList(Integer page,Integer pageSize,Integer userId, String goodsName){
        String sqlExceptSelect = "from hd_order where 1=1 ";
        List<Object> params = new ArrayList<>();
        if(BeanUtil.checkIsNotEmpty(userId)){
            sqlExceptSelect += " and userId =?";
            params.add(userId);
        }
        if(BeanUtil.checkIsNotEmpty(goodsName)){
            sqlExceptSelect += " and goodsName like ?";
            params.add("%" + goodsName + " %");
        }
        sqlExceptSelect += " order by id desc";

        Page<HdOrder> paginate = HdOrder.dao.paginate(page, pageSize, "select *", sqlExceptSelect, params.toArray());
        successResponse(paginate);
    }
}