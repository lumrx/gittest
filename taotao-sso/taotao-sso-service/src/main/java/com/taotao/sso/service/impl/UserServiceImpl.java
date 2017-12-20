package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.redis.RedisService;
import com.taotao.sso.mapper.UserMapper;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	
	private static final String TICKET_PREFIX  = "TAOTAO_TICKET_";

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private RedisService redisService;
	
	private ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public Boolean checkName(String param, Integer type) {
		
		User user = new User();
		
		switch (type) {
		case 1:
			user.setUsername(param);
			break;
		case 2:
			user.setPhone(param);
			break;
		default:
			user.setEmail(param);
			break;
		}
		
		int count = userMapper.selectCount(user);
		
		return count==0;
	}

	@Override
	public String ticketCheck(String ticket) {
		
		ticket = TICKET_PREFIX + ticket;
		
		String str = redisService.get(ticket);
		
		if (StringUtils.isNotBlank(str)) {
			
			redisService.expire(ticket, 3600);
			
		}
		
		return str;
	}

	@Override
	public void doRegister(User user) {
		
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		userMapper.insert(user);
	}

	@Override
	public String doLogin(String username, String password) throws Exception {
		String ticket = "";
		User user = new User();
		user.setUsername(username);
		user.setPassword(DigestUtils.md5Hex(password));
		List<User> list = userMapper.select(user);
		if (list != null && list.size()>0) {
			User temp = list.get(0);
			
			//生成ticket
			ticket = DigestUtils.md5Hex(username+System.currentTimeMillis());
			redisService.setex(TICKET_PREFIX + ticket, 3600, MAPPER.writeValueAsString(temp));
		}
		
		return ticket;
	}

	@Override
	public void logout(String ticket) {
		redisService.del(TICKET_PREFIX+ticket);
	}
}
