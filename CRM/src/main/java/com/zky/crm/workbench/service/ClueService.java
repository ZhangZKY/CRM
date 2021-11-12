package com.zky.crm.workbench.service;

import com.zky.crm.workbench.domain.Activity;
import com.zky.crm.workbench.domain.Clue;
import com.zky.crm.workbench.domain.ClueActivityRelation;
import com.zky.crm.workbench.domain.Tran;
import com.zky.crm.workbench.vo.PaginationVO;

import java.util.List;
import java.util.Map;

public interface ClueService {
    int saveClue(Clue clue);

    PaginationVO<Clue> getPageList(Map<String, Object> map);

    Clue getDetailByid(String id);

    Boolean deleteRelation(String id);


    boolean addRelation(List<ClueActivityRelation> carList);

    boolean convert(String clueID, Tran tran);
}
