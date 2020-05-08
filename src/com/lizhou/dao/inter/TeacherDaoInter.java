package com.lizhou.dao.inter;

import java.util.List;

import com.lizhou.bean.Clazz;
import com.lizhou.bean.Grade;
import com.lizhou.bean.Teacher;

/**
 * 操作领导的数据层接口
 * @author wuxiaoting
 *
 */
public interface TeacherDaoInter extends BaseDaoInter {
	
	/**
	 * 获取领导信息，这里需要将领导所选择的考核项目查询出来
	 * @param sql
	 * @param param
	 * @param grade 部门参数
	 * @param clazz 小组参数
	 * @return
	 */
	public List<Teacher> getTeacherList(String sql, Object[] param, Grade grade, Clazz clazz);
	
}
