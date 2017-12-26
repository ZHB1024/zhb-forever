package com.forever.zhb.pojo;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.forever.zhb.pojo.base.PersistentObject;

@Entity
@Table(name="LOGIN_INFO")
public class LoginInfoData extends PersistentObject {
    
    private UserInfoData userInfoData;
    private String password;
    
    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "USER_ID", nullable = false)
    public UserInfoData getUserInfoData() {
        return userInfoData;
    }

    public void setUserInfoData(UserInfoData userInfoData) {
        this.userInfoData = userInfoData;
    }

    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
