package com.forever.zhb.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.forever.zhb.dao.InitDAO;
import com.forever.zhb.pojo.FunctionInfoData;

public class InitDAOImpl implements InitDAO {
    
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Object object) {
        sessionFactory.getCurrentSession().save(object);
    }
    
    public void save(FunctionInfoData functionInfoData){
        Session session = sessionFactory.getCurrentSession();
        session.save(functionInfoData);
    }
    
    public List<FunctionInfoData> getFunctionInfo(){
        String hql = "select p from FunctionInfoData p ";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.list();
    }

}
