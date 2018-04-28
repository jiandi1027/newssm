package com.cjdjyf.newssm.service.sys;
import com.cjdjyf.newssm.base.BaseService;
import com.cjdjyf.newssm.mapper.sys.SysAccountRoleMapper;
import com.cjdjyf.newssm.pojo.sys.SysAccountRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author : cjd
 * @description : 账号权限中间表Service
 * @date : 2018/4/24 11:18
 */
@Service
public class SysAccountRoleService extends BaseService<SysAccountRoleMapper,SysAccountRole> {

    @Autowired
    SysAccountRoleMapper sysAccountRoleMapper;

    /**
     * @Author : cjd
     * @Description : 根据账号更新角色
     * @params :[sysAccountRole]
     * @return :void
     * @Date : 10:41 2018/4/19
     */
    public void updateRoleByAccountId(SysAccountRole sysAccountRole) {
        sysAccountRoleMapper.updateRoleByAccountId(sysAccountRole);
    }
}
