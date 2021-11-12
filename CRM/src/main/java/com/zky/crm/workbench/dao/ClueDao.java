package com.zky.crm.workbench.dao;

import com.zky.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueDao {

    int saveClue(Clue clue);

    int getTotal(Map<String, Object> map);

    List<Clue> getClueList(Map<String, Object> map);

    Clue getDetailByid(String id);


}
