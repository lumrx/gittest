package com.taotao.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/page")
@Controller
public class PageController {
	
	
	@RequestMapping("/{pageName}")
	public String toPage(@PathVariable(value="pageName") String pageName) {
		return pageName;
	}

}
