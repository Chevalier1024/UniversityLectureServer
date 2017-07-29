package com.universitylecture.universitylecture.util;

import java.util.Properties;
import java.io.IOException;
import java.sql.*;

public class DBUtil {
	
	/*    打开数据库连接    */
	
	public Connection openConnection() {
		
		Properties prop = new Properties();
		String driver = null;
		String url = null;
		String username = null;
		String password = null;
		
		try {
			//将properties配置文件载入Properties实例
			prop.load(this.getClass().getClassLoader().getResourceAsStream(
					 "DBConfig.properties"));
			
			driver = prop.getProperty("driver");
			url = prop.getProperty("url");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
			
			//加载驱动
			Class.forName(driver);
			
			return DriverManager.getConnection(url,username,password);	
		
		}   catch(IOException e) {
			e.printStackTrace();
		}   catch(ClassNotFoundException e) {
			e.printStackTrace();
		}   catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;						
	}
	
	/*   关闭数据库连接    */
	public void closeConnection(Connection conn) {
		
		try {
				conn.close();
		} catch(SQLException e) { 
			e.printStackTrace();
		}
	}
	
	
	
	

}
