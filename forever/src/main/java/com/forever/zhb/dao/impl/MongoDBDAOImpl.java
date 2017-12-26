package com.forever.zhb.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.forever.zhb.dao.IMongoDBDAO;
import com.forever.zhb.mongo.model.UserModel;
import com.forever.zhb.mongo.template.AbstractBaseMongoTemplate;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MongoDBDAOImpl extends AbstractBaseMongoTemplate implements IMongoDBDAO {

	public List<UserModel> findAll() {
		// 需要设置集合对应的尸体类和相应的集合名，从而查询结果直接映射  
        List<UserModel> userList = mongoTemplate.findAll(UserModel.class,"student");  
        return userList;  
	}
	
	public List<UserModel> findForRequery(String userName) {
		Query query = new Query();  
        Criteria criteria = new Criteria("name");  
        criteria.is(userName);  
        query.addCriteria(criteria);  
        // 查询条件，集合对应的实体类，集合名  
        List<UserModel> userList = mongoTemplate.find(query, UserModel.class,  
                "student");  
        return userList;  
	}


	public void insertUser(UserModel user) {
		// 设置插入到数据库zhbDB的collection student的文档对象  
        DBObject object = new BasicDBObject();  
        object.put("name", user.getName());  
        object.put("sex", user.getSex());  
        mongoTemplate.insert(object, "student");
	}

	public void removeUser(String userName) {
		// 设置删除条件，如果条件内容为空则删除所有  
        Query query = new Query();  
        Criteria criteria = new Criteria("name");  
        criteria.is(userName);  
        query.addCriteria(criteria);  
        mongoTemplate.remove(query, "student");  
	}

	public void updateUser(UserModel user) {
		// 设置修改条件  
        Query query = new Query();  
        Criteria criteria = new Criteria("name");  
        criteria.is(user.getName());  
        query.addCriteria(criteria);  
        // 设置修改内容  
        Update update = Update.update("sex", user.getSex());  
        // 参数：查询条件，更改结果，集合名  
        mongoTemplate.updateFirst(query, update, "student");  
	}

}
