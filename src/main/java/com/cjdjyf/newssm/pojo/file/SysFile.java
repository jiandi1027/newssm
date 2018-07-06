/*
* SysFile.java
* Copyright(C) cjd 
* All right Reserved
* 2018-05-16 created
*/
package com.cjdjyf.newssm.pojo.file;

import com.cjdjyf.newssm.base.DataEntity;
import com.cjdjyf.newssm.utils.MyUtils;

import java.io.Serializable;
import java.util.Date;

/**
* @author cjd
* @version 1.0 2018-05-16
 */
public class SysFile extends DataEntity<SysFile> implements Serializable {
    /**对应表+主键 */
    private String tableId;

    /**文件大小 MB */
    private String fileSize;

    /**文件路径 */
    private String filePath;

    /**文件名 */
    private String fileName;

    public SysFile(String fileSize, String filePath, String fileName) {
        this.fileSize = fileSize;
        this.filePath = filePath;
        this.fileName = fileName;
        this.setCreateTime(new Date());
        this.setCreatePeople(MyUtils.getUserName());
    }

    public SysFile() {
    }

    public SysFile(String tableId) {
        this.tableId = tableId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId == null ? null : tableId.trim();
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize == null ? null : fileSize.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }
}
