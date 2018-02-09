package com.lk.shiro.token;

import com.lk.bean.Constant;
import com.lk.bean.UserDO;
import com.lk.service.UserService;
import com.lk.util.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * 实现自定义的realm  用来登录的认证以及授权
 * @author likun
 * @version V1.0
 * @Title: com.lk.shiro.token.session
 * @date 2018/1/22 18:21
 */
public class UserRealm extends AuthorizingRealm{

    @Autowired
    private UserService userService;

    public UserRealm (){
        super();
    }

    /**
     * 授权方法
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 登录认证方法
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName =token.getPrincipal().toString();
        //得到密码
        String password = new String((char[])token.getCredentials());
        // 将密码加密传入后台
        password = MD5Util.encodeByMD5(password);
        UserDO user = userService.login(userName,password);
        if (Objects.isNull(user)){
            throw new AccountException(Constant.LOGIN_FAIL);
        }else if (UserDO.FORBID_STATUS.equals(user.getStatus())){
            throw new DisabledAccountException("账号已被禁用!");
        }else {
            //更新最后登录时间
            user.setLastLoginTime(LocalDateTime.now());
            int count = userService.updateByPrimaryKeySelective(user);
            if (count < 1 ){
                throw new AccountException(Constant.UPDATE_ACCOUNTLOGINTIMELATEST_FAIL);
            }
        }

        return new SimpleAuthenticationInfo(userName,password,getName());
    }

    /**
     * 清空当前用户权限信息
     */
    public  void clearCachedAuthorizationInfo() {
        PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
        SimplePrincipalCollection principals = new SimplePrincipalCollection(
                principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }
    /**
     * 指定principalCollection 清除
     */
    public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(
                principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }
}
