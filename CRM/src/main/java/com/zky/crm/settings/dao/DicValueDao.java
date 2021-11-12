package com.zky.crm.settings.dao;

import com.zky.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueDao {
    List<DicValue> getDicValue(String code);
}
