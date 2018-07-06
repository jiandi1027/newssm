package com.cjdjyf.newssm.pojo.sys.flow;

import java.lang.reflect.Field;

/**
 * @author : cjd
 * @description : 流程的历史评价 用到TreeSet需要进行排序
 * @date : 2018/5/10 16:11
 */
public class FlowComment {
    //流程决定
    private String decide;
    //评价
    private String comment;
    //时间
    private String dateTime;
    //评价部门级别
    private String groupLevel;
    //活动名称
    private String activityName;
    //操作人部门
    private String groupName;
    //操作人
    private String userName;

    public FlowComment(String decide, String comment, String dateTime, String groupLevel, String activityName, String userName, String groupName) {
        this.decide = decide;
        this.comment = comment;
        this.dateTime = dateTime;
        this.groupLevel = groupLevel;
        this.activityName = activityName;
        this.userName = userName;
        this.groupName = groupName;
    }


    public String getDecide() {
        return decide;
    }

    public void setDecide(String decide) {
        this.decide = decide;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getGroupLevel() {
        return groupLevel;
    }

    public void setGroupLevel(String groupLevel) {
        this.groupLevel = groupLevel;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    //hashcode暂时没用到
    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(obj instanceof FlowComment){
            try {
                Boolean check = true;
                Field[] objectFields = obj.getClass().getDeclaredFields();
                Field[] PersonFields = obj.getClass().getDeclaredFields();
                for(int i = 0;i< objectFields.length;i++){
                    if(!objectFields[i].get(obj).equals(PersonFields[i].get(this))){
                        check = false;
                        break;
                    }
                }
                return check;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
}
