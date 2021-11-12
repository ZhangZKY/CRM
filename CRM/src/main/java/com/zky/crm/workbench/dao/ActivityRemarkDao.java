package com.zky.crm.workbench.dao;

import com.zky.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {

    int deleteRemarkByIDs(String[] ids);

    int selectRemarkByIDs(String[] ids);


    List<ActivityRemark> getRemarkByid(String id);
}
