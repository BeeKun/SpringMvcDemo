package com.lk.service;

import com.lk.bean.UserDO;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface UserService {

     UserDO getUser(Map<String,Object> map);
}
