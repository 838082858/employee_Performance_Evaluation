package com.lizhou.bean;

/**
 * 考核成绩类
 * @author wuxiaoting
 *
 */
public class EScore {
	
	private int id; //ID
	
	private Exam exam; //考核
	
	private int examid; //考核ID
	
	private Clazz clazz; //考核工作小组
	
	private int clazzid; //工作小组ID
	
	private Course course; //考核项目
	
	private int courseid; //考核项目ID
	
	private Student student; //员工
	
	private int studentid; //员工ID
	
	private int score; //考核绩效

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public int getExamid() {
		return examid;
	}

	public void setExamid(int examid) {
		Exam exam = new Exam();
		exam.setId(examid);
		this.exam = exam;
		this.examid = examid;
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

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public int getStudentid() {
		return studentid;
	}

	public void setStudentid(int studentid) {
		Student student = new Student();
		student.setId(studentid);
		this.student = student;
		this.studentid = studentid;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
