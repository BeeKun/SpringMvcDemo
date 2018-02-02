package com.lk.shiro.token.manager;

import com.lk.bean.UserDO;
import com.lk.shiro.token.UserRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * shiro Token 管理工具
 * @author likun
 * @version V1.0
 * @Title: com.lk.shiro.session
 * @date 2018/1/22 18:19
 */
public class TokenManager {

    /**
     * 获取当前登录的用户对象
     * @return
     */
    public static UserDO getToken(){
        UserDO token = (UserDO) SecurityUtils.getSubject().getPrincipal();
        return token;
    }

    /**
     * 获取当前用户的session
     * @return
     */
    public static Session getSession(){
        return SecurityUtils.getSubject().getSession(false);
    }

    /**
     * 获取当前用户的登录名称
     * @return
     */
    public static String getUserName(){
        return getToken().getAccount();
    }

    public static int getId(){
        return getToken()==null?null:getToken().getId();
    }

    /**
     * 把值放入到当前登录用户的Session里
     * @param key
     * @param value
     */
    public static void setVal2Session(Object key ,Object value){
        getSession().setAttribute(key, value);
    }
    /**
     * 从当前登录用户的Session里取值
     * @param key
     * @return
     */
    public static Object getVal2Session(Object key){
        return getSession().getAttribute(key);
    }

    /**
     * 登录
     * @param user
     * @param rememberMe
     * @return
     */
    public static UserDO login(UserDO user,Boolean rememberMe){
        UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(),user.getPassword().toCharArray());
        token.setRememberMe(rememberMe);
        ensureUserIsLoggedOut();
        SecurityUtils.getSubject().login(token);
        return getToken();
    }

    /**
     * 判断用户是否已经登录
     * @return
     */
    public static boolean isLogin(){
        return null != SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 退出登录
     */
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    /**
     * 清空当前用户权限信息。
     * 目的：为了在判断权限的时候，再次会再次 <code>doGetAuthorizationInfo(...)  </code>方法。
     * ps：	当然你可以手动调用  <code> doGetAuthorizationInfo(...)  </code>方法。
     * 		这里只是说明下这个逻辑，当你清空了权限，<code> doGetAuthorizationInfo(...)  </code>就会被再次调用。
     */
    public static void clearNowUserAuth(){
        /**
         * 这里需要获取到shrio.xml 配置文件中，对Realm的实例化对象。才能调用到 Realm 父类的方法。
         *

         * 获取当前系统的Realm的实例化对象，方法一（通过 @link org.apache.shiro.web.mgt.DefaultWebSecurityManager 或者它的实现子类的{Collection<Realm> getRealms()}方法获取）。
         * 获取到的时候是一个集合。
         */
         //Collection<Realm>
         RealmSecurityManager securityManager =(RealmSecurityManager) SecurityUtils.getSecurityManager();
        UserRealm realm = (UserRealm)securityManager.getRealms().iterator().next();
        realm.clearCachedAuthorizationInfo();

    }

//    public static void clearUserAuthByUserId(Integer... userIds){
//        if(null == userIds || userIds.length == 0)	return ;
//        List<SimplePrincipalCollection> result = customSessionManager.getSimplePrincipalCollectionByUserId(userIds);
//    }

    private static void ensureUserIsLoggedOut() {
        try {
            //获取登录的用户信息
            if (!isLogin()){
                return;
            }
            //如果有用户登录则退出重新登录
            logout();
            //获取session如果没有则不创建create:false
            Session session = getSession();
            if (session == null) {
                return;
            }
            session.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
