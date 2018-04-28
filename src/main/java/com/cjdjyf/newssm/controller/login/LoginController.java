package com.cjdjyf.newssm.controller.login;

import com.cjdjyf.newssm.base.ResultBean;
import com.cjdjyf.newssm.constant.SysConstant;
import com.cjdjyf.newssm.pojo.sys.SysPermission;
import com.cjdjyf.newssm.service.login.LoginService;
import com.cjdjyf.newssm.service.sys.SysPermissionService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author : cjd
 * @description : 登录控制器
 * @date : 11:32 2018/3/10
 */
@Controller
@RequestMapping("/sys")
public class LoginController {
    @Autowired
    LoginService loginService;
    @Autowired
    SysPermissionService sysPermissionService;

    /**
     * @Author : cjd
     * @Description : 登录页面
     * @params :[]
     * @return :java.lang.String
     * @Date : 10:51 2018/4/19
     */
    @GetMapping("/login")
    public String loginView() {
        //登录成功后默认返回主页
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "index";
        }
        return "login";
    }

    /**
     * @Author : cjd
     * @Description : 主页面 获取根目录菜单
     * @params :[request]
     * @return :java.lang.String
     * @Date : 10:51 2018/4/19
     */
    @GetMapping("/index")
    public String indexView(HttpServletRequest request) {
        //找到根目录下的菜单
        SysPermission sysPermission = new SysPermission(SysConstant.SOURCE_MENU_ID);
        sysPermission.setMenuFlag("1");
        List<SysPermission> sysPermissionList = sysPermissionService.findAll(sysPermission);
        request.setAttribute("menus",sysPermissionList);
        return "index";
    }

    /**
     * @Author : cjd
     * @Description : 首页显示
     * @params :[]
     * @return :java.lang.String
     * @Date : 10:52 2018/4/19
     */
    @GetMapping("/home")
    public String homeView() {
        return "home";
    }

    /**
     * @return : com.cjdjyf.newssm.base.ResultBean<java.lang.String>
     * @author : cjd
     * @description : 登录验证
     * @params : [userName, password, request]
     * @date : 23:59 2018/3/10
     */
    @PostMapping("/login")
    @ResponseBody
    public ResultBean<String> loginCheck(String userName, String password, HttpServletRequest request) {
        return new ResultBean<String>(loginService.login(userName, password, request));
    }

    /**
     * @return : com.cjdjyf.newssm.base.ResultBean<java.lang.String>
     * @author : cjd
     * @description : 登出
     * @params : [request]
     * @date : 17:50 2018/3/11
     */
    @PostMapping("logout")
    @ResponseBody
    public ResultBean<String> loginCheck(HttpServletRequest request) {
        return new ResultBean<String>(loginService.logout(request));
    }

}
