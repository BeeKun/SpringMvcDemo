package com.lk.bean;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户登录实体类
 * @author likun
 * @version V1.0
 * @Title: com.lk.bean
 * @date 2018/1/12 10:49
 */
public class UserDO implements Serializable {

    private static final long serialVersionUID = 34532345523432L;

    /**
     *0:禁止登录
     */
    public static final String FORBID_STATUS = "0";
    /**
     *1:允许登录
     */
    public static final String ALLOW_STATUS = "1";
    /**
     * 用户编号
     */
    private Integer id;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 用户密码
     */
    private String password;

    /**创建时间*/
    private LocalDateTime createTime;

    /**最后登录时间*/
    private LocalDateTime lastLoginTime;

    /**1:有效，0:禁止登录*/
    private String status;

    public UserDO(){

    }

    public UserDO(UserDO user) {
        this.id = user.getId();
        this.account = user.getAccount();
        this.password = user.getPassword();
        this.createTime = user.getCreateTime();
        this.lastLoginTime = user.getLastLoginTime();
        this.status = user.getStatus();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserDO{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", lastLoginTime=" + lastLoginTime +
                ", status='" + status + '\'' +
                '}';
    }
}
