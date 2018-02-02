package com.lk.controller;

import com.lk.bean.Constant;
import com.lk.bean.DTO.UserDTO;
import com.lk.bean.UserDO;
import com.lk.service.UserService;
import com.lk.shiro.token.manager.TokenManager;
import com.lk.util.MD5Util;
import com.lk.util.RedisCache;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
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

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/signUp")
    public String signUp(){
        return "signUp";
    }

        @RequestMapping("/home")
    public String home(){
            System.out.println("======================");
        return "home";
    }

    @RequestMapping("/signUpWithInfo")
    @ResponseBody
    public ModelMap signUpWithInfo(UserDTO userDTO){
        ModelMap map = new ModelMap();
        //将用户输入的密码加密
        String passwordLocked =  MD5Util.encodeByMD5(userDTO.getPassword());
        UserDO user = new UserDO();
        BeanUtils.copyProperties(userDTO,user);
        user.setPassword(passwordLocked);
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

    @RequestMapping("/loginByUser")
    @ResponseBody
    public ModelMap loginByUser(UserDO user,Boolean rememberMe) {
        ModelMap map = new ModelMap();
        try {
            TokenManager.login(user,rememberMe);
            map.put(Constant.DATA_CODE,Constant.SUCCESS_CODE);
            map.put(Constant.DATA_MSG,Constant.LOGIN_SUCCESS);
            return map;
        }catch (DisabledAccountException e) {
            map.put(Constant.DATA_CODE,Constant.FAIL_CODE);
            map.put(Constant.DATA_MSG, "帐号已经禁用。");
        } catch (Exception e) {
            map.put(Constant.DATA_CODE,Constant.FAIL_CODE);
            map.put(Constant.DATA_MSG, "帐号或密码错误");
        }
        return map;
    }
}
