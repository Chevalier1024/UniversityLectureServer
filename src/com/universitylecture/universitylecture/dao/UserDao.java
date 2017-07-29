package com.universitylecture.universitylecture.dao;

import com.universitylecture.universitylecture.pojo.User;


public interface UserDao {
	
	//登录方法
	public User login(String phoneNumber,String password);
	
	//注册
	public User register(String name,String studentNumber,String password,String sex,String phoneNumber);
	
	public User updateUser(User user);
	

}
