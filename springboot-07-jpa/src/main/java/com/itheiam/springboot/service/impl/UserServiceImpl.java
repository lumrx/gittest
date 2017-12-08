package com.itheiam.springboot.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itheiam.springboot.pojo.User;
import com.itheiam.springboot.repository.UserRepository;
import com.itheiam.springboot.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository repository;

	@Override
	public List<User> findByUserNameLikeOrderByAgeDesc(String userName) {
		return repository.findByUserNameLikeOrderByAgeDesc(userName);
	}
	
	@Transactional
	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return repository.save(user);
	}
	
	
	
}
