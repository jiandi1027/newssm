package com.cjdjyf.newssm.controller.sys;

import com.cjdjyf.newssm.base.ResultBean;
import com.cjdjyf.newssm.pojo.sys.SysAccount;
import com.cjdjyf.newssm.pojo.sys.SysGroup;
import com.cjdjyf.newssm.service.sys.SysGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author : cjd
 * @description : 部门管理控制器
 * @date : 2018/4/24 11:18
 */
@Controller
@RequestMapping("/sys/sysGroup")
public class SysGroupController {
    @Autowired
    private SysGroupService sysGroupService;

    /**
     * @return : java.lang.String
     * @author : cjd
     * @description : 部门管理页面
     * @params : []
     * @date : 16:39 2018/4/25
     */
    @GetMapping("/list")
    public String list() {
        return "sys/sysGroup/sysGroupList";
    }

    /**
     * @return : java.lang.String
     * @author : cjd
     * @description : 部门新增页面
     * @params : [id, request]
     * @date : 16:39 2018/4/25
     */
    @GetMapping("/addList")
    public String sysAccountAddList(String id, HttpServletRequest request) {
        request.setAttribute("sysGroup", sysGroupService.findById(id));
        return "sys/sysGroup/sysGroupAddList";
    }

    /**
     * @return : java.util.HashMap<java.lang.String,java.lang.Object>
     * @author : cjd
     * @description : 部门管理树形列表数据
     * @params : []
     * @date : 16:39 2018/4/25
     */
    @PostMapping("/list")
    @ResponseBody
    public HashMap<String, Object> forList(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        SysAccount user = (SysAccount) request.getSession().getAttribute("user");
        //treegrid只接收rows数据    获取登录账号下的部门树
        map.put("rows", sysGroupService.getGroup(user.getGroupId()));
        return map;
    }

    /**
     * @return : com.cjdjyf.newssm.base.ResultBean<java.lang.String>
     * @author : cjd
     * @description : 部门编辑
     * @params : [sysGroup]
     * @date : 16:40 2018/4/25
     */
    @PostMapping("save")
    @ResponseBody
    public ResultBean<String> save(SysGroup sysGroup) {
        return new ResultBean<>(sysGroupService.save(sysGroup));
    }

    /**
     * @return : com.cjdjyf.newssm.base.ResultBean<java.lang.String>
     * @author : cjd
     * @description : 删除部门
     * @params : [sysGroup]
     * @date : 16:40 2018/4/25
     */
    @PostMapping("del")
    @ResponseBody
    public ResultBean<String> del(SysGroup sysGroup) {
        return new ResultBean<>(sysGroupService.del(sysGroup));
    }

}
