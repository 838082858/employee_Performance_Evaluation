package com.lizhou.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 考核计划类
 * 
 * 分为季度考核和平时考核两种
 * 季度考核由管理员添加
 * 平时考核由领导添加
 * @author wuxiaoting
 *
 */
public class Exam {
	
	/**
	 * 类型：季度考核
	 */
	public static final int EXAM_GRADE_TYPE = 1;
	
	/**
	 * 类型：平时考核
	 */
	public static final int EXAM_NORMAL_TYPE = 2;
	
	private int id; //ID
	
	private String name; //考核项目名称
	
	private Date time; //考核时间
	
	private String etime; //考核时间
	
	private String remark; //备注
	
	private Grade grade; //考核部门
	
	private int gradeid; //部门ID
	
	private Clazz clazz; //部门的小组: 平时考核只涉及到部门下的某个小组， 季度考核则为所有小组
	
	private int clazzid; //小组ID
	
	private Course course; //考核项目情况
	
	private int courseid; //考核项目ID
	
	private int type = EXAM_GRADE_TYPE; //类型:默认为1,1为季度考核，2为平时考核

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy 年  MM 月  dd 日");
		this.etime = sdf.format(time);
		this.time = time;
	}
	
	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.time = sdf.parse(etime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.etime = etime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
		Grade grade = new Grade();
		grade.setId(gradeid);
		this.grade = grade;
		this.gradeid = gradeid;
	}

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
		Clazz clazz = new Clazz();
		clazz.setId(clazzid);
		this.clazz = clazz;
		this.clazzid = clazzid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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
		Course course = new Course();
		course.setId(courseid);
		this.course = course;
		this.courseid = courseid;
	}

}
