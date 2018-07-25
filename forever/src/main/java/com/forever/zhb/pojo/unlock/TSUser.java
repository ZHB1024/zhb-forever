package com.forever.zhb.pojo.unlock;

import com.forever.zhb.pojo.base.IdEntity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonIgnore;


/**
 * 系统用户父类表
 * 
 * @author 张代浩
 */
@Entity
@Table(name = "t_s_base_user")
@Inheritance(strategy = InheritanceType.JOINED)
public class TSUser extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String adminName;// 用户名
	private String realName;// 真实姓名
	private String password;// 用户密码
	private Short status;// 状态1：在线,2：离线,0：禁用
	private byte[] signature;// 签名文件
	private TSDepart TSDepart = new TSDepart();// 部门
	private Date createTime;
	private String parent;//建立的父节点
	private Integer canRecover;
	private Integer canUpdateDbcStatus;
	private Integer canApproveBack;
	//private Integer canDelete;

	@Column(name = "signature", length = 3000)
	public byte[] getSignature() {
		return signature;
	}

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}

	@Column(name = "status")
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "password", length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	// getList查询转换为列表时处理json转换异常
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "departid")
	public TSDepart getTSDepart() {
		return this.TSDepart;
	}

	public void setTSDepart(TSDepart TSDepart) {
		this.TSDepart = TSDepart;
	}
	@Column(name = "canrecover")
	public Integer getCanRecover() {
		return canRecover;
	}

	public void setCanRecover(Integer canRecover) {
		this.canRecover = canRecover;
	}
	@Column(name = "canupdatedbcstatus")
	public Integer getCanUpdateDbcStatus() {
		return canUpdateDbcStatus;
	}

	public void setCanUpdateDbcStatus(Integer canUpdateDbcStatus) {
		this.canUpdateDbcStatus = canUpdateDbcStatus;
	}

	@Column(name = "createtime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "adminname", nullable = false, length = 100)
	public String getAdminName() {
		return this.adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	@Column(name = "realname", length = 50)
	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	@Column(name = "parent", length = 32)
	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	@Column(name = "canapproveback")
	public Integer getCanApproveBack() {
		return canApproveBack;
	}

	public void setCanApproveBack(Integer canApproveBack) {
		this.canApproveBack = canApproveBack;
	}

	/**
	@Column(name = "candelete")
	public Integer getCanDelete() {
		return canDelete;
	}

	public void setCanDelete(Integer canDelete) {
		this.canDelete = canDelete;
	}
	*/
}