package com.universitylecture.universitylecture.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.universitylecture.universitylecture.dao.LectureDao;
import com.universitylecture.universitylecture.pojo.Lecture;
import com.universitylecture.universitylecture.pojo.LecturePublisher;
import com.universitylecture.universitylecture.pojo.User;
import com.universitylecture.universitylecture.util.DBUtil;

public class LectureDaoImpl implements LectureDao{

	public ArrayList<Lecture> select(String dateTime, String institute) {
		
		String day = "";
		
		System.out.println(dateTime+ "  " +institute );
		
		switch(dateTime) {
			case "一天之内": day = "1";break;
			case "三天之内": day = "3";break;
			case "五天之内": day = "5";break;
			case "七天之内": day = "7";break;
			default: day = "100";break;
		}
		
		String sql = "";
		if(institute.equals("不限"))
			sql = "select * from lecture where dateTime between now() and DATE_ADD(now(), INTERVAL " + day + " DAY)";
		else 
			sql = "select * from lecture where dateTime between now() and DATE_ADD(now(), INTERVAL " + day + " DAY)"
					+ " and institute = ?";
		
		
		//实例化数据库连接工具，获得连接，准备PreparedStatement和结果集
		DBUtil util = new DBUtil();
		Connection conn = util.openConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			//获得prepareStatement
			ps = conn.prepareStatement(sql);
			
			//设置查询参数
			if(!institute.equals("不限"))
				ps.setString(1, institute);
			
			//执行查询
			rs = ps.executeQuery();
			
			//判断讲座是否存在
			ArrayList<Lecture> lectures = new ArrayList<Lecture>();
			
			int count = 0;
			while(rs.next()) {
				
				Lecture lecture = new Lecture();
				
				lecture.setID(rs.getString(1));
				lecture.setTitle(rs.getString(2));
				lecture.setTime(rs.getString(3));
				lecture.setClassroom(rs.getString(4));
				lecture.setInstitute(rs.getString(5));
				lecture.setIntroduction(rs.getString(6));
				lecture.setLecturer(rs.getString(7));
				lecture.setCredit(rs.getString(8));
				lecture.setContent(rs.getString(9));
				lecture.setSponsor(rs.getString(10));
				lecture.setCo_sponsor(rs.getString(11));
				lecture.setImagePath(rs.getString(12));
				
				lectures.add(lecture);
				
				System.out.println(rs.getString(2));
				if(++count == 5)
					break;
				
			}
			
			return lectures;
			
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
	
	public User isLecturePublisher(User user) {
		
		String sql = "select * from lecture_publisher "
				+ "where phoneNumber = ?";
		
		//实例化数据库连接工具，获得连接，准备PreparedStatement和结果集
		DBUtil util = new DBUtil();
		Connection conn = util.openConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			//获得prepareStatement
			ps = conn.prepareStatement(sql);
			
			//设置查询参数
			ps.setString(1, user.getPhoneNumber());
			
			//执行查询
			rs = ps.executeQuery();
			
			//判断邮箱是否存在
			if(rs.next()) {
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
	
	public Lecture add(Lecture lecture) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = sdf.format(new Date());
	
		String sql = "insert into lecture(title,dateTime,classroom,institute,introduction,lecturer,credit,content,sponsor,co_sponsor,image) " 
							+" values (?,?,?,?,?,?,?,?,?,?,?)";
		
		//实例化数据库连接工具，获得连接，准备PreparedStatement和结果集
		DBUtil util = new DBUtil();
		Connection conn = util.openConnection();
		PreparedStatement ps = null;
		
		try {
			
			//获得prepareStatement
			ps = conn.prepareStatement(sql);
			
			//设置查询参数
			ps.setString(1, lecture.getTitle());
			ps.setString(2, lecture.getTime());
			ps.setString(3, lecture.getClassroom());
			ps.setString(4,lecture.getInstitute());
			ps.setString(5, lecture.getIntroduction());
			ps.setString(6, lecture.getLecturer());
			ps.setString(7, lecture.getCredit());
			ps.setString(8, lecture.getContent());
			ps.setString(9, lecture.getSponsor());
			ps.setString(10,lecture.getCo_sponsor());
			ps.setString(11, lecture.getImagePath());
			
			int result = ps.executeUpdate();
			
			//判断用户是否存在
			if(result == 1) {				
				return lecture;
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

	public ArrayList<Lecture> selectByID(String ID) {
		
		
		System.out.println("-----select by ID -------");
		String sql = "select * from lecture where ID > " + ID;
		
		
		//实例化数据库连接工具，获得连接，准备PreparedStatement和结果集
		DBUtil util = new DBUtil();
		Connection conn = util.openConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			//获得prepareStatement
			ps = conn.prepareStatement(sql);
			
			//执行查询
			rs = ps.executeQuery();
			
			//判断讲座是否存在
			ArrayList<Lecture> lectures = new ArrayList<Lecture>();
			
			int count = 0;
			while(rs.next()) {
				
				Lecture lecture = new Lecture();
				
				lecture.setID(rs.getString(1));
				lecture.setTitle(rs.getString(2));
				lecture.setTime(rs.getString(3));
				lecture.setClassroom(rs.getString(4));
				lecture.setInstitute(rs.getString(5));
				lecture.setIntroduction(rs.getString(6));
				lecture.setLecturer(rs.getString(7));
				lecture.setCredit(rs.getString(8));
				lecture.setContent(rs.getString(9));
				lecture.setSponsor(rs.getString(10));
				lecture.setCo_sponsor(rs.getString(11));
				lecture.setImagePath(rs.getString(12));
				
				lectures.add(lecture);
				
				if(++count == 5)
					break;
				System.out.println(rs.getString(2));
				
			}
			
			return lectures;
			
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
