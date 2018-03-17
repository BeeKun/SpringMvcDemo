package com.lk.service.impl;

import com.lk.bean.CostInfoDO;
import com.lk.dao.CostInfoDao;
import com.lk.service.CostInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class CostInfoServiceImpl implements CostInfoService {

    @Autowired
    private CostInfoDao costInfoDao;

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public List<CostInfoDO> selectCostInfoByMap(Map<String,Object> reqMap) {
        return costInfoDao.selectCostInfoByMap(reqMap);
    }

    @Override
    public int updateCostInfoByMap(Map<String, Object> reqMap) {
        return costInfoDao.updateCostInfoByMap(reqMap);
    }

    @Override
    public int addCostInfo(Map<String,Object> reqMap){
        return costInfoDao.addCostInfo(reqMap);
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public List<CostInfoDO> checkUploadPictureOrNot(Map<String, Object> reqMap) {
        return costInfoDao.checkUploadPictureOrNot(reqMap);
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public List<CostInfoDO> selectDailyCostMoney(String account) {
        Map<String,Object> reqMap = new HashMap<>(2);
        reqMap.put("account",account);
        return costInfoDao.selectDailyCostMoney(reqMap);
    }
}
