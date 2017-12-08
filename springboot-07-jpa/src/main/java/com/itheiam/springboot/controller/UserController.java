package com.itheiam.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itheiam.springboot.pojo.User;
import com.itheiam.springboot.service.UserService;

@RequestMapping("/user")
@RestController
public class UserController {
	
	@Autowired
	private UserService service;
	
	@RequestMapping("/findByUserName/{userName}")
	public List<User> findByUserName(@PathVariable String userName){
		return service.findByUserNameLikeOrderByAgeDesc("%"+userName+"%");
	}
	
}
