package com.lk.dao;

import com.lk.bean.UserDO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 用户Dao层
 * @author likun
 */
@Repository
public interface UserDao {

     /**
      * 获取当前登录的用户
      * @param map
      * @return
      */
     UserDO getUser(Map<String,Object> map);

     /**
      * 删除user用户
      * @param id
      * @return
      */
     int deleteByPrimaryKey(Integer id);

     /**
      * 插入方法
      * @param record
      * @return
      */
     int insert(UserDO record);

     int insertSelective(UserDO record);

     UserDO selectByPrimaryKey(Integer id);

     /**
      * 更新用户信息
      * @param userDO
      * @return
      */
     int updateByPrimaryKeySelective(UserDO userDO);

     int updateByPrimaryKey(UserDO record);

}
