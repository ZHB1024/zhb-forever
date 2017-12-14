package com.forever.zhb.service;

import java.util.List;

import com.forever.zhb.mongo.model.UserModel;

public interface IMongoDBManager {
	
	List<UserModel> findAll();
	
	void insertUser(UserModel user);
	
}
