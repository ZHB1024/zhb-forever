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
 * 部门机构表
 * @author  张代浩
 */
@Entity
@Table(name = "t_s_depart")
public class TSDepart extends IdEntity implements java.io.Serializable {
	private TSDepart TSPDepart;//上级部门
	private String departname;//部门名称
	private String areacode;//地区代码
	private Integer departtype;//机构类型
	private Integer isInclude;//是否包含


	//	private String description;//部门描述
	private List<TSDepart> TSDeparts = new ArrayList<TSDepart>();//下属部门

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentdepartid")
	public TSDepart getTSPDepart() {
		return this.TSPDepart;
	}

	public void setTSPDepart(TSDepart TSPDepart) {
		this.TSPDepart = TSPDepart;
	}

	@Column(name = "departname", nullable = false, length = 100)
	public String getDepartname() {
		return this.departname;
	}

	public void setDepartname(String departname) {
		this.departname = departname;
	}
	
//	@Column(name = "description", length = 500)
//	public String getDescription() {
//		return this.description;
//	}
//
//	public void setDescription(String description) {
//		this.description = description;
//	}
	
	@Column(name = "areacode", nullable = false, length = 4)
	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	
	@Column(name = "departtype", nullable = false)
	public Integer getDeparttype() {
		return departtype;
	}

	public void setDeparttype(Integer departtype) {
		this.departtype = departtype;
	}
	
	@Column(name = "isInclude")
	public Integer getIsInclude() {
		return isInclude;
	}

	public void setIsInclude(Integer isInclude) {
		this.isInclude = isInclude;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSPDepart")
	public List<TSDepart> getTSDeparts() {
		return TSDeparts;
	}

	public void setTSDeparts(List<TSDepart> tSDeparts) {
		TSDeparts = tSDeparts;
	}
}