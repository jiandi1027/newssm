package com.cjdjyf.newssm.controller.sys;

import com.cjdjyf.newssm.base.ResultBean;
import com.cjdjyf.newssm.pojo.sys.SysPermission;
import com.cjdjyf.newssm.pojo.sys.TreeNode.MenuNode;
import com.cjdjyf.newssm.service.sys.SysPermissionService;
import com.cjdjyf.newssm.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @author : cjd
 * @description : 权限管理控制器
 * @date : 2018/4/24 11:18
 */
@Controller
@RequestMapping("/sys/sysPermission")
public class SysPermissionController {
    @Autowired
    private SysPermissionService SysPermissionService;

    /**
     * @return :java.lang.String
     * @Author : cjd
     * @Description : 权限列表页面
     * @params :[]
     * @Date : 11:14 2018/4/20
     */
    @GetMapping("list")
    public String list() {
        return "sys/sysPermission/sysPermissionList";
    }

    /**
     * @return :java.lang.String
     * @Author : cjd
     * @Description : 权限新增页面
     * @params :[id, request]
     * @Date : 11:13 2018/4/20
     */
    @GetMapping("/addList")
    public String sysPermissionAddList(String id, HttpServletRequest request, String parentId) {
        request.setAttribute("sysPermission", SysPermissionService.findById(id));
        request.setAttribute("parentId", parentId);
        return "sys/sysPermission/sysPermissionAddList";
    }

    /**
     * @return :com.cjdjyf.newssm.base.ResultBean<java.lang.String>
     * @Author : cjd
     * @Description : 权限编辑
     * @params :[sysPermission]
     * @Date : 11:13 2018/4/20
     */
    @PostMapping("save")
    @ResponseBody
    public ResultBean<String> save(SysPermission sysPermission) {
        return new ResultBean<>(SysPermissionService.save(sysPermission));
    }

    /**
     * @return :HashMap<String,Object>
     * @Author : cjd
     * @Description : 权限列表数据
     * @params :[sysPermission]
     * @Date : 11:13 2018/4/20
     */
    @PostMapping("/list")
    @ResponseBody
    public HashMap<String, Object> forList( HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        //treegrid只接收rows数据
        map.put("rows", SysPermissionService.getPermissionList(MyUtils.getGroupId()));
        return map;
    }

    /**
     * @return :com.cjdjyf.newssm.base.ResultBean<java.lang.Integer>
     * @Author : cjd
     * @Description : 权限删除
     * @params :[sysPermission]
     * @Date : 11:12 2018/4/20
     */
    @PostMapping("del")
    @ResponseBody
    public ResultBean<String> del(SysPermission sysPermission) {
        return new ResultBean<>(SysPermissionService.del(sysPermission));
    }

    /**
     * @return :com.cjdjyf.newssm.base.ResultBean<java.util.List<com.cjdjyf.newssm.pojo.sys.TreeNode.MenuNode>>
     * @Author : cjd
     * @Description : 获取主页面菜单
     * @params :[id]
     * @Date : 10:57 2018/4/19
     */
    @PostMapping("/getMenu")
    @ResponseBody
    public ResultBean<List<MenuNode>> getMenu(SysPermission sysPermission) {
        return new ResultBean<>(SysPermissionService.getMenu(sysPermission));
    }
}
