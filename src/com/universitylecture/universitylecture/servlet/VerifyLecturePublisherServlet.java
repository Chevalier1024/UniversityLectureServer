package com.universitylecture.universitylecture.servlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.universitylecture.universitylecture.dao.LectureDao;
import com.universitylecture.universitylecture.dao.UserDao;
import com.universitylecture.universitylecture.dao.impl.LectureDaoImpl;
import com.universitylecture.universitylecture.dao.impl.UserDaoImpl;
import com.universitylecture.universitylecture.pojo.LecturePublisher;
import com.universitylecture.universitylecture.pojo.User;


@WebServlet("/VerifyLecturePublisherServlet")
public class VerifyLecturePublisherServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException {
		doPost(request,response);	
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,IOException {
		
		request.setCharacterEncoding("UTF-8");
		//获得客户端请求的参数
		ServletInputStream in = request.getInputStream();
		User returnUser = null;
		ObjectInputStream inObj = new ObjectInputStream(in);
		
		try {
			returnUser = (User) inObj.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			inObj.close();
		}
	
		
		//实例化数据访问对象
		LectureDao dao = new LectureDaoImpl();
		User user = dao.isLecturePublisher(returnUser);
		
		response.setContentType("application/x-java-serialized-object");
		OutputStream os = response.getOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		
		oos.writeObject(user);
		oos.flush();
		os.close();
	}

}
