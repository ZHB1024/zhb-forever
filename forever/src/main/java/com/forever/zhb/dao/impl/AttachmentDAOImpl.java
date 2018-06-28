package com.forever.zhb.dao.impl;

import com.forever.zhb.dao.AttachmentDAO;
import com.forever.zhb.dao.base.BaseHibernateDAO;
import com.forever.zhb.pojo.FileInfoData;
import com.forever.zhb.utils.StringUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttachmentDAOImpl extends BaseHibernateDAO implements AttachmentDAO {

    protected  final Logger logger = LoggerFactory.getLogger(AttachmentDAOImpl.class);


    @Override
    public void saveOrUpdate(FileInfoData fileInfoData){
        if (null == fileInfoData){
            return;
        }
        if (StringUtil.isBlank(fileInfoData.getId())){
            sessionFactory.getCurrentSession().save(fileInfoData);
        }else{
            sessionFactory.getCurrentSession().update(fileInfoData);
        }

    }

    @Override
    public int countFiles(Integer type, String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FileInfoData.class);
        if (null != type){
            criteria.add(Restrictions.eq("type",type));
        }
        if (StringUtil.isNotBlank(name)){
            criteria.add(Restrictions.like("fileName","%"+name+"%"));
        }
        criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
        return Integer.parseInt(criteria.uniqueResult().toString());
    }

    @Override
    public List<FileInfoData> queryFiles(Integer type, String name, int start, int pageSize) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FileInfoData.class);
        if (null != type){
            criteria.add(Restrictions.eq("type",type));
        }
        if (StringUtil.isNotBlank(name)){
            criteria.add(Restrictions.like("fileName","%"+name+"%"));
        }
        criteria.setFirstResult(start);
        criteria.setMaxResults(pageSize);
        criteria.addOrder(Order.desc("createTime"));
        return criteria.list();
    }

    @Override
    public FileInfoData  getFileById(String id){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FileInfoData.class);
        if (null != id){
            criteria.add(Restrictions.eq("id",id));
        }
        List<FileInfoData> files = criteria.list();
        return files.size()>0?files.get(0):null;
    }

}
