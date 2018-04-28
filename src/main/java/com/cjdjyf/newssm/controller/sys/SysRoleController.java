package com.cjdjyf.newssm.controller.sys;

import com.cjdjyf.newssm.base.PageBean;
import com.cjdjyf.newssm.base.ResultBean;
import com.cjdjyf.newssm.pojo.sys.SysAccount;
import com.cjdjyf.newssm.pojo.sys.SysRole;
import com.cjdjyf.newssm.service.sys.SysRoleService;
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
 * @description : 角色管理控制器
 * @date : 2018/4/24 11:18
 */
@Controller
@RequestMapping("/sys/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * @Author : cjd
     * @Description : 角色管理页面
     * @params :[]
     * @return :java.lang.String
     * @Date : 10:57 2018/4/19
     */
    @GetMapping("/list")
    public String list() {
        return "sys/sysRole/sysRoleList";
    }

    /**
     * @Author : cjd
     * @Description : 角色编辑页面
     * @params :[id, request]
     * @return :java.lang.String
     * @Date : 10:58 2018/4/19
     */
    @GetMapping("/addList")
    public String sysRoleAddList(String id, HttpServletRequest request) {
        request.setAttribute("sysRole", sysRoleService.findById(id));
        return "sys/sysRole/sysRoleAddList";
    }

    /**
     * @Author : cjd
     * @Description : 角色管理列表数据
     * @params :[]
     * @return :java.util.List<com.cjdjyf.newssm.pojo.sys.SysRole>
     * @Date : 10:58 2018/4/19
     */
    @PostMapping("/list")
    @ResponseBody
    public PageBean<SysRole> forList(SysRole sysRole,HttpServletRequest request) {
        //如果部门为空 就查自己部门的数据
        if (StringUtils.isEmpty(sysRole.getLoginGroupId())) {
            SysAccount user = (SysAccount)request.getSession().getAttribute("user");
            sysRole.setLoginGroupId(user.getGroupId());
        }
        return sysRoleService.findPageBean(sysRole);
    }

    /**
     * @Author : cjd
     * @Description : 角色编辑
     * @params :[sysRole]
     * @return :com.cjdjyf.newssm.base.ResultBean<java.lang.Integer>
     * @Date : 10:58 2018/4/19
     */
    @PostMapping("save")
    @ResponseBody
    public ResultBean<String> save(SysRole sysRole) {
        return new ResultBean<>(sysRoleService.save(sysRole));
    }

    /**
     * @Author : cjd
     * @Description : 角色删除
     * @params :[sysRole]
     * @return :com.cjdjyf.newssm.base.ResultBean<java.lang.String>
     * @Date : 16:46 2018/4/23
     */
    @PostMapping("del")
    @ResponseBody
    public ResultBean<String> del(SysRole sysRole) {
        return new ResultBean<>(sysRoleService.del(sysRole));
    }
}
