package com.lizhou.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.lizhou.bean.Clazz;
import com.lizhou.bean.Course;
import com.lizhou.bean.Exam;
import com.lizhou.bean.Grade;
import com.lizhou.bean.Leave;
import com.lizhou.bean.Page;
import com.lizhou.bean.Student;
import com.lizhou.tools.MysqlTool;

public class LeaveDaoImpl extends BaseDaoImpl {
	/**
	 * 添加请假信息
	 * @param leave
	 * @return
	 */
	public boolean addLeave(Leave leave){
		String sql = "insert into leaves values(null,"+leave.getStudentId()+",'"+leave.getInfo()+"',"+Leave.LEAVE_STATUS_WAIT+",'"+leave.getRemark()+"')";
		 update(sql);
		 return true;
	}
	
	/**
	 * 编辑请假单
	 * @param leave
	 * @return
	 */
	public boolean editLeave(Leave leave){
		String sql = "update leaves set student_id = "+leave.getStudentId()+", info = '"+leave.getInfo()+"',status = "+leave.getStatus()+",remark = '"+leave.getRemark()+"' where id = " + leave.getId();
		update(sql);
		return true;
	}
	
	/**
	 * 删除请假信息
	 * @param id
	 * @return
	 */
	public boolean deleteLeave(int id) {
		// TODO Auto-generated method stub
		String sql = "delete from leaves where id = " + id ;
		update(sql);
		return true;
	}
	
	/**
	 * 获取分页请假单信息列表
	 * @param leave
	 * @param page
	 * @return
	 */
	public List<Leave> getLeaveList(Leave leave,Page page){
		List<Leave> ret = new ArrayList<Leave>();
		String sql = "select * from leaves ";
		if(leave.getStudentId() != 0){
			sql += " and student_id = " + leave.getStudentId() + "";
		}
		sql += " limit " + page.getStart() + "," + page.getSize();
		ResultSet rs = query(sql.replaceFirst("and", "where"));
		ResultSetMetaData meta = null;
		try {
			meta = rs.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while(rs.next()){
				//创建对象
				Leave exam = new Leave();
				//遍历每个字段
				for(int i=1;i <= meta.getColumnCount();i++){
					String field = meta.getColumnName(i);
					Object value = rs.getObject(field);
					BeanUtils.setProperty(exam, field, rs.getObject(field));
					
				}
				exam.setStudentId(Integer.parseInt(rs.getObject("student_id").toString()));
				ret.add(exam);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		
		return ret;
	}
	
	private ResultSet query(String sql) {
		// TODO Auto-generated method stub
		//获取数据库连接
		Connection conn = MysqlTool.getConnection();
		//预编译
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			ResultSet rs = ps.executeQuery();
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取总记录数
	 * @param leave
	 * @return
	 */
	public int getLeaveListTotal(Leave leave){
		int total = 0;
		String sql = "select count(*)as total from leaves ";
		if(leave.getStudentId() != 0){
			sql += " and student_id = " + leave.getStudentId() + "";
		}
		ResultSet resultSet = query(sql.replaceFirst("and", "where"));
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return total;
	}

	public void closeCon() {
		// TODO Auto-generated method stub
		
	}



}
