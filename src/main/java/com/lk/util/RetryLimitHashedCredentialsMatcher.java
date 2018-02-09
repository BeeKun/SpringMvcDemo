package com.lk.util;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import javax.mail.Flags;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * token以及数据库中查询的info比较器
 * @author likun
 * @date 2018-02-08
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    /**
     * 在AuthenticatingRealm中,需要匹配提交的凭证和数据库中的是否匹配
     * 由于token中的密码是页面传递没有经过加密的,而数据库中的数据都是经过各种加密结果,所以匹配的时候需要处理
     * 这边提供了一个自定义的比较方式,也可以根据xml配置
     * @param token
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        // 获取token中的用户名
        String username = (String)token.getPrincipal();
        // 获取token中的密码
        String tokenPassword =  String.valueOf((char[]) token.getCredentials());
        boolean matches = true;

        //比较用户名
        if (!username.equals(info.getPrincipals().toString())) {
            matches = false;
            return matches;
        }

        //比较密码
        if (! MD5Util.validatePassword(info.getCredentials().toString(),tokenPassword)) {
            matches = false;
            return matches;
        }
        return matches;
    }
}
