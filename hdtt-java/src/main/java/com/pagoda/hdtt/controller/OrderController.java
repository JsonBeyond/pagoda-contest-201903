package com.pagoda.hdtt.controller;

import com.jfinal.plugin.activerecord.Page;
import com.pagoda.hdtt.aotogen.MsgTemplate;
import com.pagoda.hdtt.aotogen.Order;
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
        Order order = Order.dao.findById(id);
        if(BeanUtil.checkIsEmpty(order)){
            throw new ServiceException("未找到对应订单!");
        }
        successResponse(order);
    }

    /**
     * 查询订单列表
     */
    public void queryOrderList(Integer page,Integer pageSize,Integer userId){
        String sqlExceptSelect = "from order where 1=1 ";
        List<Object> params = new ArrayList<>();
        if(BeanUtil.checkIsNotEmpty(userId)){
            sqlExceptSelect += " and userId =?";
            params.add(userId);
        }
        sqlExceptSelect += " order by id desc";

        Page<Order> paginate = Order.dao.paginate(page, pageSize, "select *", sqlExceptSelect, params.toArray());
        successResponse(paginate);
    }
}