package com.cjdjyf.newssm.shiro;

import com.cjdjyf.newssm.pojo.sys.SysAccount;
import com.cjdjyf.newssm.pojo.sys.SysAccountRole;
import com.cjdjyf.newssm.pojo.sys.SysRolePermission;
import com.cjdjyf.newssm.service.sys.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MyRealm extends AuthorizingRealm {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    SysAccountService sysAccountService;
    @Autowired
    SysAccountRoleService sysAccountRoleService;
    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    SysRolePermissionService sysRolePermissionService;
    @Autowired
    SysPermissionService sysPermissionService;
    //权限ID

    /**
     * @return : org.apache.shiro.authz.AuthorizationInfo
     * @author : cjd
     * @description : 权限验证
     * @params : [principals]
     * @date : 13:32 2018/3/10
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        try {
            //获取账号ID
            String accountId = sysAccountService.findByName(principals.getPrimaryPrincipal().toString()).getId();
            //角色ID
            List<SysAccountRole> sysAccountRoleList = sysAccountRoleService.findAll(new SysAccountRole(accountId));
            if (sysAccountRoleList.size() > 0) {
                String roleId = sysAccountRoleList.get(0).getRoleId();
                StringBuilder stringBuilder = new StringBuilder();
                if (StringUtils.isNotEmpty(roleId)) {
                    String roles[] = roleId.split(",");
                    for (String role : roles) {
                        try {
                            //获取角色名 添加进缓存
                            info.addRole(sysRoleService.findById(role).getRoleName());
                            //按角色添加权限
                            addPermissionByRole(info, role, stringBuilder);
                        } catch (Exception e) {
                            logger.error("角色{}下无权限", role);
                        }
                    }
                    SecurityUtils.getSubject().getSession().setAttribute("permissionId", stringBuilder.substring(0, stringBuilder.length() - 1));
                }
            }
        } catch (Exception e) {
            logger.error("账号{}下无角色", principals.getPrimaryPrincipal().toString());
        }
        return info;
    }

    /**
     * @return :void
     * @Author : cjd
     * @Description : 按角色添加权限
     * @params :[info, role]
     * @Date : 9:55 2018/4/19
     */
    private void addPermissionByRole(SimpleAuthorizationInfo info, String role, StringBuilder stringBuilder) {
        //取出角色对应权限ID
        List<SysRolePermission> permissionList = sysRolePermissionService.findAll(new SysRolePermission(role));
        if (permissionList.size() > 0) {
            String permissionId = permissionList.get(0).getPermissionId();
            String permissions[] = permissionId.split(",");
            for (String permission1 : permissions) {
                try {
                    String permissionName = sysPermissionService.findById(permission1).getPermissionName();
                    info.addStringPermission(permissionName);
                } catch (Exception e) {
                    logger.error("无找到权限{}",permission1);
                }
            }
            stringBuilder.append(permissionList.get(0).getPermissionId()).append(",");
        }
    }

    /**
     * @return : org.apache.shiro.authc.AuthenticationInfo
     * @author : cjd
     * @description : 登录验证
     * @params : [authcToken]
     * @date : 13:32 2018/3/10
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        try {
            SysAccount sysAccount = sysAccountService.findByName(token.getUsername());
            if (token.getUsername().equals(sysAccount.getUserName())) {
                //加密
                ByteSource salt = ByteSource.Util.bytes(sysAccount.getUserName());
                return new SimpleAuthenticationInfo(sysAccount.getUserName(), sysAccount.getPassword(), salt, getName());
            }
        } catch (Exception ignored) {
        }
        throw new AuthenticationException();
    }
}
