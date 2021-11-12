package com.zky.crm.workbench.web.controller;

import com.zky.crm.settings.domain.User;
import com.zky.crm.settings.service.DicService;
import com.zky.crm.settings.service.UserService;
import com.zky.crm.settings.service.impl.DicServiceImpl;
import com.zky.crm.settings.service.impl.UserServiceImpl;
import com.zky.crm.util.DateTimeUtil;
import com.zky.crm.util.PrintJson;
import com.zky.crm.util.ServiceFactory;
import com.zky.crm.util.UUIDUtil;
import com.zky.crm.workbench.domain.*;
import com.zky.crm.workbench.service.ActivityService;
import com.zky.crm.workbench.service.ClueService;
import com.zky.crm.workbench.service.impl.ActivityServiceImpl;
import com.zky.crm.workbench.service.impl.ClueServiceImpl;
import com.zky.crm.workbench.vo.PaginationVO;
import org.apache.ibatis.annotations.Param;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClueController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = request.getServletPath();

        if ("/workbench/clue/getUserList.do".equals(path)) {
            getUserList(request, response);
        }

        if ("/workbench/clue/saveClue.do".equals(path)) {
            saveClue(request, response);
        }

        if ("/workbench/clue/pageList.do".equals(path)) {
            getPageList(request, response);
        }

        if ("/workbench/clue/detail.do".equals(path)) {
            getDetailByid(request, response);
        }

        if ("/workbench/clue/getActivityByClueID.do".equals(path)) {
            getActivityByClueID(request, response);
        }

        if ("/workbench/clue/deleteRelation.do".equals(path)) {
            deleteRelation(request, response);
        }

        if ("/workbench/clue/getAcivityList.do".equals(path)) {
            getAcivityList(request, response);
        }

        if ("/workbench/clue/addRelation.do".equals(path)) {
            addRelation(request, response);
        }

        if ("/workbench/clue/getAcivityListByName.do".equals(path)) {
            getAcivityListByName(request, response);
        }

        if ("/workbench/clue/convert.do".equals(path)) {
            convert(request, response);
        }

    }

    private void convert(HttpServletRequest request, HttpServletResponse response) {
        String flag = request.getParameter("flag");
        String clueID = request.getParameter("clueID");

        Tran tran = null;

        if("1".equals(flag)){
            /*创建临时交易列表*/
            tran = new Tran();
            String money = request.getParameter("amountOfMoney");
            String name = request.getParameter("tradeName");
            String expectedDate = request.getParameter("expectedClosingDate");
            String stage = request.getParameter("stage");
            String activityID = request.getParameter("activityID");

            tran.setId(UUIDUtil.getUUID());
            tran.setMoney(money);
            tran.setName(name);
            tran.setExpectedDate(expectedDate);
            tran.setStage(stage);
            tran.setActivityId(activityID);
            User user = (User) request.getSession().getAttribute("user");
            tran.setCreateBy(user.getName());
            tran.setCreateTime(DateTimeUtil.getSysTime());

        }
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean result = cs.convert(clueID, tran);


    }

    private void getAcivityListByName(HttpServletRequest request, HttpServletResponse response) {
        String aName = request.getParameter("aName");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> activityList = as.getAcivityListByName(aName);
        PrintJson.printJsonObj(response, activityList);


    }

    private void addRelation(HttpServletRequest request, HttpServletResponse response) {
        String clueID = request.getParameter("clueID");
        String[] activityIDs = request.getParameterValues("activityID");

        List<ClueActivityRelation> carList = new ArrayList<>();

        for(String activityID: activityIDs){
            ClueActivityRelation car = new ClueActivityRelation();
            car.setId(UUIDUtil.getUUID());
            car.setClueId(clueID);
            car.setActivityId(activityID);
            carList.add(car);
            System.out.println(activityID);
        }

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag = cs.addRelation(carList);

        PrintJson.printJsonFlag(response, flag);

    }

    private void getAcivityList(HttpServletRequest request, HttpServletResponse response) {
        String clueID = request.getParameter("clueID");
        String aName = request.getParameter("aName");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> activityList = as.getAcivityList(clueID, aName);

        PrintJson.printJsonObj(response, activityList);


    }

    private void deleteRelation(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Boolean flag = cs.deleteRelation(id);

        PrintJson.printJsonFlag(response, flag);

    }

    private void getActivityByClueID(HttpServletRequest request, HttpServletResponse response) {
        String clueID = request.getParameter("clueID");
        System.out.println(clueID);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> activityList = as.getActivityByClueID(clueID);

        PrintJson.printJsonObj(response ,activityList);


    }

    private void getDetailByid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Clue clue = cs.getDetailByid(id);

        request.setAttribute("clue", clue);

        request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request, response);
    }



    private void getPageList(HttpServletRequest request, HttpServletResponse response) {

       String pageNoStr = request.getParameter("pageNo");
       int pageNo = Integer.valueOf(pageNoStr);
       String pageSizeStr = request.getParameter("pageSize");
       int pageSize = Integer.valueOf(pageSizeStr);
       int skipCount = (pageNo-1) * pageSize;

       String fullname = request.getParameter("fullname");

       String company = request.getParameter("company");
       String phone = request.getParameter("phone");
       String source = request.getParameter("source");
       String owner = request.getParameter("owner");
       String mphone = request.getParameter("mphone");
       String state = request.getParameter("state");

       Map<String, Object> map = new HashMap<>();
       map.put("skipCount", skipCount);
       map.put("pageSize", pageSize);
       map.put("fullname", fullname);
       map.put("company", company);
       map.put("phone", phone);
       map.put("source", source);
       map.put("owner", owner);
       map.put("mphone", mphone);
       map.put("state", state);



       ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
       PaginationVO<Clue> vo = cs.getPageList(map);

       PrintJson.printJsonObj(response ,vo);

    }

    private void saveClue(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");

        String id = UUIDUtil.getUUID();
        String fullname = request.getParameter("fullname");
        String appellation = request.getParameter("appellation");
        String owner = request.getParameter("owner");
        String company = request.getParameter("company");
        String job = request.getParameter("job");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String website = request.getParameter("website");
        String mphone = request.getParameter("mphone");
        String state = request.getParameter("state");
        String source = request.getParameter("source");
        String createBy = user.getName();
        String createTime = DateTimeUtil.getSysTime();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        String address = request.getParameter("address");

        Clue clue = new Clue();
        clue.setId(id);
        clue.setFullname(fullname);
        clue.setAppellation(appellation);
        clue.setOwner(owner);
        clue.setCompany(company);
        clue.setJob(job);
        clue.setEmail(email);
        clue.setPhone(phone);
        clue.setWebsite(website);
        clue.setMphone(mphone);
        clue.setState(state);
        clue.setSource(source);
        clue.setCreateBy(createBy);
        clue.setCreateTime(createTime);
        clue.setDescription(description);
        clue.setContactSummary(contactSummary);
        clue.setNextContactTime(nextContactTime);
        clue.setAddress(address);

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        int count = cs.saveClue(clue);
        boolean flag = false;
        if(count == 1){
            flag = true;
        }

        PrintJson.printJsonFlag(response, flag);

    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> userList = us.getUserList();
        PrintJson.printJsonObj(response, userList);
    }
}

