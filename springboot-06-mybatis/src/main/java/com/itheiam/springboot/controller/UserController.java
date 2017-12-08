package com.itheiam.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itheiam.springboot.pojo.User;
import com.itheiam.springboot.service.UserService;

@RequestMapping("/user")
@Controller
public class UserController {
	
	@Autowired
	private UserService service;
	
	@RequestMapping("/queryAll")
	@ResponseBody
	public List<User> queryAll() {
		List<User> users = service.queryAll();
		return users;
	}
	
}
