package com.lk.service.impl;

import com.lk.bean.UserDO;
import com.lk.dao.UserDao;
import com.lk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    public UserDO getUser(Map<String, Object> map) {
        return userDao.getUser(map) ;
    }
}
