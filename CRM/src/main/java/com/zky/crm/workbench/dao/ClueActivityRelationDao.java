package com.zky.crm.workbench.dao;

import com.zky.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {


    int deleteRelation(String id);


    int addRelation(ClueActivityRelation car);
}
