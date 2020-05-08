package com.lizhou.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import com.lizhou.bean.Clazz;
import com.lizhou.bean.Grade;
import com.lizhou.bean.Page;
import com.lizhou.bean.Student;
import com.lizhou.dao.inter.BaseDaoInter;
import com.lizhou.dao.inter.StudentDaoInter;
import com.lizhou.tools.MysqlTool;

public class StudentDaoImpl extends BaseDaoImpl implements StudentDaoInter {

	public List<Student> getStudentList(String sql, List<Object> param) {
		//数据集合
		List<Student> list = new LinkedList<>();
		try {
			//获取数据库连接
			Connection conn = MysqlTool.getConnection();
			//预编译
			PreparedStatement ps = conn.prepareStatement(sql);
			//设置参数
			if(param != null && param.size() > 0){
				for(int i = 0;i < param.size();i++){
					ps.setObject(i+1, param.get(i));
				}
			}
			//执行sql语句
			ResultSet rs = ps.executeQuery();
			//获取元数据
			ResultSetMetaData meta = rs.getMetaData();
			//遍历结果集
			while(rs.next()){
				//创建对象
				Student stu = new Student();
				//遍历每个字段
				for(int i=1;i <= meta.getColumnCount();i++){
					String field = meta.getColumnName(i);
					BeanUtils.setProperty(stu, field, rs.getObject(field));
				}
				//查询小组
				Clazz clazz = (Clazz) getObject(Clazz.class, "SELECT * FROM clazz WHERE id=?", new Object[]{stu.getClazzid()});
				//查询部门
				Grade grade = (Grade) getObject(Grade.class, "SELECT * FROM grade WHERE id=?", new Object[]{stu.getGradeid()});
				//添加
				stu.setClazz(clazz);
				stu.setGrade(grade);
				//添加到集合
				list.add(stu);
			}
			//关闭连接
			MysqlTool.closeConnection();
			MysqlTool.close(ps);
			MysqlTool.close(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public String getStudentName(int id){
		String sql = "select name from student where id = ?";
		//获取数据库连接
		Connection conn = MysqlTool.getConnection();
		
		//预编译
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setObject(1, id);
			//执行sql语句
			ResultSet rs = ps.executeQuery();
			//获取元数据
			ResultSetMetaData meta = rs.getMetaData();
			while(rs.next()){
				//创建对象
				Student stu = new Student();
				//遍历每个字段
				for(int i=1;i <= meta.getColumnCount();i++){
					String field = meta.getColumnName(i);
					return rs.getObject(field).toString();
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
