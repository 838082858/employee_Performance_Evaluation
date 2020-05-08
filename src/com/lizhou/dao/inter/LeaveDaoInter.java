package com.lizhou.dao.inter;

import java.util.List;

import com.lizhou.bean.Clazz;
import com.lizhou.bean.Grade;
import com.lizhou.bean.Leave;
import com.lizhou.bean.Student;
import com.lizhou.bean.Teacher;

public interface LeaveDaoInter extends BaseDaoInter{
	/**
	 * 获取领导信息，这里需要将领导所选择的请假信息查询出来
	 * @param sql
	 * @param param
	 * @return
	 */
	public List<Leave> getLeaveList(String sql, List<Object> param);
}
