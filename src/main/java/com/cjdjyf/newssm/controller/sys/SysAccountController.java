package com.cjdjyf.newssm.controller.sys;

import com.cjdjyf.newssm.base.PageBean;
import com.cjdjyf.newssm.base.ResultBean;
import com.cjdjyf.newssm.pojo.sys.SysAccount;
import com.cjdjyf.newssm.service.sys.SysAccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : cjd
 * @description : 账号管理控制器
 * @date : 2018/4/24 11:18
 */
@Controller
@RequestMapping("/sys/sysAccount")
public class SysAccountController {
    @Autowired
    private SysAccountService sysAccountService;

    /**
     * @return :java.lang.String
     * @Author : cjd
     * @Description : 账号管理页面
     * @params :[]
     * @Date : 10:56 2018/4/19
     */
    @GetMapping("/list")
    public String list() {
        return "sys/sysAccount/sysAccountList";
    }

    /**
     * @return :java.lang.String
     * @Author : cjd
     * @Description : 账号编辑页面
     * @params :[id, request]
     * @Date : 10:56 2018/4/19
     */
    @GetMapping("/addList")
    public String sysAccountAddList(String id, HttpServletRequest request) {
        request.setAttribute("sysAccount", sysAccountService.findById(id));
        return "sys/sysAccount/sysAccountAddList";
    }

    /**
     * @author : cjd
     * @description : 修改密码页面
     * @params : [request]
     * @return : java.lang.String
     * @date : 11:30 2018/4/27
     */
    @GetMapping("/changePwdList")
    public String sysAccountChangeList(HttpServletRequest request) {
        return "sys/sysAccount/sysAccountChangePwdList";
    }

    /**
     * @return : java.util.List<com.cjdjyf.newssm.pojo.sys.SysAccount>
     * @author : cjd
     * @description : 账号管理列表数据
     * @date : 17:51 2018/3/11
     */
    @PostMapping("/list")
    @ResponseBody
    public PageBean<SysAccount> forList(SysAccount sysAccount,HttpServletRequest request) {
        //如果部门为空 就查自己部门的数据
        if (StringUtils.isEmpty(sysAccount.getGroupId())) {
            SysAccount user = (SysAccount)request.getSession().getAttribute("user");
            sysAccount.setGroupId(user.getGroupId());
        }
        return sysAccountService.findPageBean(sysAccount);
    }


    /**
     * @return : com.cjdjyf.newssm.base.ResultBean<java.lang.String>
     * @author : cjd
     * @description : 账号编辑
     * @params : [sysAccount]
     * @date : 17:51 2018/3/11
     */
    @PostMapping("save")
    @ResponseBody
    public ResultBean<String> save(SysAccount sysAccount) {
        return new ResultBean<>(sysAccountService.save(sysAccount));
    }

    /**
     * @return :com.cjdjyf.newssm.base.ResultBean<java.lang.Integer>
     * @Author : cjd
     * @Description : 删除账号
     * @params :[sysAccount]
     * @Date : 10:44 2018/4/19
     */
    @PostMapping("del")
    @ResponseBody
    public ResultBean<String> del(SysAccount sysAccount) {
        return new ResultBean<>(sysAccountService.del(sysAccount));
    }

    /**
     * @return : com.cjdjyf.newssm.base.ResultBean<java.lang.String>
     * @author : cjd
     * @description : 重置密码
     * @params : [sysAccount]
     * @date : 10:33 2018/4/26
     */
    @PostMapping("reset")
    @ResponseBody
    public ResultBean<String> reset(SysAccount sysAccount) {
        return new ResultBean<>(sysAccountService.reset(sysAccount));
    }

    /**
     * @author : cjd
     * @description : 修改密码
     * @params : [sysAccount]
     * @return : com.cjdjyf.newssm.base.ResultBean<java.lang.String>
     * @date : 11:30 2018/4/27
     */
    @PostMapping("changePwd")
    @ResponseBody
    public ResultBean<String> changePwd(SysAccount sysAccount) {
        return new ResultBean<>(sysAccountService.changePwd(sysAccount));
    }
}
