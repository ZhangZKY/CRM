package com.zky.crm.settings.dao;

import com.zky.crm.settings.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    User login(@Param("loginAct") String loginAct, @Param("loginPwd") String loginPwd);

    List<User> getUserList();
}
