package com.lizhou.bean;

/**
 * 领导与考核项目的具体对应
 * @author wuxiaoting
 *
 */
public class CourseItem {
	
	private Clazz clazz; //工作小组
	
	private int clazzid; //工作小组ID
	
	private Grade grade; //部门
	
	private int gradeid; //部门ID
	
	private Course course; //考核项目
	
	private int courseid; //考核项目ID
	
	private Teacher teacher; //领导
	
	private int teacherid; //领导ID

	public Clazz getClazz() {
		return clazz;
	}

	public void setClazz(Clazz clazz) {
		this.clazz = clazz;
	}

	public int getClazzid() {
		return clazzid;
	}

	public void setClazzid(int clazzid) {
		this.clazzid = clazzid;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public int getGradeid() {
		return gradeid;
	}

	public void setGradeid(int gradeid) {
		this.gradeid = gradeid;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public int getCourseid() {
		return courseid;
	}

	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public int getTeacherid() {
		return teacherid;
	}

	public void setTeacherid(int teacherid) {
		this.teacherid = teacherid;
	}

	
}
