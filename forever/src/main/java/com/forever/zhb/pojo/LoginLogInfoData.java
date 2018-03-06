package com.forever.zhb.pojo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.forever.zhb.dic.LoginEnum;
import com.forever.zhb.pojo.base.PersistentObject;

@Entity
@Table(name="LOGIN_LOG_INFO")
public class LoginLogInfoData extends PersistentObject {
	
	private String userName;
	private String clientIp;
	private Integer loginIn;
	private Calendar createTime;
	private String loginInName;
	private String browserName;

	@Id
    @GeneratedValue(generator = "app_seq")
    @GenericGenerator(name = "app_seq", strategy = "com.forever.zhb.pojo.strategy.StringRandomGenerator")
    @Column(name = "ID")
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "USER_NAME", nullable = false)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "CLIENT_IP", nullable = false)
	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	@Column(name = "LOGIN_IN", nullable = false)
	public Integer getLoginIn() {
		return loginIn;
	}

	public void setLoginIn(Integer loginIn) {
		this.loginIn = loginIn;
	}

	@Column(name = "CREATE_TIME", nullable = false)
	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	@Transient
	public String getLoginInName() {
		for (LoginEnum loginEnum : LoginEnum.values()) {
			if (loginEnum.getIndex() == loginIn) {
				return loginEnum.getName();
			}
		}
		return "未定义";
	}

	public void setLoginInName(String loginInName) {
		this.loginInName = loginInName;
	}

	@Column(name="BROWSER_NAME")
	public String getBrowserName() {
		return browserName;
	}

	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

}
