package com.forever.zhb.mybatis.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.forever.zhb.mybatis.dao.UserMapper;
import com.forever.zhb.mybatis.pojo.User;
import com.forever.zhb.mybatis.service.UserService;

@Service("userService") 
public class UserServiceImpl implements UserService {
	
	@Resource  
    private UserMapper userMapper;  

	@Override
	public User getUserById(int userId) {
		return this.userMapper.selectByPrimaryKey(userId);  
	}

}
