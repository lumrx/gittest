package com.itheiam.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itheiam.springboot.pojo.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
		List<User> findByUserNameLikeOrderByAgeDesc(String userName);

}
