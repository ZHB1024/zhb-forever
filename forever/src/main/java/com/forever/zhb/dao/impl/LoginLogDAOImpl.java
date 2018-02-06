package com.forever.zhb.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.forever.zhb.dao.LoginLogDAO;
import com.forever.zhb.dao.base.BaseHibernateDAO;
import com.forever.zhb.pojo.LoginLogInfoData;

public class LoginLogDAOImpl extends BaseHibernateDAO implements LoginLogDAO {
	
	private static final String SELECT_LOGINLOGINFO= " select p from LoginLogInfoData p ";
	
	private static final String _W = " where ";
	private static final String _A = " and ";
    private static final String _CON_ID = " p.id = :id";
    private static final String _CON_USERNAME = " p.userName = :userName";
    private static final String _ORDER_BY_SORT = " order by p.createTime desc";
	
	@Override
	public void save(LoginLogInfoData loginLogInfoData) {
		sessionFactory.getCurrentSession().save(loginLogInfoData);
	}

	@Override
	public List<LoginLogInfoData> getLoginLogInfoByUserName(String userName) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoginLogInfoData.class);
		/*crit.add(Restrictions.in("auditedResult", auditReultTypesList));*/
		criteria.add(Restrictions.eq("userName", userName));
		criteria.addOrder(Order.desc("createTime"));
		return criteria.list();
	}
	
	@Override
	public int countLoginLogInfoByUserName(String userName){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoginLogInfoData.class);
		criteria.add(Restrictions.eq("userName", userName));
		criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
		return Integer.parseInt(criteria.uniqueResult().toString());
	}
	
	@Override
	public List<LoginLogInfoData> getLoginLogInfoByUserNamePage(String userName,int start,int pageSize){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoginLogInfoData.class);
		/*crit.add(Restrictions.in("auditedResult", auditReultTypesList));*/
		criteria.add(Restrictions.eq("userName", userName));
		criteria.addOrder(Order.desc("createTime"));
		criteria.setFirstResult(start);
		criteria.setMaxResults(pageSize);
		return criteria.list();
	}
	
	public int countLoginLogInfo(){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoginLogInfoData.class);
		criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
		return Integer.parseInt(criteria.uniqueResult().toString());
	}

	@Override
	public List<LoginLogInfoData> getLoginLogInfo() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoginLogInfoData.class);
		/*crit.add(Restrictions.in("auditedResult", auditReultTypesList));*/
		/*criteria.add(Restrictions.eq(propertyName, value))*/
		criteria.addOrder(Order.desc("createTime"));
		return criteria.list();
	}
	
	@Override
	public List<LoginLogInfoData> getLoginLogInfoPage(int start,int pageSize) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoginLogInfoData.class);
		/*crit.add(Restrictions.in("auditedResult", auditReultTypesList));*/
		/*criteria.add(Restrictions.eq(propertyName, value))*/
		criteria.addOrder(Order.desc("createTime"));
		criteria.setFirstResult(start);
		criteria.setMaxResults(pageSize);
		return criteria.list();
	}

}
