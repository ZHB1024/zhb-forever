package com.forever.zhb.dao.base;

import org.hibernate.SessionFactory;

public class BaseHibernateDAO {
	protected SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
}
