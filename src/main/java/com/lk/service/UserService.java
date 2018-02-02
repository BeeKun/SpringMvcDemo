package com.lk.service;

import com.lk.bean.UserDO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface UserService {

     UserDO login(String userName,String password);

     int insertUser(UserDO userDO);

     int updateByPrimaryKeySelective(UserDO userDO);
}
