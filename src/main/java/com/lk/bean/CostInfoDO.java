package com.lk.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 消费明细记录bean
 * @author likun
 * @date 2018-02-07
 */
public class CostInfoDO implements Serializable {

    private static final long serialVersionUID = 42L;

    private String id;

    /**
     * 账户名称
     */
    private String account;

    /**
     * 消费金额(暂时设置成不允许小数)
     */
    private Integer costMoney;

    /**
     * 消费明细
     */
    private String costDescription;

    /**
     * 消费地点
     */
    private String costAddress;

    /**
     * 消费时间(为什么DateTimeFormat不起作用?)
     * 触发条件,首先是在页面请求的参数在bean中的转化,其次在applicationContext.xml中需要配置
     * format的bean
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "costTime", columnDefinition="TIMESTAMP", nullable = false)
    private LocalDateTime costTime;

    /**
     * 消费图片名称(文件保存在服务器上)
     */
    private String costPictureName;

    /**
     * 临时存储时间转换(不得已而为之,jsp没有找到方法处理LocalDateTime)
     */
    private String dateTemp;

    public String getDateTemp() {
        return dateTemp;
    }

    public void setDateTemp(String dateTemp) {
        this.dateTemp = dateTemp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCostMoney() {
        return costMoney;
    }

    public void setCostMoney(Integer costMoney) {
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

    public LocalDateTime getCostTime() {
        return costTime;
    }

    public void setCostTime(LocalDateTime costTime) {
        this.costTime = costTime;
    }

    public String getCostPictureName() {
        return costPictureName;
    }

    public void setCostPictureName(String costPictureName) {
        this.costPictureName = costPictureName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "CostInfoDO{" +
                "id='" + id + '\'' +
                ", account='" + account + '\'' +
                ", costMoney=" + costMoney +
                ", costDescription='" + costDescription + '\'' +
                ", costAddress='" + costAddress + '\'' +
                ", costTime=" + costTime +
                ", costPictureName='" + costPictureName + '\'' +
                '}';
    }
}
