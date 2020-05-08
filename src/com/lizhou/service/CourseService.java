package com.lizhou.service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.lizhou.bean.Clazz;
import com.lizhou.bean.Course;
import com.lizhou.bean.Grade;
import com.lizhou.bean.Page;
import com.lizhou.bean.Student;
import com.lizhou.dao.impl.BaseDaoImpl;
import com.lizhou.dao.impl.ClazzDaoImpl;
import com.lizhou.dao.inter.BaseDaoInter;
import com.lizhou.dao.inter.ClazzDaoInter;
import com.lizhou.tools.MysqlTool;
import com.lizhou.tools.StringTool;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 考核项目服务层
 * @author wuxiaoting
 *
 */
public class CourseService {
	
	BaseDaoInter dao = new BaseDaoImpl();
	
	/**
	 * 获取所有考核项目
	 * @return
	 */
	public String getCourseList(String gradeid){
		List<Object> list;
		if(StringTool.isEmpty(gradeid)){
			list = dao.getList(Course.class, "SELECT * FROM course");
		} else{
			list = dao.getList(Course.class, 
					"SELECT c.* FROM course c, grade_course gc WHERE c.id=gc.courseid AND gc.gradeid=?", 
					new Object[]{Integer.parseInt(gradeid)});
		}
		//json化
        String result = JSONArray.fromObject(list).toString();
        
        return result;
	}

	/**
	 * 添加考核项目
	 * @param course
	 */
	public void addCourse(Course course) {
		dao.insert("INSERT INTO course(name) value(?)", new Object[]{course.getName()});
	}

	/**
	 * 删除考核项目
	 * @param courseid
	 * @throws Exception 
	 */
	public void deleteClazz(int courseid) throws Exception {
		//获取连接
		Connection conn = MysqlTool.getConnection();
		try {
			//开启事务
			MysqlTool.startTransaction();
			//删除绩效表
			dao.deleteTransaction(conn, "DELETE FROM escore WHERE courseid=?", new Object[]{courseid});
			//删除班级的考核项目和领导的关联
			dao.deleteTransaction(conn, "DELETE FROM clazz_course_teacher WHERE courseid=?", new Object[]{courseid});
			//删除部门与考核项目关联
			dao.deleteTransaction(conn, "DELETE FROM grade_course WHERE courseid=?",  new Object[]{courseid});
			//最后删除考核项目
			dao.deleteTransaction(conn, "DELETE FROM course WHERE id=?",  new Object[]{courseid});
			
			//提交事务
			MysqlTool.commit();
		} catch (Exception e) {
			//回滚事务
			MysqlTool.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			MysqlTool.closeConnection();
		}
	}
	
	
	
	
}
