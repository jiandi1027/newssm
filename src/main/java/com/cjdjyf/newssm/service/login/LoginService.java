package com.cjdjyf.newssm.service.login;

import com.cjdjyf.newssm.service.sys.SysAccountService;
import com.cjdjyf.newssm.utils.MyUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : cjd
 * @description : 登录验证Service
 * @date : 13:56 2018/3/10
 */
@Service
public class LoginService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SysAccountService sysAccountService;
    /**
     * @Author : cjd
     * @Description : 登录验证
     * @params :[userName, password, request]
     * @return :java.lang.String
     * @Date : 9:49 2018/4/19
     */
    public String login(String userName, String password, HttpServletRequest request) {
        logger.info("-------------验证登录-------------userName:{}", userName);
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        Subject subject = SecurityUtils.getSubject();
        /*登录失败会抛出异常 在AOP处理了*/
        if (!subject.isAuthenticated()) {
            subject.login(token);
        }
        subject.login(token);
        logger.info("-------------登录成功-------------userName:{}", userName);
        request.getSession().setAttribute("user",sysAccountService.findByName(userName));
        return "登录成功";
    }

    /**
     * @Author : cjd
     * @Description : 退出登录
     * @params :[request]
     * @return :java.lang.String
     * @Date : 9:50 2018/4/19
     */
    public String logout(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            logger.debug("用户{}退出", MyUtils.getSysAccount());
            subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
        }
        return "退出失败";
    }
}
