package com.zky.crm.workbench.service;

import com.zky.crm.workbench.domain.Activity;
import com.zky.crm.workbench.domain.ActivityRemark;
import com.zky.crm.workbench.vo.PaginationVO;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    int saveActivity(Activity Activity);


    PaginationVO<Activity> pageList(Map<String, Object> map);

    Boolean deleteList(String[] ids);

    Activity getActivityByid(String id);

    int updateActivity(Activity Activity);


    Map<String, Object> getDetailByid(String id);

    List<ActivityRemark> getRemarkByid(String id);

    List<Activity> getActivityByClueID(String clueID);

    List<Activity> getAcivityList(String clueID, String aName);

    List<Activity> getAcivityListByName(String aName);
}
