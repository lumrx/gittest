package com.itheiam.springboot;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itheiam.springboot.pojo.User;
import com.itheiam.springboot.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class UserTest {
	
	@Autowired
	private UserService service;

	@Test
	public void test() {
		
		User user = new User();
		user.setAccount("itheima");
		user.setAge(23);
		user.setBirthday(new Date());
		user.setCreateTime(new Date());
		user.setGender(1);
		user.setUserName("老王");
		user.setPassword("123456");
		User saveUser = service.saveUser(user);
		System.out.println(saveUser);
	}

}
