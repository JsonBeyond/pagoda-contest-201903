package com.pagoda.hdtt.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author xieluxin
 * @Date 2020/1/3 11:16
 * @Version 1.0
 */
public class CrossInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        handler(inv.getController().getResponse());
        inv.invoke();

    }

    private void handler(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,HEAD,PUT,DELETE,OPTION");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
    }
}