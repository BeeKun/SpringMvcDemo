package com.lk.dao;

import com.lk.bean.CostInfoDO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 消费信息Dao层
 * @author likun
 * @date 2018-02-08
 */
@Repository
public interface CostInfoDao {

    /**
     * 查询消费信息所有
     * @param reqMap
     * @return
     */
    List<CostInfoDO> selectCostInfoByMap(Map<String, Object> reqMap);

    int updateCostInfoByMap(Map<String, Object> reqMap);

    /**
     * 插入消费信息
     * @param reqMap
     * @return
     */
    int addCostInfo(Map<String, Object> reqMap);

    List<CostInfoDO> checkUploadPictureOrNot(Map<String, Object> reqMap);

    /**
     * 查询每日消费总和
     * @param reqMap
     * @return
     */
    List<CostInfoDO> selectDailyCostMoney(Map<String, Object> reqMap);
}
