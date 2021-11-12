package com.zky.crm.web.listener;

import com.zky.crm.settings.domain.DicValue;
import com.zky.crm.settings.service.DicService;
import com.zky.crm.settings.service.impl.DicServiceImpl;
import com.zky.crm.util.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SysInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("服务器缓存处理数据字典开始");
        ServletContext application = sce.getServletContext();
        DicService ds = (DicService) ServiceFactory.getService(new DicServiceImpl());

        Map<String, List<DicValue>> map = ds.getDic();

        Set<String> keySet = map.keySet();
        for(String k:keySet){
            List<DicValue> dic = map.get(k);
            application.setAttribute(k, dic);
        }

        System.out.println("服务器缓存处理数据字典结束！！！！！");


    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }



}
