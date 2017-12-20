package com.taotao.portal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/page")
@Controller
public class PageController {
	
	
	@RequestMapping("/{paramName}")
	public String toPage(@PathVariable("paramName") String paramName) {
		return paramName;
	}
	
}
