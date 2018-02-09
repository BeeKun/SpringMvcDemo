package com.lk.service;

import com.lk.bean.UserDO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 用户登录接口
 * @author likun
 * @date 2018-02-07
 */
public interface UserService {

     /**
      * 登录
      * @param userName
      * @param password
      * @return
      */
     UserDO login(String userName,String password);

     /**
      * 插入用户信息
      * @param userDO
      * @return
      */
     int insertUser(UserDO userDO);

     /**
      * 更新用户信息
      * @param userDO
      * @return
      */
     int updateByPrimaryKeySelective(UserDO userDO);
}
