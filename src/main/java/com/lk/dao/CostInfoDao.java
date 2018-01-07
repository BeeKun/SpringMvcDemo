package com.lk.dao;

import com.lk.bean.CostInfoDO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CostInfoDao {

    List<CostInfoDO> selectCostInfoByMap(Map<String, Object> reqMap);

    int updateCostInfoByMap(Map<String, Object> reqMap);

    int addCostInfo(Map<String, Object> reqMap);
}
