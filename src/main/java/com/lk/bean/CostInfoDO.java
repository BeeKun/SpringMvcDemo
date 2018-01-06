package com.lk.bean;

import java.io.Serializable;
import java.util.Date;

public class CostInfoDO implements Serializable {

    private static final long serialVersionUID = 42L;

    private String id;

    private int costMoney;

    private String costDescription;

    private String costAddress;

    private Date costTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCostMoney() {
        return costMoney;
    }

    public void setCostMoney(int costMoney) {
        this.costMoney = costMoney;
    }

    public String getCostDescription() {
        return costDescription;
    }

    public void setCostDescription(String costDescription) {
        this.costDescription = costDescription;
    }

    public String getCostAddress() {
        return costAddress;
    }

    public void setCostAddress(String costAddress) {
        this.costAddress = costAddress;
    }

    public Date getCostTime() {
        return costTime;
    }

    public void setCostTime(Date costTime) {
        this.costTime = costTime;
    }
}
