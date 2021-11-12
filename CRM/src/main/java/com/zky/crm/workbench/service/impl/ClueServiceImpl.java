package com.zky.crm.workbench.service.impl;

import com.zky.crm.util.SqlSessionUtil;
import com.zky.crm.workbench.dao.ClueActivityRelationDao;
import com.zky.crm.workbench.dao.ClueDao;
import com.zky.crm.workbench.domain.Activity;
import com.zky.crm.workbench.domain.Clue;
import com.zky.crm.workbench.domain.ClueActivityRelation;
import com.zky.crm.workbench.domain.Tran;
import com.zky.crm.workbench.service.ClueService;
import com.zky.crm.workbench.vo.PaginationVO;

import java.util.List;
import java.util.Map;

public class ClueServiceImpl implements ClueService {
    ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    ClueActivityRelationDao carDao = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);

    @Override
    public int saveClue(Clue clue) {
        int count = clueDao.saveClue(clue);
        return count;
    }

    @Override
    public PaginationVO<Clue> getPageList(Map<String, Object> map) {
        //获取线索总条数
        int count = clueDao.getTotal(map);

        //获取线索列表
        List<Clue> clueList = clueDao.getClueList(map);


        //创建PaginationVO对象并赋值

        PaginationVO<Clue> vo = new PaginationVO();
        vo.setTotal(count);
        vo.setDataList(clueList);

        return vo;
    }

    @Override
    public Clue getDetailByid(String id) {
        Clue clue = clueDao.getDetailByid(id);
        return clue;
    }

    @Override
    public Boolean deleteRelation(String id) {
        int count = carDao.deleteRelation(id);
        boolean flag = false;

        if(count == 1){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean addRelation(List<ClueActivityRelation> carList) {
        boolean flag = true;

        for(ClueActivityRelation car: carList){
            int count = carDao.addRelation(car);
            if(count != 1){
                flag = false;
            }
        }

        return flag;
    }

    @Override
    public boolean convert(String clueID, Tran tran) {
        String createBy = tran.getCreateBy();
        if(tran == null){

        }
        return false;
    }
}
