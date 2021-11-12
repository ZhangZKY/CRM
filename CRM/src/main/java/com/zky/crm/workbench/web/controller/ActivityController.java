package com.zky.crm.workbench.web.controller;

import com.zky.crm.settings.domain.User;
import com.zky.crm.settings.service.UserService;
import com.zky.crm.settings.service.impl.UserServiceImpl;
import com.zky.crm.util.*;
import com.zky.crm.workbench.domain.Activity;
import com.zky.crm.workbench.domain.ActivityRemark;
import com.zky.crm.workbench.service.ActivityService;
import com.zky.crm.workbench.service.impl.ActivityServiceImpl;
import com.zky.crm.workbench.vo.PaginationVO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = request.getServletPath();
        if("/workbench/activity/getUserList.do".equals(path)){
            getUserList(request, response);
        }

        if("/workbench/activity/saveActivity.do".equals(path)){
            saveActivity(request, response);
        }

        if("/workbench/activity/pageList.do".equals(path)){
            pageList(request, response);
        }

        if("/workbench/activity/getActivityUserListByid.do".equals(path)){
            getActivityUserListByid(request,response);

        }

        if("/workbench/activity/deleteList.do".equals(path)){
            deleteList(request,response);
        }

        if("/workbench/activity/updateActivity.do".equals(path)){
            updateActivity(request,response);
        }


        if("/workbench/activity/detail.do".equals(path)){
            getDetailByid(request,response);
        }

        if("/workbench/activity/remark.do".equals(path)){
            getRemarkByid(request,response);
        }

    }

    private void getRemarkByid(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<ActivityRemark> remarkList = as.getRemarkByid(id);
        PrintJson.printJsonObj(response, remarkList);
    }


    private void getDetailByid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Map<String, Object> map = as.getDetailByid(id);


        request.setAttribute("map", map);
        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request,response);

    }

    private void updateActivity(HttpServletRequest request, HttpServletResponse response) {
        Activity activity = new Activity();

        String id = request.getParameter("id");
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User)request.getSession(false).getAttribute("user")).getName();

        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setEditTime(editTime);
        activity.setEditBy(editBy);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        int count = as.updateActivity(activity);

        boolean flag = true;

        if(count != 1){
            flag = false;
        }

        PrintJson.printJsonFlag(response, flag);
    }


    private void getActivityUserListByid(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> userList = us.getUserList();

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Activity activity = as.getActivityByid(id);

        Map map = new HashMap();
        map.put("userList", userList);
        map.put("activity", activity);

        PrintJson.printJsonObj(response, map);

    }

    private void deleteList(HttpServletRequest request, HttpServletResponse response) {
        String[] ids = request.getParameterValues("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Boolean flag = as.deleteList(ids);
        PrintJson.printJsonFlag(response, flag);


    }

    public void pageList(HttpServletRequest request, HttpServletResponse response){
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String pageNoStr = request.getParameter("pageNo");
        int pageNo = Integer.valueOf(pageNoStr);
        String pageSizeStr = request.getParameter("pageSize");
        int pageSize = Integer.valueOf(pageSizeStr);

        int skipCount = (pageNo-1)*pageSize;

        Map<String, Object> map = new HashMap<>();
        map.put("owner", owner);
        map.put("name", name);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("skipCount", skipCount);
        map.put("pageSize", pageSize);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        PaginationVO<Activity> vo = as.pageList(map);

        PrintJson.printJsonObj(response,vo);
    }



    public void saveActivity(HttpServletRequest request, HttpServletResponse response){
        Activity activity = new Activity();

        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession(false).getAttribute("user")).getName();

        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        int count = as.saveActivity(activity);

        boolean flag = true;

        if(count != 1){
            flag = false;
        }

        PrintJson.printJsonFlag(response, flag);

    }


    public void getUserList(HttpServletRequest request, HttpServletResponse response){
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> userList = us.getUserList();
        PrintJson.printJsonObj(response, userList);
    }
}
