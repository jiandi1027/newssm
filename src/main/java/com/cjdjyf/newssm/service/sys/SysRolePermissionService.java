package com.cjdjyf.newssm.service.sys;

import com.cjdjyf.newssm.base.BaseService;
import com.cjdjyf.newssm.mapper.sys.SysRolePermissionMapper;
import com.cjdjyf.newssm.pojo.sys.SysRolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : cjd
 * @description : 角色权限中间表Service
 * @date : 2018/4/24 11:18
 */
@Service
public class SysRolePermissionService extends BaseService<SysRolePermissionMapper,SysRolePermission> {
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    /**
     * @author : cjd
     * @description : 根据角色ID更新权限
     * @params : [sysRolePermission]
     * @return : void
     * @date : 16:13 2018/4/24
     */
    public void updatePermissionByRoleId(SysRolePermission sysRolePermission) {
        sysRolePermissionMapper.updatePermissionByRoleId(sysRolePermission);
    }
}
