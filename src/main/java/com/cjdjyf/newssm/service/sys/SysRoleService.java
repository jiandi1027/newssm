package com.cjdjyf.newssm.service.sys;

import com.cjdjyf.newssm.base.BaseService;
import com.cjdjyf.newssm.base.PageBean;
import com.cjdjyf.newssm.mapper.sys.SysRoleMapper;
import com.cjdjyf.newssm.pojo.sys.SysPermission;
import com.cjdjyf.newssm.pojo.sys.SysRole;
import com.cjdjyf.newssm.pojo.sys.SysRolePermission;
import com.cjdjyf.newssm.utils.MyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : cjd
 * @description : 角色管理Service
 * @date : 2018/4/24 11:18
 */
@Service
public class SysRoleService extends BaseService<SysRoleMapper, SysRole> {
    @Autowired
    private SysRolePermissionService sysRolePermissionService;
    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * @return :java.util.List<com.cjdjyf.newssm.pojo.sys.SysRole>
     * @Author : cjd
     * @Description : 角色列表数据  替换权限ID为权限名称
     * @params :[sysRole]
     * @Date : 16:48 2018/4/23
     */
    @Override
    public PageBean<SysRole> findPageBean(SysRole sysRole) {
        PageBean<SysRole> pageBean = super.findPageBean(sysRole);
        /*替换权限名称*/
        replacePermissionName(pageBean.getRows());
        return pageBean;
    }

    /**
     * @return : java.lang.String
     * @author : cjd
     * @description : 新增账号
     * @params : [sysRole]
     * @date : 17:53 2018/3/11
     */
    @Override
    public String insert(SysRole sysRole) {
        String result;
        List<SysRole> sysAccountList = this.findAll(new SysRole(sysRole.getRoleName()));
        //如果不存在该角色
        if (sysAccountList.size() == 0) {
            logger.info("管理员{}新增角色{}", MyUtils.getSysAccount(), sysRole.getRoleName());
            //新增角色
            super.insert(sysRole);
            //新增角色权限对应表
            sysRolePermissionService.insert(new SysRolePermission(sysRole.getId(), sysRole.getPermissionId()));
            result = "添加成功！";
        } else {
            result = "已存在该角色！";
        }
        return result;
    }

    /**
     * @return : java.lang.String
     * @author : cjd
     * @description : 更新账号
     * @params : [sysRole]
     * @date : 17:53 2018/3/11
     */
    @Override
    public String updateByIdSelective(SysRole sysRole) {
        String result;
        //如果前后角色名不同 且 存在
        boolean same = this.findById(sysRole.getId()).getRoleName().equals(sysRole.getRoleName());
        boolean exist = this.findAll(new SysRole(sysRole.getRoleName())).size() != 0;
        if (!same && exist) {
            return "已存在该角色,更新失败";
        }
        logger.info("管理员{}更新角色{}", MyUtils.getSysAccount(), sysRole.getRoleName());
        //更新角色
        super.updateByIdSelective(sysRole);
        //更新角色权限对应表
        sysRolePermissionService.updatePermissionByRoleId(new SysRolePermission(sysRole.getId(), sysRole.getPermissionId()));
        result = "更新成功";
        return result;
    }

    /**
     * @return : void
     * @author : cjd
     * @description : 替换权限名称
     * @params : [sysRoleList]
     * @date : 11:06 2018/4/24
     */
    private void replacePermissionName(List<SysRole> sysRoleList) {
        for (SysRole role : sysRoleList) {
            if (StringUtils.isNotEmpty(role.getPermissionId())) {
                StringBuilder permissionName = new StringBuilder();
                String[] permissionId = role.getPermissionId().split(",");
                for (String permission : permissionId) {
                    SysPermission byId = sysPermissionService.findById(permission);
                    if (byId == null) {
                        continue;
                    }
                    permissionName.append(byId.getPermissionName()).append(",");
                }
                role.setPermissionName(permissionName.substring(0, permissionName.length() - 1));
            }
        }
    }

    /**
     * @return : com.cjdjyf.newssm.pojo.sys.SysRole
     * @author : cjd
     * @description : 移除已删除角色
     * @params : [id]
     * @date : 9:08 2018/5/2
     */
    public String removeDel(String roles) {
        List<String> arrayList = new ArrayList<>(Arrays.asList(roles.split(",")));
        for (int i = 0; i<arrayList.size(); i++) {
            SysRole byId = this.findById(arrayList.get(i));
            if(byId ==null){
                arrayList.remove(i);
                i=i-1;      //移除后长度减一
            }
        }
        return StringUtils.join(arrayList.toArray(), ",");

    /*    SysRole sysRole = this.findById(id);
        if (sysRole == null) {
            return null;
        }
        List<String> arrayList = new ArrayList<>(Arrays.asList(sysRole.getPermissionId().split(",")));

        for (int i = 0; i < arrayList.size(); i++) {
            SysPermission byId = sysPermissionService.findById(arrayList.get(i));
            if (byId == null) {
                arrayList.remove(i);
            }
        }
        sysRole.setPermissionId(StringUtils.join(arrayList.toArray(), ","));
        return sysRole;*/
    }



}
