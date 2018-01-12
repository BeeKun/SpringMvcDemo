package com.lk.bean.DTO;

/**
 * @author likun
 * @version V1.0
 * @Title: com.lk.bean.DTO
 * @date 2018/1/12 11:23
 */
public class UserDTO {

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
}
