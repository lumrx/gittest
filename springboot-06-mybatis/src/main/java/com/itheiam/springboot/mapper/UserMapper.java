package com.itheiam.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.itheiam.springboot.pojo.User;

@Mapper
public interface UserMapper {
	
	public List<User> queryUsers();
	
}
