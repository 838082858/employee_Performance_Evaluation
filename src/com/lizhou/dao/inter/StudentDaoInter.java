package com.lizhou.dao.inter;

import java.util.List;

import com.lizhou.bean.Page;
import com.lizhou.bean.Student;

/**
 * 操作员工的数据层接口
 * @author wuxiaoting
 *
 */
public interface StudentDaoInter extends BaseDaoInter {
	
	/**
	 * 获取员工信息，这里需要将员工的小组，部门等信息封装进去
	 * @param sql 要执行的sql语句
	 * @param param 参数
	 * @return
	 */
	public List<Student> getStudentList(String sql, List<Object> param);
	
}
