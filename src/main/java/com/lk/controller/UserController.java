package com.lk.controller;

import com.lk.bean.Constant;
import com.lk.bean.DTO.UserDTO;
import com.lk.bean.UserDO;
import com.lk.service.UserService;
import com.lk.shiro.token.manager.TokenManager;
import com.lk.util.MD5Util;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.DisabledAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * 最基本的登录注册操作
 * @author likun
 * @version V1.0
 * @Title: com.lk.controller
 * @date 2018/1/12 9:55
 *                             _ooOoo_
 *                            o8888888o
 *                            88" . "88
 *                            (| -_- |)
 *                            O\  =  /O
 *                         ____/`---'\____
 *                       .'  \\|     |//  `.
 *                      /  \\|||  :  |||//  \
 *                     /  _||||| -:- |||||-  \
 *                     |   | \\\  -  /// |   |
 *                     | \_|  ''\---/''  |   |
 *                     \  .-\__  `-`  ___/-. /
 *                   ___`. .'  /--.--\  `. . __
 *                ."" '<  `.___\_<|>_/___.'  >'"".
 *               | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *               \  \ `-.   \_ __\ /__ _/   .-` /  /
 *          ======`-.____`-.___\_____/___.-`____.-'======
 *                             `=---='
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *                     佛祖保佑        永无BUG
 *            佛曰:
 *                   写字楼里写字间，写字间里程序员；
 *                   程序人员写程序，又拿程序换酒钱。
 *                   酒醒只在网上坐，酒醉还来网下眠；
 *                   酒醉酒醒日复日，网上网下年复年。
 *                   但愿老死电脑间，不愿鞠躬老板前；
 *                   奔驰宝马贵者趣，公交自行程序员。
 *                   别人笑我忒疯癫，我笑自己命太贱；
 *                   不见满街漂亮妹，哪个归得程序员？
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private UserService userService;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    /**
     * 登录页面
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    /**
     * 注册页面
     * @return
     */
    @RequestMapping("/signUp")
    public String signUp(){
        return "signUp";
    }

    /**
     * 主页
     * @return
     */
    @RequestMapping("/home")
    public String home(){
        return "home";
    }

    /**
     * 注册账号
     * @param userDTO
     * @return
     */
    @RequestMapping("/signUpWithInfo")
    @ResponseBody
    public ModelMap signUpWithInfo(UserDTO userDTO){
        ModelMap map = new ModelMap();
        //将用户输入的密码加密
        String passwordLocked =  MD5Util.encodeByMD5(userDTO.getPassword());
        UserDO user = new UserDO();
        BeanUtils.copyProperties(userDTO,user);
        user.setCreateTime(LocalDateTime.now());
        user.setPassword(passwordLocked);
        // 新增用户数据
        int count = userService.insertUser(user);
        if (count<1){
            map.put(Constant.DATA_CODE,Constant.FAIL_CODE);
            map.put(Constant.DATA_MSG,Constant.SIGN_UP_FAIL);
            return map;
        }
        map.put(Constant.DATA_CODE,Constant.SUCCESS_CODE);
        map.put(Constant.DATA_MSG,Constant.SIGN_UP_SUCCESS);
        return map;
    }

    /**
     * 登录,成功后将用户名存入redis缓存中
     * @param user
     * @param rememberMe
     * @return
     */
    @RequestMapping("/loginByUser")
    @ResponseBody
    public ModelMap loginByUser(UserDO user,Boolean rememberMe) {
        ModelMap map = new ModelMap();
        try {
            UserDO userDO = TokenManager.login(user,rememberMe);
           /**
            * 看网上的资料参差不齐,redis缓存中的数据到底是否可以直接覆盖更新,我没有一一实验,不过在opsForValue中
            * set方法是可以覆盖key的value的,不需要先调用delete方法
            */
            redisTemplate.opsForValue().set("account",userDO.getAccount());
            logger.debug("=========================="+ redisTemplate.opsForValue().get("account")+"==========================");
            map.put(Constant.DATA_CODE,Constant.SUCCESS_CODE);
            map.put(Constant.DATA_MSG,Constant.LOGIN_SUCCESS);
            return map;
        }catch (DisabledAccountException e) {
            map.put(Constant.DATA_CODE,Constant.FAIL_CODE);
            map.put(Constant.DATA_MSG, Constant.ACCOUNT_FORBIDDEN);
        } catch (AccountException e) {
            map.put(Constant.DATA_CODE,Constant.FAIL_CODE);
            map.put(Constant.DATA_MSG, Constant.LOGIN_FAIL);
        }
        return map;
    }
}
