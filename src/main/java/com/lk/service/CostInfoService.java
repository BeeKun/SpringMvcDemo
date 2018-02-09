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

    /**
     * 新增消费信息
     * @param reqMap
     * @return
     */
    int addCostInfo(Map<String,Object> reqMap);

    /**
     * 校验是否有上传图片
     * @param reqMap
     * @return
     */
    List<CostInfoDO> checkUploadPictureOrNot (Map<String,Object> reqMap);

    /**
     * 获取每日金额总和
     * @param account
     * @return
     */
    List<CostInfoDO> selectDailyCostMoney(String account);
}
