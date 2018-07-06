
/**
 * @Description: 流程实体类
 * @author cjd
 * @version V1.0
 */

package com.cjdjyf.newssm.base;

import com.cjdjyf.newssm.pojo.sys.flow.FlowComment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.TreeSet;

public abstract class ProcessEntity<T> extends DataEntity<T> {
    private static final long serialVersionUID = -4449763424408165319L;
    //对应流程实例ID
    private String processInstanceId;
    //有无审批权限
    private String hasPermission;
    //处理人
    private String assignee;
    //审批意见
    private String comment;
    //审批选择
    private String decide;
    //所有历史批注 保留顺序 所以用了tree
    private TreeSet<FlowComment> commentSet;
    //当前任务名称
    private String taskName;
    //当前节点名称
    private String activityName;
    //完成任务需要的参数
    private HashMap<String, Object> properties;

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getHasPermission() {
        return hasPermission;
    }

    public void setHasPermission(String hasPermission) {
        this.hasPermission = hasPermission;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDecide() {
        return decide;
    }

    public void setDecide(String decide) {
        this.decide = decide;
    }

    /**
     * @return : java.util.TreeSet<com.cjdjyf.newssm.pojo.sys.flow.FlowComment>
     * @author : cjd
     * @description : 获取评论的Set
     * @date : 11:14 2018/5/11
     */
    public TreeSet<FlowComment> getCommentSet() {
        if (commentSet == null) {
            //lambda表达式替代匿名内部类
            commentSet = new TreeSet<>((o1, o2) -> compareTime(o2.getDateTime(), o1.getDateTime()));
        }

        return commentSet;
    }

    public void setCommentSet(TreeSet<FlowComment> commentSet) {
        this.commentSet = commentSet;
    }

    /**
     * @return : int
     * @author : cjd
     * @description : 比较两个String类型的时间戳
     * @params : [time1, time2]
     * @date : 11:07 2018/5/11
     */
    public int compareTime(String time1, String time2) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            long l = format.parse(time1).getTime() - format.parse(time2).getTime();
            return (int) l;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public HashMap getProperties() {
        return properties;
    }

    public void setProperties(HashMap<String, Object> properties) {
        this.properties = properties;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
}
