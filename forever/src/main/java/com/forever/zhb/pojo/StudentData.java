package com.forever.zhb.pojo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="T_STUDENT")
public class StudentData {
	
	private String id;
	private String name;
	private int age;
	private Calendar rxrq;

	@Id
    @GeneratedValue(generator = "app_seq")
    @GenericGenerator(name = "app_seq", strategy = "com.forever.zhb.pojo.strategy.StringRandomGenerator")
    @Column(name = "ID")
	public String getId() {
		return id;
	}

	public void setId(String paramString) {
	}

	@Column(name= "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name= "AGE")
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Column(name= "RXRQ")
	public Calendar getRxrq() {
		return rxrq;
	}

	public void setRxrq(Calendar rxrq) {
		this.rxrq = rxrq;
	}
    
}
