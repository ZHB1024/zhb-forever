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
@Table(name="USER_INFO")
public class UserInfoData extends PersistentObject {
    
    private String name;
    private RoleInfoData roleInfoData;
    private String realName;
    private String sex;
    private String phone;
    private String email;

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ROLE_ID", nullable = false)
    public RoleInfoData getRoleInfoData() {
        return roleInfoData;
    }

    public void setRoleInfoData(RoleInfoData roleInfoData) {
        this.roleInfoData = roleInfoData;
    }

    @Column(name = "REAL_NAME")
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Column(name = "SEX")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Column(name = "PHONE")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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
