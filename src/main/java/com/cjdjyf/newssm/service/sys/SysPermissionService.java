package com.cjdjyf.newssm.service.sys;

import com.cjdjyf.newssm.base.BaseService;
import com.cjdjyf.newssm.constant.SysConstant;
import com.cjdjyf.newssm.mapper.sys.SysPermissionMapper;
import com.cjdjyf.newssm.pojo.sys.SysPermission;
import com.cjdjyf.newssm.pojo.sys.TreeNode.MenuNode;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author : cjd
 * @description : 权限管理Service
 * @date : 2018/4/24 11:18
 */
@Service
public class SysPermissionService extends BaseService<SysPermissionMapper, SysPermission> {
    private Set<SysPermission> treeSet;

    /**
     * @return : java.util.List<com.cjdjyf.newssm.pojo.sys.TreeNode.MenuNode>
     * @author : cjd
     * @description : 根据ID获取子菜单
     * @params : [id]
     * @date : 22:21 2018/3/11
     */
    public List<MenuNode> getMenu(SysPermission sysPermission1, Boolean is_mean) {
        //权限ID
        String permissionId = (String) SecurityUtils.getSubject().getSession().getAttribute("permissionId");
        Set<String> set = new HashSet<String>(Arrays.asList(permissionId.split(",")));
        treeSet = new HashSet<>();

        ArrayList<MenuNode> menuNodes = new ArrayList<>();
        if (is_mean) {
            //如果是首页菜单 条件加上flag = 1
            sysPermission1.setMenuFlag("1");
        } else {
            //找根目录菜单
            sysPermission1.setParentId(SysConstant.SOURCE_MENU_PARENT);
        }
        for (SysPermission sysPermission : this.findAll(sysPermission1)) {
            //非首页进入     或     首页进入要有菜单权限
            if (treeSet.add(sysPermission) && !is_mean || set.contains(sysPermission.getId())) {
                menuNodes.add(getChildren(sysPermission.getMenuNode(), is_mean, sysPermission1.getLoginGroupId()));
            }
        }
        //树查询要返回List
        return menuNodes;
    }

    /**
     * @return : com.cjdjyf.newssm.pojo.sys.SysMenu
     * @author : cjd
     * @description : 递归实现菜单
     * @params : [sysMenu]
     * @date : 19:49 2018/3/11
     */
    private MenuNode getChildren(MenuNode menuNode, Boolean is_mean, String loginGroupId) {
        String permissionId = (String) SecurityUtils.getSubject().getSession().getAttribute("permissionId");
        Set<String> set = new HashSet<String>(Arrays.asList(permissionId.split(",")));
        SysPermission sysPermission1 = new SysPermission(menuNode.getId());

        //如果是首页菜单 条件加上flag = 1
        if (is_mean) {
            sysPermission1.setMenuFlag("1");
        }
        sysPermission1.setLoginGroupId(loginGroupId);
        for (SysPermission sysPermission : this.findAll(sysPermission1)) {
            //非首页进入     或     首页进入要有菜单权限
            if (treeSet.add(sysPermission) && !is_mean || set.contains(sysPermission.getId())) {
                //递归调用
                menuNode.addChildren(getChildren(sysPermission.getMenuNode(), is_mean,loginGroupId));
            }
        }
        //easyui-tree有BUG 没有children时会显示所有节点 所以这里关闭节点
        if (menuNode.getChildrenSize() == 0) {
            menuNode.setState("1");
        }
        return menuNode;
    }

}
