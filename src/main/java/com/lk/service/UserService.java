package com.lk.service;

import com.lk.bean.UserDO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface UserService {

     @Cacheable
     UserDO getUser(Map<String,Object> map);

     int insertUser(UserDO userDO);
}
