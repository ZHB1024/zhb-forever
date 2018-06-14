package com.forever.zhb.dao.impl;

import com.forever.zhb.dao.UserDAO;
import com.forever.zhb.dao.base.BaseHibernateDAO;
import com.forever.zhb.pojo.UserInfoData;
import com.forever.zhb.utils.StringUtil;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

public class UserDAOImpl extends BaseHibernateDAO implements UserDAO {


    @Override
    public void saveOrUpdate(UserInfoData userInfoData){
        try{
            if (null == userInfoData) {
                return ;
            }
            if (StringUtils.isBlank(userInfoData.getId())) {
                sessionFactory.getCurrentSession().save(userInfoData);
            }else{
                sessionFactory.getCurrentSession().update(userInfoData);
            }
        }catch(Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public UserInfoData getUserInfoById(String id) {
        String hql = "select p from UserInfoData p where p.id = :id ";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("id", id);
        List<UserInfoData> result = query.list();
        return result.size() == 0?null :result.get(0);
    }

    @Override
    public UserInfoData getUserInfoByName(String name){
        String hql = "select p from UserInfoData p where p.name = :name ";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("name", name);
        List<UserInfoData> result = query.list();
        return result.size() == 0?null :result.get(0);
    }

    @Override
    public int countUserInfos(){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserInfoData.class);
        criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
        return  Integer.parseInt(criteria.uniqueResult().toString());
    }

    @Override
    public List<UserInfoData> getUserInfos(int start,int pageSize){
        /*Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserInfoData.class);
        criteria.addOrder(Order.asc("createTime"));
        criteria.setFirstResult(start);
        criteria.setMaxResults(pageSize);
        List<UserInfoData> result = criteria.list();*/

        String hql = "select p from UserInfoData p ";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setFirstResult(start);
        query.setMaxResults(pageSize);
        List<UserInfoData> result = query.list();
        return result;
    }


}
