package com.lk.dao;

import com.lk.bean.UserDO;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserDao {

     UserDO getUser(Map<String,Object> map);

}
