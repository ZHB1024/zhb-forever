package com.forever.zhb.dao.impl;

import com.forever.zhb.dao.AccountDAO;
import com.forever.zhb.dao.base.BaseHibernateDAO;
import com.forever.zhb.pojo.LoginInfoData;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;

public class AccountDAOImpl extends BaseHibernateDAO implements AccountDAO {

    @Override
    public void addOrUpdate(LoginInfoData loginInfoData) {
        if (null == loginInfoData) {
            return ;
        }
        if (StringUtils.isBlank(loginInfoData.getId())) {
            sessionFactory.getCurrentSession().save(loginInfoData);
        }else{
            sessionFactory.getCurrentSession().update(loginInfoData);
        }
    }

    @Override
    public LoginInfoData getAccountByName(String name) {
        String hql = "select p from LoginInfoData p where p.userInfoData.name = :name ";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("name", name);
        List<LoginInfoData> result = query.list();
        return result.size() == 0?null :result.get(0);
    }

    @Override
    public void deleteAccountByUserId(String userId){
        String hql = "update LoginInfoData p set p.deleteFlag = 1 where p.userInfoData.id = :userId ";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("userId", userId);
        query.executeUpdate();
    }
}
