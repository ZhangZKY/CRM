package com.zky.crm.settings.service;

import com.zky.crm.settings.domain.DicValue;
import com.zky.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface DicService {
    Map<String, List<DicValue>> getDic();


}
