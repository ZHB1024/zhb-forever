package com.forever.zhb.pojo.unlock;

import com.forever.zhb.pojo.base.IdEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *菜单权限表
 * @author  张代浩
 */
@Entity
@Table(name = "t_s_function")
public class TSFunction extends IdEntity implements java.io.Serializable {
	private com.forever.zhb.pojo.unlock.TSFunction TSFunction;//父菜单
	private String functionName;//菜单名称
	private Short functionLevel;//菜单等级
	private String functionUrl;//菜单地址
	private Short functionIframe;//菜单地址打开方式
	private String functionOrder;//菜单排序
	private List<com.forever.zhb.pojo.unlock.TSFunction> TSFunctions = new ArrayList<com.forever.zhb.pojo.unlock.TSFunction>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentfunctionid")
	public com.forever.zhb.pojo.unlock.TSFunction getTSFunction() {
		return this.TSFunction;
	}

	public void setTSFunction(com.forever.zhb.pojo.unlock.TSFunction TSFunction) {
		this.TSFunction = TSFunction;
	}

	@Column(name = "functionname", nullable = false, length = 50)
	public String getFunctionName() {
		return this.functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	@Column(name = "functionlevel")
	public Short getFunctionLevel() {
		return this.functionLevel;
	}

	public void setFunctionLevel(Short functionLevel) {
		this.functionLevel = functionLevel;
	}
	@Column(name = "functionurl", length = 100)
	public String getFunctionUrl() {
		return this.functionUrl;
	}

	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}
	@Column(name = "functionorder")
	public String getFunctionOrder() {
		return functionOrder;
	}

	public void setFunctionOrder(String functionOrder) {
		this.functionOrder = functionOrder;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSFunction")
	public List<com.forever.zhb.pojo.unlock.TSFunction> getTSFunctions() {
		return TSFunctions;
	}
	public void setTSFunctions(List<com.forever.zhb.pojo.unlock.TSFunction> TSFunctions) {
		this.TSFunctions = TSFunctions;
	}
	@Column(name = "functioniframe")
	public Short getFunctionIframe() {
		return functionIframe;
	}
	public void setFunctionIframe(Short functionIframe) {
		this.functionIframe = functionIframe;
	}

}