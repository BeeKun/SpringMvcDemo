package com.lk.service;

import com.lk.bean.CostInfoDO;

import java.util.List;
import java.util.Map;

/**
 * 消费明细Service
 * @author likun
 * @date 2018-01-06
 */
public interface CostInfoService {

    List<CostInfoDO> selectCostInfoByMap(Map<String,Object> reqMap);

    int updateCostInfoByMap(Map<String,Object> reqMap);

    int addCostInfo(Map<String,Object> reqMap);
}
