package com.lizhou.bean;

/**
 * 系统初始化的一些信息
 * @author wuxiaoting
 *
 */
public class SystemInfo {
	
	private int id; 
	
	private String schoolName; //公司名称
	
	private int forbidTeacher; //禁止领导登录系统
	
	private int forbidStudent; //禁止员工登录系统
	
	private String noticeTeacher; //领导通知
	
	private String noticeStudent; //员工通知

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public int getForbidTeacher() {
		return forbidTeacher;
	}

	public void setForbidTeacher(int forbidTeacher) {
		this.forbidTeacher = forbidTeacher;
	}

	public int getForbidStudent() {
		return forbidStudent;
	}

	public void setForbidStudent(int forbidStudent) {
		this.forbidStudent = forbidStudent;
	}

	public String getNoticeTeacher() {
		return noticeTeacher;
	}

	public void setNoticeTeacher(String noticeTeacher) {
		this.noticeTeacher = noticeTeacher;
	}

	public String getNoticeStudent() {
		return noticeStudent;
	}

	public void setNoticeStudent(String noticeStudent) {
		this.noticeStudent = noticeStudent;
	}
	
}
