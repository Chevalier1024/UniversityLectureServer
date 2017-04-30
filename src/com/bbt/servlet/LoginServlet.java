package com.bbt.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bbt.dao.UserDao;
import com.bbt.dao.impl.UserDaoImpl;
import com.bbt.entity.User;

public class LoginServlet  extends HttpServlet {
	
	public void doGet(HttpServletRequest request,HttpServletResponse response)
				throws ServletException,IOException {
		
		response.setContentType("text/html");
		//获得响应的输出流
		PrintWriter pw = response.getWriter();		
		
		pw.print("<html><p>hello world</p></html>");
		
		//获得客户端请求的参数
		String studentNumber = request.getParameter("studentNumber");
		String passwd = request.getParameter("passwd");
		
		//实例化数据访问对象
		UserDao dao = new UserDaoImpl();
		User user = dao.login(studentNumber, passwd);
		
		if(user != null) {
			//响应客户端成功,登录成功
			pw.print(build(user));
		} else {
			//响应客户端失败,登录失败
			pw.print("login error");
		}
		pw.flush();
		pw.close();		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException,IOException {
		doGet(request,response);
	}
	
	private String build(User u) {
		String userMsg = "";
		userMsg += "studentNumber=" + u.getStudentNumber();
		userMsg +=";";
		userMsg +="name=" + u.getName();
		return userMsg;
	}

}
