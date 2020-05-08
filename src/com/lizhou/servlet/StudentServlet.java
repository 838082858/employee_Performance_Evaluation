package com.lizhou.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;

import com.lizhou.bean.Course;
import com.lizhou.bean.Page;
import com.lizhou.bean.Student;
import com.lizhou.bean.User;
import com.lizhou.dao.impl.StudentDaoImpl;
import com.lizhou.exception.FileFormatException;
import com.lizhou.exception.NullFileException;
import com.lizhou.exception.ProtocolException;
import com.lizhou.exception.SizeException;
import com.lizhou.service.StudentService;
import com.lizhou.tools.StringTool;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 员工类Servlet
 * @author wuxiaoting
 *
 */
public class StudentServlet extends HttpServlet {
	
	//创建服务层对象
	private StudentService service = new StudentService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取请求的方法
		String method = request.getParameter("method");
		if("toStudentListView".equalsIgnoreCase(method)){ //转发到员工列表页
			request.getRequestDispatcher("/WEB-INF/view/student/studentList.jsp").forward(request, response);
		} else if("toStudentNoteListView".equalsIgnoreCase(method)){ //转发到员工列表页
			request.getRequestDispatcher("/WEB-INF/view/student/studentNoteList.jsp").forward(request, response);
		} else if("toExamStudentView".equalsIgnoreCase(method)){ //转发到员工的考核列表页
			request.getRequestDispatcher("/WEB-INF/view/student/examStudentList.jsp").forward(request, response);
		} else if("toStudentPersonalView".equalsIgnoreCase(method)){ //转发到员工个人列表页
			toPersonal(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取请求的方法
		String method = request.getParameter("method");
		//请求分发
		if("StudentList".equalsIgnoreCase(method)){ //获取所有员工数据
			studentList(request, response);
		} else if("AddStudent".equalsIgnoreCase(method)){ //添加员工
			addStudent(request, response);
		} else if("DeleteStudent".equalsIgnoreCase(method)){ //删除员工
			deleteStudent(request, response);
		} else if("EditStudent".equalsIgnoreCase(method)){ //修改员工信息
			editStudent(request, response);
		} else if("StudentClazzList".equalsIgnoreCase(method)){ //获取当前员工工作小组的所有员工
			studentClazzList(request, response);
		}else if("StudentTabs".equals(method)){
			studentTabsList(request, response);
		}
		
	}

	private void studentTabsList(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		StudentDaoImpl studentDaoImpl = new StudentDaoImpl();
		List<Student> students = studentDaoImpl.getStudentList("select * from student",null);
		List<Course> courseList = new LinkedList<>();
		for(Student student :students){
			Course course = new Course();
			course.setId(student.getId());
			course.setName(student.getName());
			courseList.add(course);
		}
		//json化
        String result = JSONArray.fromObject(courseList).toString();
        try {
			response.getWriter().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 转到个人信息页，加载个人信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void toPersonal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		Student student = service.getStudent(user.getAccount());
		request.getSession().setAttribute("userDetail", student);
		request.getRequestDispatcher("/WEB-INF/view/student/studentPersonal.jsp").forward(request, response);
	}
	
	private void studentClazzList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		User user = (User) request.getSession().getAttribute("user");
		//获取分页参数
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		
		//获取数据
		String result = service.getStudentList(user.getAccount(), new Page(page, rows));
		//返回数据
        response.getWriter().write(result);
	}

	private void editStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//获取参数名
		Enumeration<String> pNames = request.getParameterNames();
		Student student = new Student();
		while(pNames.hasMoreElements()){
			String pName = pNames.nextElement();
			String value = request.getParameter(pName);
			try {
				BeanUtils.setProperty(student, pName, value);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		service.editStudent(student);
		response.getWriter().write("success");
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//获取要删除的工号
		String[] numbers = request.getParameterValues("numbers[]");
		String[] ids = request.getParameterValues("ids[]");
		try {
			service.deleteStudent(ids, numbers);
			response.getWriter().write("success");
		} catch (Exception e) {
			response.getWriter().write("fail");
			e.printStackTrace();
		}
	}

	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//获取参数名
		Enumeration<String> pNames = request.getParameterNames();
		Student student = new Student();
		while(pNames.hasMoreElements()){
			String pName = pNames.nextElement();
			String value = request.getParameter(pName);
			try {
				BeanUtils.setProperty(student, pName, value);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		service.addStudent(student);
		response.getWriter().write("success");
	}

	private void studentList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//获取分页参数
		Object object = request.getParameter("page");
		int page = 1;
//		if(object != null || StringUtils.isNotBlank(object.toString())){
//			page =Integer.parseInt(object.toString());
//		}
		int rows = 100;
		object = request.getParameter("rows");
//		if(object != null || StringUtils.isNotBlank(object.toString())){
//			page =Integer.parseInt(object.toString());
//		}
	
		//部门ID
		String gradeid = request.getParameter("gradeid");
		//工作小组ID
		String clazzid = request.getParameter("clazzid");
		
		//封装参数
		Student student = new Student();
		
		if(!StringTool.isEmpty(gradeid)){
			student.setGradeid(Integer.parseInt(gradeid));
		}
		if(!StringTool.isEmpty(clazzid)){
			student.setClazzid(Integer.parseInt(clazzid));
		}
		
		//获取数据
		String result = service.getStudentList(student, new Page(page, rows));
		//返回数据
        response.getWriter().write(result);
	}
	
}
