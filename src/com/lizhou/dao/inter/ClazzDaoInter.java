package com.lizhou.dao.inter;

import java.util.List;

import com.lizhou.bean.Clazz;
import com.lizhou.bean.Page;

/**
 * 班级数据层接口
 * @author wuxiaoting
 *
 */
public interface ClazzDaoInter extends BaseDaoInter {
	
	/**
	 * 获取小组详细信息
	 * @param gradeid 部门ID
	 * @param page 分页参数
	 * @return
	 */
	public List<Clazz> getClazzDetailList(String gradeid, Page page);
	
}
