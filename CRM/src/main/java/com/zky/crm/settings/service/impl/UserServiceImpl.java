package com.zky.crm.settings.service.impl;

import com.zky.crm.exception.LoginException;
import com.zky.crm.settings.dao.UserDao;
import com.zky.crm.settings.domain.User;
import com.zky.crm.settings.service.UserService;
import com.zky.crm.util.DateTimeUtil;
import com.zky.crm.util.SqlSessionUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws RuntimeException{
        User user = userDao.login(loginAct, loginPwd);

        if(user == null){
            throw new LoginException("账号或密码错误！");
        }

        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if(expireTime.compareTo(currentTime) < 0){
            throw new LoginException("账号已失效！");
        }

        String lockState = user.getLockState();
        if("0".equals(lockState)){
            throw new LoginException("账号已锁定！");
        }

        String allowIPs = user.getAllowIps();
        if(!allowIPs.contains(ip)){
            throw new LoginException("ip地址受限，请联系管理员！");
        }

        return user;
    }


    @Override
    public User login(String loginAct, String loginPwd) throws RuntimeException{
        User user = userDao.login(loginAct, loginPwd);

        if(user == null){
            throw new LoginException("账号或密码错误！");
        }

        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if(expireTime.compareTo(currentTime) < 0){
            throw new LoginException("账号已失效！");
        }

        String lockState = user.getLockState();
        if("0".equals(lockState)){
            throw new LoginException("账号已锁定！");
        }


        return user;
    }

    @Override
    public List<User> getUserList() {
        List<User> userList = userDao.getUserList();
        return userList;
    }
}
