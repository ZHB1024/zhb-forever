package com.forever.zhb.pojo;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.forever.zhb.pojo.base.PersistentObject;

@Entity
@Table(name="ROLE_INFO")
public class RoleInfoData extends PersistentObject {
    
    private String name;
    private String description;
    //private FunctionInfoData functionInfoData;
    
    
    @Column(name = "NAME")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    /*@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "FUNCTION_ID", nullable = false)
    public FunctionInfoData getFunctionInfoData() {
        return functionInfoData;
    }
    public void setFunctionInfoData(FunctionInfoData functionInfoData) {
        this.functionInfoData = functionInfoData;
    }*/
    
    @Id
    @GeneratedValue(generator = "app_seq")
    @GenericGenerator(name = "app_seq", strategy = "com.forever.zhb.pojo.strategy.StringRandomGenerator")
    @Column(name = "ID")
    public String getId(){
        return this.id;
    }

    public  void setId(String id){
        this.id = id;
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
