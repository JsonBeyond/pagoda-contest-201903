package com.pagoda.hdtt.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.pagoda.hdtt.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author xieluxin
 * @Date 2020/1/2 11:13
 * @Version 1.0
 */
@Slf4j
public class GlobalExceptionInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        String errorMsg = null;
        Map<String, Object> map = new HashMap<>();
        map.put("status", 10001);
        try {
            inv.invoke();
        }catch (ServiceException e){
            log.warn(e.getMessage(), e);
            map.put("errorMsg",e.getMessage());
            inv.getController().renderJson(map);
        }catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("errorMsg","服务器内部错误！原因：" + e.getMessage());
            inv.getController().renderJson(map);
        }

    }
}