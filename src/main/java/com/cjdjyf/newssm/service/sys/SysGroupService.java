package com.cjdjyf.newssm.service.sys;

import com.cjdjyf.newssm.base.BaseService;
import com.cjdjyf.newssm.mapper.sys.SysGroupMapper;
import com.cjdjyf.newssm.pojo.sys.SysGroup;
import com.cjdjyf.newssm.pojo.sys.TreeNode.GroupNode;
import com.cjdjyf.newssm.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author : cjd
 * @description : 部门管理Service
 * @date : 2018/4/24 15:17
 */
@Service
public class SysGroupService extends BaseService<SysGroupMapper, SysGroup> {
    @Autowired
    private SysGroupMapper sysGroupMapper;


    /**
     * @return : java.util.List<com.cjdjyf.newssm.pojo.sys.TreeNode.MenuNode>
     * @author : cjd
     * @description : 根据ID获取子部门
     * @params : [id]
     * @date : 22:21 2018/3/11
     */
    public List<GroupNode> getGroup(String id) {
        ArrayList<GroupNode> groupNodes = new ArrayList<>();
        //递归添加当前部门
        groupNodes.add(getChildren(this.findById(id).getGroupNode()));
        //树查询要返回List
        return groupNodes;
    }

    /**
     * @return : com.cjdjyf.newssm.pojo.sys.SysMenu
     * @author : cjd
     * @description : 递归实现菜单
     * @params : [sysMenu]
     * @date : 19:49 2018/3/11
     */
    private GroupNode getChildren(GroupNode groupNode) {
        for (SysGroup sysGroup : this.findAll(new SysGroup(groupNode.getId()))) {
            groupNode.addChildren(getChildren(sysGroup.getGroupNode()));
        }
        //easyui-tree有BUG 没有children时会显示所有节点 所以这里关闭节点
        if (groupNode.getChildrenSize() == 0) {
            groupNode.setState("1");
        }
        return groupNode;
    }

    /**
     * @return :java.util.List<com.cjdjyf.newssm.pojo.sys.SysGroup>
     * @Author : cjd
     * @Description : 获取同级无下属的部门
     * @Date : 16:48 2018/5/29
     */
    public List<HashMap<String, String>> getLevelGroup() {
        String groupParentId = MyUtils.getUser().getGroupParentId();
        List<SysGroup> sysGroupList = this.findAll(new SysGroup(groupParentId));
        for (int i = 0; i < sysGroupList.size(); i++) {
            SysGroup sysGroup = sysGroupList.get(i);
            Integer count = Integer.valueOf(sysGroupMapper.getChildCount(sysGroup));
            //移除有下级的部门
            if (count > 0) {
                sysGroupList.remove(i);
                i = i - 1;      //移除后长度减一
            }
        }

        List<HashMap<String, String>> result = new ArrayList<>();
        for (SysGroup sysGroup : sysGroupList) {
            HashMap<String, String> map = new HashMap<>();
            map.put("key", sysGroup.getId());
            map.put("value", sysGroup.getGroupName());
            result.add(map);
        }
        return result;
    }
}
