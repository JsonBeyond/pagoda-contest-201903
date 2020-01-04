package com.pagoda.hdtt;

import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.json.FastJsonFactory;
import com.jfinal.json.Json;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import com.pagoda.hdtt.aotogen._MappingKit;
import com.pagoda.hdtt.controller.HelloController;
import com.pagoda.hdtt.controller.LoginController;
import com.pagoda.hdtt.interceptor.CrossInterceptor;
import com.pagoda.hdtt.interceptor.GlobalExceptionInterceptor;

/**
 * @Author xieluxin
 * @Date 2019/12/31 16:01
 * @Version 1.0
 */
public class HdttConfig extends JFinalConfig {
    @Override
    public void configConstant(Constants me) {

        me.setEncoding("UTF-8");
        PropKit.use("config.properties");
		me.setDevMode(true);
        me.setViewType(ViewType.JSP);
//        me.setJsonFactory(new FastJsonFactory());
        FastJsonFactory factory = new FastJsonFactory();

    }

    @Override
    public void configRoute(Routes me) {
        me.add("/template", HelloController.class);
        me.add("/account", LoginController.class);
    }

    @Override
    public void configEngine(Engine me) {

    }

    @Override
    public void configPlugin(Plugins me) {
        C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password"),PropKit.get("jdbcDriver"));
        //ORM Activerecord
        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
        arp.setShowSql(true);
        _MappingKit.mapping(arp);
//        _MappingKit.mapping(arp);//交由_MappingKit类管理映射关系
//
//        XiaomuPlugin xm=new XiaomuPlugin();
//        me.add(xm);

        //加入插件管理器
        me.add(c3p0Plugin);
        me.add(arp);
    }

    @Override
    public void configInterceptor(Interceptors me) {
        //添加全局action层S拦截器
        me.add(new GlobalExceptionInterceptor());
        me.add(new CrossInterceptor());
    }

    @Override
    public void configHandler(Handlers me) {

    }

    public static void main(String[] args) {
        JFinal.start("WebRoot",9001,"/hdtt",5);
    }
}