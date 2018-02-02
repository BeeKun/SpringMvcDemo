package com.lk.dao;

import com.lk.bean.UserDO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserDao {

     UserDO getUser(Map<String,Object> map);

     int deleteByPrimaryKey(Integer id);

     int insert(UserDO record);

     int insertSelective(UserDO record);

     UserDO selectByPrimaryKey(Integer id);

     int updateByPrimaryKeySelective(UserDO userDO);

     int updateByPrimaryKey(UserDO record);

}
