package com.ClassTransaction.local;

import java.util.List;

import com.ClassTransaction.client.Course;

public interface CourseDBHelperI {
	/**
	 * 根据课程名称查找课程
	
	 * @return
	 */
	Course findCourse(String courseName);
	
	/**
	 * 根据课程名，时间，地点称查找课程
	
	 * @return
	 */
	Course findCourse(String courseName,String courseTime,String coursePlace);
	
	
	/**
	 *查找全部的课程
	 * @return
	 */
	List<Course> findCourses();
	
	/**
	 * 保存新课程
	 * @param course
	 */
	boolean save(Course course);
	
	
	
	/**
	 * 修改课程
	 * @param user
	 */
	void delete(String id);
	
	/**
	 * 返回用户数
	 * @return
	 */
	int getCourseCount();
	
	/**
	 * 根据课程名模糊查找用户  正则表达式
	 * @param userName
	 * @return
	 */
	List<Course> query(String realCourse);
	
	/**
	 * 根据ID查找用户
	 * @param id
	 * @return
	 */
	Course find(String courseId);
}
