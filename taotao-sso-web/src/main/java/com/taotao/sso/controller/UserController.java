package com.taotao.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.sso.service.UserService;

@RequestMapping("/user")
@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/check/{param}/{type}",method=RequestMethod.GET)
	public ResponseEntity<String> check(@PathVariable String param, @PathVariable Integer type,@RequestParam("callback") String callback){
		try {
			String flag = "false";
			if (type >0 && type < 4) {
				Boolean bool = userService.checkName(param,type);
				flag = bool.toString();
				if (StringUtils.isNotBlank(callback)) {
					flag = callback+"("+flag+")";
				}
				return ResponseEntity.ok(flag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
	
	@RequestMapping(value="{ticket}",method=RequestMethod.GET)
	public ResponseEntity<String> queryUser(@PathVariable String ticket,@RequestParam("callback") String callback){
		
		try {
			if (StringUtils.isNotBlank(ticket)) {
				String result = userService.ticketCheck(ticket);
				if (StringUtils.isNotBlank(callback)) {
					result = callback +"("+result+")";
				}
				return ResponseEntity.ok(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
}
