package com.zky.crm.settings.service;

import com.zky.crm.settings.domain.User;

import java.util.List;

public interface UserService {
    User login(String name, String password, String ip);
    User login(String name, String password);

    List<User> getUserList();
}
