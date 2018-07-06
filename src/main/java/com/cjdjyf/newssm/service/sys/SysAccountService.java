package com.cjdjyf.newssm.service.sys;

import com.cjdjyf.newssm.base.BaseService;
import com.cjdjyf.newssm.constant.SysConstant;
import com.cjdjyf.newssm.mapper.sys.SysAccountMapper;
import com.cjdjyf.newssm.pojo.sys.SysAccount;
import com.cjdjyf.newssm.pojo.sys.SysAccountRole;
import com.cjdjyf.newssm.pojo.sys.SysRole;
import com.cjdjyf.newssm.utils.MyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
     * @description : 设置角色名
     * @params : [sysAccount]
     * @date : 16:00 2018/3/11
     */
    @Override
    public List<SysAccount> findAll(SysAccount record) {
        List<SysAccount> list = super.findAll(record);
        setRoleName(list);
        return list;
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
        //更新账号和关联表
        super.updateByIdSelective(sysAccount);
        if (StringUtils.isNotEmpty(sysAccount.getRoleId())) {
            sysAccountRoleService.updateRoleByAccountId(new SysAccountRole(sysAccount.getId(), sysAccount.getRoleId()));
        }
        return "更新成功";
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
            logger.info("管理员{}新增账号{}", MyUtils.getUserName(), sysAccount.getUserName());
            sysAccount.setPassword(MyUtils.GetMD5(sysAccount.getUserName(), SysConstant.DEFAULT_PASSWORD));
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
     * @author : cjd
     * @description : 替换角色ID为角色名称
     * @params : [sysAccountList]
     * @date : 11:01 2018/4/24
     */
    private void setRoleName(List<SysAccount> sysAccountList) {
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
     * @return : java.lang.String
     * @author : cjd
     * @description : 重置密码
     * @params : [sysAccount]
     * @date : 10:09 2018/4/26
     */
    public String reset(SysAccount sysAccount) {
        sysAccount.setPassword(MyUtils.GetMD5(sysAccount.getUserName(), SysConstant.DEFAULT_PASSWORD));
        return super.updateByIdSelective(sysAccount);
    }

    /**
     * @return : java.lang.Void
     * @author : cjd
     * @description : 修改密码
     * @params : [sysAccount]
     * @date : 10:57 2018/4/26
     */
    public String changePwd(SysAccount sysAccount) {
        SysAccount user = this.findByName(MyUtils.getUserName());
        //如果相同 替换密码
        if (MyUtils.GetMD5(MyUtils.getUserName(), sysAccount.getPassword()).equals(user.getPassword())) {
            user.setPassword(MyUtils.GetMD5(MyUtils.getUserName(), sysAccount.getReplacePassword()));
        } else {
            return "密码不正确";
        }
        return super.updateByIdSelective(user);
    }

    /**
     * @return :java.util.List<java.util.HashMap<java.lang.String,java.lang.String>>
     * @Author : cjd
     * @Description : 获取同部门的同事
     * @Date : 17:15 2018/5/29
     */
    public List<HashMap<String, String>> getLevelAccount() {
        SysAccount sysAccount = new SysAccount();
        sysAccount.setGroupId(MyUtils.getGroupId());
        sysAccount.setStopFlag("0");
        //有值则不取自己
        sysAccount.setId(MyUtils.getUser().getId());
        List<SysAccount> list = this.findAll(sysAccount);

        List<HashMap<String, String>> result = new ArrayList<>();
        for (SysAccount account : list) {
            HashMap<String, String> map = new HashMap<>();
            map.put("key", account.getId());
            map.put("value", account.getUserName());
            result.add(map);
        }
        return result;
    }
}
