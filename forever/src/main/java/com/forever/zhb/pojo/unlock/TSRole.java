package com.forever.zhb.pojo.unlock;

import com.forever.zhb.pojo.base.IdEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 角色表
 *  @author  张代浩
 */
@Entity
@Table(name = "t_s_role")
public class TSRole extends IdEntity implements java.io.Serializable {
	private String roleName;//角色名称
	private String roleCode;//角色编码
	@Column(name = "rolename", nullable = false, length = 100)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	@Column(name = "rolecode", length = 30)
	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
}