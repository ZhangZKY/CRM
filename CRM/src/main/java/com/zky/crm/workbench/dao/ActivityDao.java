package com.zky.crm.workbench.dao;

import com.zky.crm.workbench.domain.Activity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    int saveActivity(Activity activity);

    int updateActivity(Activity activity);

    List<Activity> getActivityList(Map<String, Object> map);

    int getTotal(Map<String, Object> map);


    int deleteByIDs(String[] ids);

    Activity getActivityByid(String id);


    Activity getActivityByidp(String id);

    List<Activity> getActivityByClueID(String clueID);

    List<Activity> getAcivityList(@Param("clueID") String clueID, @Param("aName") String aName);

    List<Activity> getAcivityListByName(String aName);
}
