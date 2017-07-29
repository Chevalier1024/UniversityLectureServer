package com.universitylecture.universitylecture.servlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.net.httpserver.HttpServer;
import com.universitylecture.universitylecture.dao.LectureDao;
import com.universitylecture.universitylecture.dao.impl.LectureDaoImpl;
import com.universitylecture.universitylecture.pojo.Lecture;


@WebServlet("/SelectLectureByIDServlet")
public class SelectLectureByIDServlet extends HttpServlet {
	

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
		ServletInputStream in = request.getInputStream();
		Lecture returnLecture = null;
		ObjectInputStream inObj = new ObjectInputStream(in);
		
		try {
			returnLecture = (Lecture) inObj.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			inObj.close();
		}
	
		
		//实例化数据访问对象
		LectureDao lectureDao = new LectureDaoImpl();
		ArrayList<Lecture> lectures = lectureDao.selectByID(returnLecture.getID());
		
		response.setContentType("application/x-java-serialized-object");
		OutputStream os = response.getOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		
		oos.writeObject(lectures);
		oos.flush();
		os.close();
	}


}
