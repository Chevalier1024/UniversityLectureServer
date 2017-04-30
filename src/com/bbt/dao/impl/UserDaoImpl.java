package com.bbt.dao.impl;

import java.sql.*;

import com.bbt.dao.UserDao;
import com.bbt.entity.User;
import com.bbt.util.DBUtil;

public class UserDaoImpl implements UserDao{

	
	/*
	 * 通过学号和密码登录，成功则返回User对象，失败则返回null
	 */
	public User login(String studentNumber, String passwd) {
		
		String sql = "select studentNumber,passwd,name  from user "
				+ "where studentNumber = ? and passwd = ?";
		
		//实例化数据库连接工具，获得连接，准备PreparedStatement和结果集
		DBUtil util = new DBUtil();
		Connection conn = util.openConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			//获得prepareStatement
			ps = conn.prepareStatement(sql);
			
			//设置查询参数
			ps.setString(1, studentNumber);
			ps.setString(2, passwd);
			
			//执行查询
			rs = ps.executeQuery();
			
			//判断用户是否存在
			if(rs.next()) {
				String name = rs.getString(3);
				
				User user = new User();
				user.setStudentNumber(studentNumber);
				user.setPasswd(passwd);
				user.setName(name);
				
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
