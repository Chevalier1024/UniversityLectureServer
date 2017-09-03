package com.universitylecture.universitylecture.dao;

import java.util.ArrayList;

import com.universitylecture.universitylecture.pojo.Lecture;
import com.universitylecture.universitylecture.pojo.LecturePublisher;
import com.universitylecture.universitylecture.pojo.User;

public interface LectureDao {
	
	public ArrayList<Lecture> select(String dateTime,String institue,int counter);
	
	public ArrayList<Lecture> selectByID(String ID);
	
	public Lecture add(Lecture lecture);
	
	public User isLecturePublisher(User user);
}
