package com.zky.crm.settings.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zky.crm.settings.domain.User;
import com.zky.crm.settings.service.UserService;
import com.zky.crm.settings.service.impl.UserServiceImpl;
import com.zky.crm.util.MD5Util;
import com.zky.crm.util.PrintJson;
import com.zky.crm.util.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Service;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");

        //loginPwd = MD5Util.getMD5(loginPwd);

        //String ip = request.getRemoteAddr();

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        try{
            User user = us.login(loginAct, loginPwd);

            request.getSession().setAttribute("user", user);

            PrintJson.printJsonFlag(response, true);

        }catch(RuntimeException e){
            String msg = e.getMessage();
            Map<String, Object> map = new HashMap<>();
            map.put("success", false);
            map.put("msg", msg);

            PrintJson.printJsonObj(response, map);
        }




    }
}
