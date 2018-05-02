package com.cjdjyf.newssm.service.sys;

import com.cjdjyf.newssm.base.BaseService;
import com.cjdjyf.newssm.base.PageBean;
import com.cjdjyf.newssm.constant.SysConstant;
import com.cjdjyf.newssm.mapper.sys.SysAccountMapper;
import com.cjdjyf.newssm.pojo.sys.SysAccount;
import com.cjdjyf.newssm.pojo.sys.SysAccountRole;
import com.cjdjyf.newssm.pojo.sys.SysRole;
import com.cjdjyf.newssm.utils.MyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : cjd
 * @description : 账号管理Service
 * @date : 2018/4/24 11:18
 */
@Service
public class SysAccountService extends BaseService<SysAccountMapper, SysAccount> {
    @Autowired
    private SysAccountMapper sysAccountMapper;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysAccountRoleService sysAccountRoleService;

    /**
     * @return : com.cjdjyf.newssm.pojo.sys.SysAccount
     * @author : cjd
     * @description : 根据账号名查找
     * @params : [username]
     * @date : 17:54 2018/3/11
     */
    public SysAccount findByName(String username) {
        return sysAccountMapper.findByName(username);
    }

    /**
     * @return : java.util.List<com.cjdjyf.newssm.pojo.sys.SysAccount>
     * @author : cjd
     * @description : 获取账号列表
     * @params : [sysAccount]
     * @date : 16:00 2018/3/11
     */
    @Override
    public PageBean<SysAccount> findPageBean(SysAccount sysAccount) {
        PageBean<SysAccount> pageBean = super.findPageBean(sysAccount);
        /*替换角色ID为角色名称*/
        replaceRoleName(pageBean.getRows());
        return pageBean;
    }

    /**
     * @return : java.lang.String
     * @author : cjd
     * @description : 重写修改 修改账号的同时修改关联表
     * @params : [sysAccount]
     * @date : 17:53 2018/3/11
     */
    @Override
    public String updateByIdSelective(SysAccount sysAccount) {
        String result;
        //如果前后用户名不同 且 已存在
        boolean same = this.findById(sysAccount.getId()).getUserName().equals(sysAccount.getUserName());
        boolean exist = this.findAll(new SysAccount(sysAccount.getUserName())).size() != 0;
        if (!same && exist) {
            return "已存在该账号";
        }
        logger.info("管理员{}更新账号{}", MyUtils.getSysAccount(), sysAccount.getUserName());
        //更新账号和关联表
        super.updateByIdSelective(sysAccount);
        sysAccountRoleService.updateRoleByAccountId(new SysAccountRole(sysAccount.getId(), sysAccount.getRoleId()));
        result = "更新成功";
        return result;
    }

    /**
     * @return : java.lang.String
     * @author : cjd
     * @description : 重写新增 新增账号同时新增账号角色关联表
     * @params : [sysAccount]
     * @date : 17:53 2018/3/11
     */
    @Override
    public String insert(SysAccount sysAccount) {
        String result;
        List<SysAccount> sysAccountList = this.findAll(new SysAccount(sysAccount.getUserName()));
        if (sysAccountList.size() == 0) {
            logger.info("管理员{}新增账号{}", MyUtils.getSysAccount(), sysAccount.getUserName());
            sysAccount.setPassword(MyUtils.GetMD5(sysAccount.getUserName(),SysConstant.DEFAULT_PASSWORD));
            //新增账号和关联表
            super.insert(sysAccount);
            sysAccountRoleService.insert(new SysAccountRole(sysAccount.getId(), sysAccount.getRoleId()));
            result = "添加成功！";
        } else {
            result = "已存在该账号！";
        }
        return result;
    }

    /**
     * @return : void
     * @author : cjd
     * @description : 替换角色ID为角色名称
     * @params : [sysAccountList]
     * @date : 11:01 2018/4/24
     */
    private void replaceRoleName(List<SysAccount> sysAccountList) {
        for (SysAccount account : sysAccountList) {
            if (StringUtils.isNotEmpty(account.getRoleId())) {
                StringBuilder roleName = new StringBuilder();
                String[] roleId = account.getRoleId().split(",");
                for (String role : roleId) {
                    SysRole sysRole = sysRoleService.findById(role);
                    if (sysRole != null) {
                        roleName.append(sysRole.getRoleName()).append(",");
                    }
                }
                if (roleName.length() > 0) {
                    account.setRoleName(roleName.substring(0, roleName.length() - 1));
                }
            }
        }
    }

    /**
     * @author : cjd
     * @description : 重置密码
     * @params : [sysAccount]
     * @return : java.lang.String
     * @date : 10:09 2018/4/26
     */
    public String reset(SysAccount sysAccount) {
        sysAccount.setPassword(MyUtils.GetMD5(MyUtils.getSysAccount(),SysConstant.DEFAULT_PASSWORD));
        return super.updateByIdSelective(sysAccount);
    }

    /**
     * @author : cjd
     * @description : 修改密码
     * @params : [sysAccount]
     * @return : java.lang.Void
     * @date : 10:57 2018/4/26
     */
    public String changePwd(SysAccount sysAccount) {
        SysAccount user = this.findByName(MyUtils.getSysAccount());
        //如果相同 替换密码
        if (MyUtils.GetMD5(MyUtils.getSysAccount(),sysAccount.getPassword()).equals(user.getPassword())) {
            user.setPassword(MyUtils.GetMD5(MyUtils.getSysAccount(),sysAccount.getReplacePassword()));
        } else {
            return "密码不正确";
        }
        return super.updateByIdSelective(user);
    }

}
