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
    //表名
    private String tableName;
    //表引擎
    private String engine;
    //表描述
    private String tableComment;
    //mapper存放路径
    private String mapperURL;
    //实体类存放路径
    private String pojoURL;
    //作者
    private String author;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
