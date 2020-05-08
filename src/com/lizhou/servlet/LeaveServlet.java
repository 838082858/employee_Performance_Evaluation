package com.lizhou.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;








import com.lizhou.bean.Course;
import com.lizhou.bean.Leave;
import com.lizhou.bean.Page;
import com.lizhou.bean.Student;
import com.lizhou.bean.User;
import com.lizhou.dao.impl.LeaveDaoImpl;
import com.lizhou.dao.impl.StudentDaoImpl;
import com.lizhou.service.ExamService;
import com.lizhou.tools.StringTool;


public class LeaveServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		//获取请求的方法
		String method = request.getParameter("method");
		if("toLeaveListView".equalsIgnoreCase(method)){ //转发到请假列表页
			request.getRequestDispatcher("/WEB-INF/view/other/leaveList.jsp").forward(request, response);
		} else if("toLeaveStudentListView".equalsIgnoreCase(method)){ //转发到员工的请假列表页
			request.getRequestDispatcher("/WEB-INF/view/student/leaveStudentList.jsp").forward(request, response);
		} else if("toLeaveTeacherListView".equalsIgnoreCase(method)){ //转发到领导的请假列表页
			request.getRequestDispatcher("/WEB-INF/view/teacher/leaveTeacherList.jsp").forward(request, response);
		} 
	}
//	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		String method = request.getParameter("method");
//		if("toLeaveListView".equals(method)){
//			try {
//				request.getRequestDispatcher("/WEB-INF/view/other/leaveList.jsp").forward(request, response);
//			} catch (ServletException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}else if("AddLeave".equals(method)){
//			addLeave(request,response);
//		}else if("LeaveList".equals(method)){
//			getLeaveList(request,response);
//		}else if("EditLeave".equals(method)){
//			editLeave(request,response);
//		}else if("CheckLeave".equals(method)){
//			checkLeave(request,response);
//		}
//		else if("DeleteLeave".equals(method)){
//			deleteLeave(request,response);
//		}
//	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取请求的方法
		String method = request.getParameter("method");
		//请求分发
		if("LeaveList".equalsIgnoreCase(method)){ //获取所有员工数据
			getLeaveList(request, response);
		}
			else if("AddLeave".equals(method)){
				addLeave(request,response);
			}else if("LeaveList".equals(method)){
				getLeaveList(request,response);
			}else if("EditLeave".equals(method)){
				editLeave(request,response);
			}else if("CheckLeave".equals(method)){
				checkLeave(request,response);
			}
			else if("DeleteLeave".equals(method)){
				deleteLeave(request,response);
			}
	}
	
	
	private void deleteLeave(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("id"));
		LeaveDaoImpl leaveDao = new LeaveDaoImpl();
		String msg = "success";
		if(!leaveDao.deleteLeave(id)){
			msg = "error";
		}
		try {
			response.getWriter().write(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void checkLeave(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		int studentId = Integer.parseInt(request.getParameter("studentid"));
		int id = Integer.parseInt(request.getParameter("id"));
		int status = Integer.parseInt(request.getParameter("status"));
		String info = request.getParameter("info");
		String remark = request.getParameter("remark");
		Leave leave = new Leave();
		leave.setStudentId(studentId);
		leave.setInfo(info);
		leave.setRemark(remark);
		leave.setStatus(status);
		leave.setId(id);
		LeaveDaoImpl leaveDao = new LeaveDaoImpl();
		String msg = "error";
		if(leaveDao.editLeave(leave)){
			msg = "success";
		}
		try {
			response.getWriter().write(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			leaveDao.closeCon();
		}
	}
	private void editLeave(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		int studentId = Integer.parseInt(request.getParameter("studentid"));
		int id = Integer.parseInt(request.getParameter("id"));
		String info = request.getParameter("info");
		Leave leave = new Leave();
		leave.setStudentId(studentId);
		leave.setInfo(info);
		leave.setRemark("");
		leave.setStatus(Leave.LEAVE_STATUS_WAIT);
		leave.setId(id);
		LeaveDaoImpl leaveDao = new LeaveDaoImpl();
		String msg = "error";
		if(leaveDao.editLeave(leave)){
			msg = "success";
		}
		try {
			response.getWriter().write(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			leaveDao.closeCon();
		}
	}
	private void getLeaveList(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		int studentId = request.getParameter("studentid") == null ? 0 : Integer.parseInt(request.getParameter("studentid").toString());
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		Leave leave = new Leave();
		//获取当前登录用户类型
		User user = (User) request.getSession().getAttribute("user");
		int userType = user.getType();
		if(userType == 2){
			//如果是学生，只能查看自己的信息
//			Student currentUser = (Student)request.getSession().getAttribute("user");
			studentId = user.getId();
		}
		leave.setStudentId(studentId);
		LeaveDaoImpl leaveDao = new LeaveDaoImpl();
		List<Leave> leaveList = leaveDao.getLeaveList(leave, new Page(currentPage, pageSize));
		StudentDaoImpl studentDaoImpl = new StudentDaoImpl();
		for(Leave leaves :leaveList){
//			Student student = new Student();
			int stId = leaves.getStudentId();
//			student.setId(stId);
			leaves.setStudentName(studentDaoImpl.getStudentName(stId));
//			leave.setStudent(student);
		}
		int total = leaveDao.getLeaveListTotal(leave);
		leaveDao.closeCon();
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", leaveList);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(leaveList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void addLeave(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		int studentId = Integer.parseInt(request.getParameter("studentid"));
		String info = request.getParameter("info");
		Leave leave = new Leave();
		leave.setStudentId(studentId);
		leave.setInfo(info);
		leave.setRemark("");
		LeaveDaoImpl leaveDao = new LeaveDaoImpl();
		String msg = "error";
		if(leaveDao.addLeave(leave)){
			msg = "success";
		}
		try {
			response.getWriter().write(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			leaveDao.closeCon();
		}
	}
}
