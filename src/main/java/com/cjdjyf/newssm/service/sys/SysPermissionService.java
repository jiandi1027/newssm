package com.cjdjyf.newssm.service.sys;

import com.cjdjyf.newssm.base.BaseService;
import com.cjdjyf.newssm.constant.SysConstant;
import com.cjdjyf.newssm.mapper.sys.SysPermissionMapper;
import com.cjdjyf.newssm.pojo.sys.SysPermission;
import com.cjdjyf.newssm.pojo.sys.TreeNode.MenuNode;
import org.apache.commons.lang3.StringUtils;
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
    private Set<String> treeSet; //防止权限多次添加

    /**
     * @return : java.util.List<com.cjdjyf.newssm.pojo.sys.TreeNode.MenuNode>
     * @author : cjd
     * @description : 获取菜单
     * @params : [id]
     * @date : 22:21 2018/3/11
     */
    public List<MenuNode> getMenu(SysPermission sysPermission1) {
        //权限ID
        String permissionId = (String) SecurityUtils.getSubject().getSession().getAttribute("permissionId");
        Set<String> set = new HashSet<String>(Arrays.asList(permissionId.split(",")));

        treeSet = new HashSet<>();
        ArrayList<MenuNode> menuNodes = new ArrayList<>();
        for (SysPermission sysPermission : this.findAll(sysPermission1)) {
            //有菜单权限
            if (treeSet.add(sysPermission.getId()) && set.contains(sysPermission.getId())) {
                menuNodes.add(getMenuChildren(sysPermission.getMenuNode(), sysPermission1.getLoginGroupId()));
            }
        }
        //树查询要返回List
        return menuNodes;
    }

    /**
     * @return : com.cjdjyf.newssm.pojo.sys.SysMenu
     * @author : cjd
     * @description : 获取菜单子节点
     * @params : [sysMenu]
     * @date : 19:49 2018/3/11
     */
    private MenuNode getMenuChildren(MenuNode menuNode, String loginGroupId) {
        String permissionId = (String) SecurityUtils.getSubject().getSession().getAttribute("permissionId");
        Set<String> set = new HashSet<String>(Arrays.asList(permissionId.split(",")));
        SysPermission sysPermission1 = new SysPermission(menuNode.getId());
        sysPermission1.setMenuFlag("1");
        //找到团队下创建的菜单
        sysPermission1.setLoginGroupId(loginGroupId);
        for (SysPermission sysPermission : this.findAll(sysPermission1)) {
            //有菜单权限
            if (set.contains(sysPermission.getId())) {
                //递归调用
                menuNode.addChildren(getMenuChildren(sysPermission.getMenuNode(), loginGroupId));
            }
        }
        //easyui-tree有BUG 没有children时会显示所有节点 所以这里关闭节点
        if (menuNode.getChildrenSize() == 0) {
            menuNode.setState("1");
        }
        return menuNode;
    }

    /**
     * @return : java.util.List<com.cjdjyf.newssm.pojo.sys.TreeNode.MenuNode>
     * @author : cjd
     * @description : 获取权限列表
     * @params : [sysPermission1]
     * @date : 12:07 2018/4/28
     */
    public List<MenuNode> getPermissionList(String loginGroupId) {
        treeSet = new HashSet<>();
        ArrayList<MenuNode> menuNodes = new ArrayList<>();
        //查根目录下的权限
        if (treeSet.add(SysConstant.SOURCE_MENU_ID)) {
            menuNodes.add(getPermissionChildren(this.findById(SysConstant.SOURCE_MENU_ID).getMenuNode(), loginGroupId));
        }
        //所有能改权限的都赋予 系统设置权限
      /*  SysPermission sysPermission = new SysPermission();
        sysPermission.setPermissionName("系统设置");
        menuNodes.get(0).getChildren().add(getPermissionChildren(this.findAll(sysPermission).get(0).getMenuNode(),null));
      */
        //树查询要返回List
        return menuNodes;
    }

    /**
     * @return : com.cjdjyf.newssm.pojo.sys.TreeNode.MenuNode
     * @author : cjd
     * @description : 获取权限列表子节点
     * @params : [menuNode, loginGroupId]
     * @date : 12:26 2018/4/28
     */
    private MenuNode getPermissionChildren(MenuNode menuNode, String loginGroupId) {
        SysPermission sysPermission1 = new SysPermission(menuNode.getId());
        //添加后只能查出部门及子部门创建的权限
        sysPermission1.setLoginGroupId(loginGroupId);
        for (SysPermission sysPermission : this.findAll(sysPermission1)) {
            if (treeSet.add(sysPermission.getId())) {
                //递归调用
                menuNode.addChildren(getPermissionChildren(sysPermission.getMenuNode(), loginGroupId));
            }
        }
        //easyui-tree有BUG 没有children时会显示所有节点 所以这里关闭节点
        if (menuNode.getChildrenSize() == 0) {
            menuNode.setState("1");
        }
        return menuNode;
    }

    /**
     * @return : java.lang.String
     * @author : cjd
     * @description : 移除已删除权限
     * @params : [permissions]
     * @date : 9:34 2018/5/2
     */
    public String removeDel(String permissions) {
        List<String> arrayList = new ArrayList<>(Arrays.asList(permissions.split(",")));
        for (int i = 0; i < arrayList.size(); i++) {
            SysPermission byId = this.findById(arrayList.get(i));
            if (byId == null) {
                arrayList.remove(i);
                i = i - 1;       //移除后长度减一
            }
        }
        return StringUtils.join(arrayList.toArray(), ",");
    }
}
