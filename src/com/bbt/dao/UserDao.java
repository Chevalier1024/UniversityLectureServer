package com.bbt.dao;

import com.bbt.entity.User;

public interface UserDao {
	
	//登录方法
	public User login(String studentNumber,String passwd);
	

}
