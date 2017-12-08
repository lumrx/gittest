package com.itheiam.springboot.service;

import java.util.List;

import com.itheiam.springboot.pojo.User;

public interface UserService {
	
	List<User> findByUserNameLikeOrderByAgeDesc(String userName);
	
	User saveUser(User user);

}
