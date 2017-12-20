package com.taotao.portal;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.portal.util.CookieUtils;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;

@RequestMapping("/user")
@Controller
public class UserController {
	
	private static final String USER_TICKET = "TT_TICKET";
	private static final int TICKET_TIME = 3600;
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value="/doRegister",method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> doRegister(User user){
		
		Map<String, Object> map = new HashMap<>();
		map.put("status", 500);
		
		try {
			userService.doRegister(user);
			map.put("status", 200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(map);
	}
	
	
	@RequestMapping(value="/doLogin")
	public ResponseEntity<Map<String, Object>> doLogin(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("username") String username,@RequestParam("password")String password){
		Map<String, Object> map = new HashMap<>();
		map.put("status", 500);
		
		try {
			
			String ticket = userService.doLogin(username,password);
			
			if (StringUtils.isNotBlank(ticket)) {
				map.put("status", 200);
				//存入cookis中
				CookieUtils.setCookie(request, response, USER_TICKET, ticket, TICKET_TIME);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(map);
	}
	
	
	@RequestMapping(value="/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response){
			CookieUtils.deleteCookie(request, response, USER_TICKET);
			String ticket = CookieUtils.getCookieValue(request, USER_TICKET);
			userService.logout(ticket);
			return "redirect:/index.html";
	}
}
