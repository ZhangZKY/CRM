package com.zky.crm.settings.service.impl;

import com.zky.crm.settings.dao.DicTypeDao;
import com.zky.crm.settings.dao.DicValueDao;
import com.zky.crm.settings.domain.DicType;
import com.zky.crm.settings.domain.DicValue;
import com.zky.crm.settings.service.DicService;
import com.zky.crm.util.SqlSessionUtil;
import com.zky.crm.workbench.domain.Clue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DicServiceImpl implements DicService {
    DicTypeDao typeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    DicValueDao valueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);

    @Override
    public Map<String, List<DicValue>> getDic() {
        Map<String, List<DicValue>> map = new HashMap<>();

        //获取所有dic-type值
        List<DicType> typeList = typeDao.getDicType();

        //获取不同dic-ty对应的dic-value集合并封装为map集合
        for(DicType type:typeList){
            String code = type.getCode();
            List<DicValue> valueList = valueDao.getDicValue(code);
            map.put(code, valueList);
        }

        return map;
    }


}
