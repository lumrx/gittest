package com.itheiam.springboot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itheiam.springboot.mapper.UserMapper;
import com.itheiam.springboot.pojo.User;
import com.itheiam.springboot.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	
	@Autowired
	private UserMapper mapper;

	@Override
	public List<User> queryAll() {
		
		return mapper.queryUsers();
	}

}
