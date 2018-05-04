package com.cjdjyf.newssm.pojo.tool;

import com.cjdjyf.newssm.base.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : cjd
 * @description : 自动代码生成POJO
 * @date : 2018/5/3 17:25
 */
public class CodeCreate extends DataEntity<CodeCreate> implements Serializable {
    private String tableName;
    private String engine;
    private String tableComment;
    private String mapperURL;
    private String pojoURL;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMapperURL() {
        return mapperURL;
    }

    public void setMapperURL(String mapperURL) {
        this.mapperURL = mapperURL;
    }

    public String getPojoURL() {
        return pojoURL;
    }

    public void setPojoURL(String pojoURL) {
        this.pojoURL = pojoURL;
    }

}
