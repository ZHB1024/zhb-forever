package com.forever.zhb.pojo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.forever.zhb.pojo.base.PersistentObject;

@Entity
@Table(name="FILE_INFO")
public class FileInfoData extends PersistentObject {
    
    private String filePath;
    private String fileType;
    private Long fileSize;
    private String fileName;
    private int type;
    
    @Column(name="FILE_PATH")
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Column(name="FILE_TYPE")
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Column(name="FILE_SIZE")
    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    @Column(name="FILE_NAME")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column(name="TYPE")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    

    @Id
    @GeneratedValue(generator = "app_seq")
    @GenericGenerator(name = "app_seq", strategy = "com.forever.zhb.pojo.strategy.StringRandomGenerator")
    @Column(name = "ID")
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id=id;
    }
    
    @Column(name = "CREATE_TIME")
    public Calendar getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }
    
    @Column(name = "UPDATE_TIME")
    public Calendar getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Calendar updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "DELETE_FLAG")
    public int getDeleteFlag() {
        return this.deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

}
