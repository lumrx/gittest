package com.taotao.sso.service;

import com.taotao.sso.pojo.User;

public interface UserService {

	Boolean checkName(String param, Integer type);

	String ticketCheck(String ticket);

	void doRegister(User user);

	String doLogin(String username, String password)throws Exception;

	void logout(String ticket);

}
