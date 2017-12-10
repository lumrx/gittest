package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taotao.manage.service.TestService;

@RequestMapping("/test")
@RestController
public class TestController {
	
	
	@Autowired
	private TestService service;
	
	
	@RequestMapping("demo")
	public String queryCurrentDate() {
		return service.queryCurrentDate();
	}
	
}
