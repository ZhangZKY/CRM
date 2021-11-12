package com.zky.crm.workbench.service.impl;

import com.zky.crm.util.PrintJson;
import com.zky.crm.util.SqlSessionUtil;
import com.zky.crm.workbench.dao.ActivityDao;
import com.zky.crm.workbench.dao.ActivityRemarkDao;
import com.zky.crm.workbench.domain.Activity;
import com.zky.crm.workbench.domain.ActivityRemark;
import com.zky.crm.workbench.service.ActivityService;
import com.zky.crm.workbench.vo.PaginationVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {
    ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    ActivityRemarkDao remarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);

    @Override
    public int saveActivity(Activity activity) {

        int count = activityDao.saveActivity(activity);
        return count;
    }


    @Override
    public int updateActivity(Activity activity) {

        int count = activityDao.updateActivity(activity);
        return count;
    }

    @Override
    public Map<String, Object> getDetailByid(String id) {
        Map<String, Object> map = new HashMap<>();
        //获取activity信息
        Activity activity = activityDao.getActivityByidp(id);

        //返回map集合
        map.put("activity", activity);

        return map;
    }

    @Override
    public List<ActivityRemark> getRemarkByid(String id) {

        //获取remark信息
        List<ActivityRemark> remarkList = remarkDao.getRemarkByid(id);

        return remarkList;
    }

    @Override
    public List<Activity> getActivityByClueID(String clueID) {
        List<Activity> activityList = activityDao.getActivityByClueID(clueID);

        return activityList;
    }

    @Override
    public List<Activity> getAcivityList(String clueID, String aName) {
        List<Activity> activityList = activityDao.getAcivityList(clueID, aName);
        return activityList;
    }

    @Override
    public List<Activity> getAcivityListByName(String aName) {
        List<Activity> activityList = activityDao.getAcivityListByName(aName);
        return activityList;
    }


    @Override
    public PaginationVO<Activity> pageList(Map<String, Object> map) {

        int total = activityDao.getTotal(map);

        List<Activity> dataList = activityDao.getActivityList(map);

        PaginationVO vo = new PaginationVO();
        vo.setTotal(total);
        vo.setDataList(dataList);

        return vo;
    }

    @Override
    public Boolean deleteList(String[] ids) {
        ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
        ActivityRemarkDao remarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);

        boolean flag = false;
        int selectRemarkCount = remarkDao.selectRemarkByIDs(ids);
        int deleteRemarkCount = remarkDao.deleteRemarkByIDs(ids);

        int deleteCount = activityDao.deleteByIDs(ids);

        if((selectRemarkCount == deleteRemarkCount) && (ids.length == deleteCount)){
            flag = true;
        }

        return flag;
    }

    @Override
    public Activity getActivityByid(String id) {
        Activity activity = activityDao.getActivityByid(id);
        return activity;
    }


}
