package com.ClassTransaction.local;

import java.util.ArrayList;
import java.util.List;

import com.ClassTransaction.client.Course;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class CourseDBHelper extends BaseDBHelper implements CourseDBHelperI {
	
	public static	final String CREATE_TABLE_COURSE  = "CREATE TABLE t_course ("
			+ "ID varchar(255) NOT NULL default 0,"
			+ "COURSENAME varchar(255) NOT NULL default 0,"
			+ "COURSETIME varchar(255) NOT NULL default 0,"
			+ "COURSEPLACE varchar(255) NOT NULL default 0,"
			+ "COURSEMASTERID varchar(255) NOT NULL default 0,"
			+ "COURSECREDIT varchar(255) NOT NULL default 0,"
			+ "COURSETYPE varchar(255) NOT NULL default 0,"
			+ "COURSEREMARK varchar(255) NOT NULL default '' ,"
			+ "ISDELETE varchar(255) NOT NULL default 0,"
			+ "PRIMARY KEY  (ID,COURSETIME,COURSEPLACE))";
  

	public CourseDBHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_COURSE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String sql = "drop table if exists t_course;";
        db.execSQL(sql);
        onCreate(db);
	}

	public Course findCourse(String courseName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Course findCourse(String courseName, String courseTime,
			String coursePlace) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Course> findCourses() {
		// TODO Auto-generated method stub
		String sql = "select * from T_COURSE u where u.ISDELETE = '0'";
		List<Course> courses = (List<Course>)getDatas(sql, new ArrayList<Object>(), Course.class);
		return courses;
	}

	public boolean save(Course course) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = getWritableDatabase();
		try{
			String sql = "insert into T_COURSE VALUES ("
			+"'" + course.getID() + "','"
			+course.getCOURSENAME() + "', '"
			+ course.getCOURSETIME() + "', '"
			+ course.getCOURSEPLACE() + "', '"
			+ course.getCOURSEMASTERID() + "', '"
			+ course.getCOURSECREDIT() + "', '"
			+ course.getCOURSETYPE() + "', '"
			+ course.getCOURSEREMARK() + "', '"
			+ "0')";
			db.execSQL(sql);
			db.close();
		}catch(Exception e){
	    	  e.printStackTrace();
	    	  return false;
		  }
		return true;
	}

	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

	public int getCourseCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Course> query(String realCourse) {
		// TODO Auto-generated method stub
		return null;
	}

	public Course find(String courseId) {
		// TODO Auto-generated method stub
		return null;
	}

}