package com.lk.service.impl;

import com.lk.bean.UserDO;
import com.lk.dao.UserDao;
import com.lk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户实现类
 * @author likun
 * @date 2018-02-09
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    /**
     * 查询方法获取登录信息,由redis管理缓存
     * @param userName
     * @param password
     * @return
     */
    @Override
    @Cacheable(cacheNames="user",key="#userName")
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public UserDO login(String userName , String password) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("account",userName);
        map.put("password",password);
        UserDO userDO = new UserDO();
        try{
            userDO = userDao.getUser(map);
            System.out.println("从数据库中取值...");
        }catch (Exception e){
            e.printStackTrace();
        }
        return userDO;
    }

    /**
     * 新增用户信息
     * @param userDO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertUser(UserDO userDO) {
        return userDao.insert(userDO);
    }

    /**
     * 登录用户更新方法,并删除缓存
     * @param userDO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = "user",key = "#userDO")
    public int updateByPrimaryKeySelective(UserDO userDO) {
        return userDao.updateByPrimaryKeySelective(userDO);
    }
}
