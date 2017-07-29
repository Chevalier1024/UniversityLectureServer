package com.universitylecture.universitylecture.dao.impl;

import java.sql.*;

import com.universitylecture.universitylecture.dao.UserDao;
import com.universitylecture.universitylecture.pojo.User;
import com.universitylecture.universitylecture.util.DBUtil;
import com.universitylecture.universitylecture.util.MD5Util;



public class UserDaoImpl implements UserDao{

	
	/*
	 * 1：成功登录
	 * 2：手机号不存在
	 * 3：密码错误
	 * 
	 */
	public User login(String phoneNumber, String password) {
		String sql = "select * from user "
				+ "where phoneNumber = ? and password = ?";
		
		//实例化数据库连接工具，获得连接，准备PreparedStatement和结果集
		DBUtil util = new DBUtil();
		Connection conn = util.openConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			//获得prepareStatement
			ps = conn.prepareStatement(sql);
			
			//设置查询参数
			ps.setString(1, phoneNumber);
			ps.setString(2, MD5Util.getStringMD5(password));
			
			//执行查询
			rs = ps.executeQuery();
			
			//判断邮箱是否存在
			if(rs.next()) {
				Integer id = rs.getInt(1);
				String name = rs.getString(2);
				String sex = rs.getString(4);
				String studentNumber = rs.getString(6);
				
				User user = new User(id,name,password,sex,phoneNumber,	studentNumber);
				return user;
			}
			
		}  catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
					rs = null;
				}
				if(ps != null) {
					ps.close();
					ps = null;
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
			util.closeConnection(conn);
		}
		
		return null;	
	}
	
	public User register(String name,String studentNumber,String password,String sex,String phoneNumber) {
		
		String sql = "insert into user(name,studentNumber,password,sex,phoneNumber)  values (?,?,?,?,?)";
		
		//实例化数据库连接工具，获得连接，准备PreparedStatement和结果集
		DBUtil util = new DBUtil();
		Connection conn = util.openConnection();
		PreparedStatement ps = null;
		
		try {
			
			//获得prepareStatement
			ps = conn.prepareStatement(sql);
			
			//设置查询参数
			ps.setString(1, name);
			ps.setString(2, studentNumber);
			ps.setString(3, MD5Util.getStringMD5(password));
			ps.setString(4, sex);
			ps.setString(5,phoneNumber);
			
			int result = ps.executeUpdate();
			
			//判断用户是否存在
			if(result == 1) {			
				User user = new User(name,studentNumber,password,sex,phoneNumber);	
				return user;
			}
			
		}  catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null) {
					ps.close();
					ps = null;
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
			util.closeConnection(conn);
		}
		
		return null;	
		
	}
	
	public User updateUser(User user) {

		String sql = "update user SET sex = ?,name = ?,phoneNumber = ? where studentNumber = ? ";
		
		//实例化数据库连接工具，获得连接，准备PreparedStatement和结果集
		DBUtil util = new DBUtil();
		Connection conn = util.openConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			//获得prepareStatement
			ps = conn.prepareStatement(sql);
			
			//设置查询参数
			ps.setString(1, user.getSex());
			ps.setString(2, user.getName());
			ps.setString(3, user.getPhoneNumber());
			ps.setString(4, user.getStudentNumber());
			
			//执行查询
			int result = ps.executeUpdate();
			
			//判断邮箱是否存在
			if(result != 0) {
				return user;
			}
			
		}  catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
					rs = null;
				}
				if(ps != null) {
					ps.close();
					ps = null;
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
			util.closeConnection(conn);
		}
		return null;	
	}
	

}
