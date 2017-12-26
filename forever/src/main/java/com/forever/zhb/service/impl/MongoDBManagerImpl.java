package com.forever.zhb.service.impl;

import java.util.List;

import com.forever.zhb.dao.IMongoDBDAO;
import com.forever.zhb.mongo.model.UserModel;
import com.forever.zhb.service.IMongoDBManager;

public class MongoDBManagerImpl implements IMongoDBManager {
	
	private IMongoDBDAO mongoDBDao;
	
	public List<UserModel> findAll(){
		return mongoDBDao.findAll();
	}
	
	public void insertUser(UserModel user){
		mongoDBDao.insertUser(user);
	}
	

	public IMongoDBDAO getMongoDBDao() {
		return mongoDBDao;
	}

	public void setMongoDBDao(IMongoDBDAO mongoDBDao) {
		this.mongoDBDao = mongoDBDao;
	}
}
