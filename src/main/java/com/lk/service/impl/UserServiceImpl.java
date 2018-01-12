package com.lk.service.impl;

import com.lk.bean.UserDO;
import com.lk.dao.UserDao;
import com.lk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    public UserDO getUser(Map<String, Object> map) {
        UserDO userDO = new UserDO();
        try{
            userDO = userDao.getUser(map) ;
        }catch (Exception e){
            e.printStackTrace();
        }
        return userDO;
    }

    @Override
    public int insertUser(UserDO userDO) {
        return userDao.insert(userDO);
    }
}
