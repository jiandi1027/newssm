package com.cjdjyf.newssm.pojo.tool.toolFlow;

/**
 * @author : cjd
 * @description : 流程定义
 * @date : 2018/5/22 14:44
 */
public class ToolFlow {
    //id
    private String id;
    //key
    private String key;
    //部署ID
    private String deploymentId;
    //资源名
    private String name;
    //版本
    private String version;

    public ToolFlow(String id, String key, String deploymentId, String name, String version) {
        this.id = id;
        this.key = key;
        this.deploymentId = deploymentId;
        this.name = name;
        this.version = version;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
