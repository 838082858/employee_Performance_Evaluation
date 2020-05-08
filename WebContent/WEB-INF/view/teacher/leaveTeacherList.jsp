<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>请假列表</title>
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="easyui/css/demo.css">
	<script type="text/javascript" src="easyui/jquery.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/themes/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript">
		//验证只能为数字
$(function() {	
			//datagrid初始化 
		    $('#dataList').datagrid({ 
		        title:'请假列表', 
		        iconCls:'icon-more',//图标 
		        border: true, 
		        collapsible: false,//是否可折叠的 
		        fit: true,//自动大小 
		        method: "post",
		        url:"LeaveServlet?method=LeaveTeacherList&t="+new Date().getTime(),
		        idField:'id', 
		        singleSelect: true,//是否单选 
		        pagination: true,//分页控件 
		        rownumbers: true,//行号 
		        sortName:'id',
		        sortOrder:'DESC', 
		        remoteSort: false,
		        columns: [[  
					{field:'chk',checkbox: true,width:50},
	 		        {field:'id',title:'ID',width:50, sortable: true},    
	 		       	{field:'studentName',title:'员工',width:200,
	 		        	//formatter: function(value,row,index){
	 				//		if (row.studentId){
	 					//		var studentList = $("#studentList").combobox("getData");
	 					//		for(var i=0;i<studentList.length;i++ ){
	 								//console.log(clazzList[i]);
	 					//			if(row.studentId == studentList[i].id)return studentList[i].name;
	 					//		}
	 					//		return row.studentId;
	 					//	} else {
	 						//	return 'not found';
	 					//	}
	 					//}	
	 		       	},
	 		       	{field:'info',title:'请假原因',width:400},
	 		        {field:'status',title:'状态',width:80,
	 		       	formatter: function(value,row,index){
							switch(row.status){
								case 0:{
									return '等待审核';
								}
								case 1:{
									return '审核通过';
								}
								case -1:{
									return '审核不通过';
								}
							}
						}	
	 		        },
	 		        {field:'remark',title:'批复内容',width:400},
		 		]], 
		        toolbar: "#toolbar",
		        onBeforeLoad : function(){
		        	try{
		        		$("#studentList").combobox("getData")
		        	}catch(err){
		        		preLoadClazz();
		        	}
		        }
		    }); 
			//提前加载员工信息
		    function preLoadClazz(){
		  		$("#studentList").combobox({
			  		width: "150",
			  		height: "25",
			  		valueField: "id",
			  		textField: "name",
			  		multiple: false, //可多选
			  		editable: false, //不可编辑
			  		method: "post",
			  		url: "StudentServlet?method=StudentTabs&t="+new Date().getTime()+"&from=combox&page=1&rows=100",
			  		onChange: function(newValue, oldValue){
			  			//加载工作小组下的员工
			  			$('#dataList').datagrid("options").queryParams = {clazzid: newValue};
			  			$('#dataList').datagrid("reload");
			  		}
			  	});
		  	}
			
		  //设置分页控件 
		    var p = $('#dataList').datagrid('getPager'); 
		    $(p).pagination({ 
		        pageSize: 10,//每页显示的记录条数，默认为10 
		        pageList: [10,20,30,50,100],//可以设置每页记录条数的列表 
		        beforePageText: '第',//页数文本框前显示的汉字 
		        afterPageText: '页    共 {pages} 页', 
		        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
		    });
		   	
		    //设置工具类按钮
		    $("#add").click(function(){
		    	$("#addDialog").dialog("open");
		    });
		    
		  //设置审核按钮
		    $("#check").click(function(){
		    	var selectRows = $("#dataList").datagrid("getSelections");
	        	if(selectRows.length != 1){
	            	$.messager.alert("消息提醒", "请选择一条数据进行操作!", "warning");
	            } else{
	            	if(selectRows[0].status != 0){
	            		$.messager.alert("消息提醒", "该请假信息已被审核，请勿重复审核!", "warning");
	            		return;
	            	}
			    	$("#checkDialog").dialog("open");
	            }
		    });
	    
		  
		    //审核请假信息
		  	$("#checkDialog").dialog({
		  		title: "审核请假信息",
		    	width: 450,
		    	height: 350,
		    	iconCls: "icon-edit",
		    	modal: true,
		    	collapsible: false,
		    	minimizable: false,
		    	maximizable: false,
		    	draggable: true,
		    	closed: true,
		    	buttons: [
		    		{
						text:'提交',
						plain: true,
						iconCls:'icon-add',
						handler:function(){
							var validate = $("#checkForm").form("validate");
							if(!validate){
								$.messager.alert("消息提醒","请检查你输入的数据!","warning");
								return;
							} else{
								
								var studentid = $("#dataList").datagrid("getSelected").studentId;
								var id = $("#dataList").datagrid("getSelected").id;
								var info = $("#dataList").datagrid("getSelected").info;
								var remark = $("#check_remark").textbox("getValue");
								var status = $("#check_statusList").combobox("getValue");
								var data = {id:id, studentid:studentid, info:info,remark:remark,status:status};
								
								$.ajax({
									type: "post",
									url: "LeaveServlet?method=CheckLeave",
									data: data,
									success: function(msg){
										if(msg == "success"){
											$.messager.alert("消息提醒","审核成功!","info");
											//关闭窗口
											$("#checkDialog").dialog("close");
											//清空原表格数据
											$("#check_remark").textbox('setValue',"");
											
											//重新刷新页面数据
								  			$('#dataList').datagrid("reload");
								  			$('#dataList').datagrid("uncheckAll");
											
										} else{
											$.messager.alert("消息提醒","审核失败!","warning");
											return;
										}
									}
								});
							}
						}
					},
					{
						text:'重置',
						plain: true,
						iconCls:'icon-reload',
						handler:function(){
							$("#check_remark").val("");
							$("#check_statusList").combox('clear');
						}
					},
				],
				onBeforeOpen: function(){
					/*
					var selectRow = $("#dataList").datagrid("getSelected");
					//设置值
					$("#edit_info").textbox('setValue',selectRow.info);
					//$("#edit-id").val(selectRow.id);
					var studentId = selectRow.studentId;
					setTimeout(function(){
						$("#edit_studentList").combobox('setValue', studentId);
					}, 100);*/
				},
				onClose: function(){
					$("#edit_info").val("");
					//$("#edit-id").val('');
				}
		    });
 	        //下拉框通用属性
	  	    $("#add_studentList, #edit_studentList,#studentList").combobox({
	  		width: "200",
	  		height: "30",
	  		valueField: "id",
	  		textField: "name",
	  		multiple: false, //不可多选
	  		editable: false, //不可编辑
	  		method: "post",
	  	}); 
 	        
	  	  //添加信息员工选择框
	  	  $("#add_studentList").combobox({
	  	    url: "StudentServlet?method=StudentTabs&t="+new Date().getTime()+"&from=combox",
	  	    onLoadSuccess: function(){
	  	      //默认选择第一条数据
	  	      var data = $(this).combobox("getData");
	  	      $(this).combobox("setValue",
	  	                       data[
	  	        0].id);
	  	    }
	  	  });
	  	  //编辑信息员工选择框
	  	  $("#edit_studentList").combobox({
	  	    url: "StudentServlet?method=StudentTabs&t="+new Date().getTime()+"&from=combox",
	  	    onLoadSuccess: function(){
	  	      //默认选择第一条数据
	  	      var data = $(this).combobox("getData");
	  	      $(this).combobox("setValue",
	  	                       data[
	  	        0].id);
	  	    }
	  	  });
		 
		  //搜索按钮监听事件
		  	$("#search-btn").click(function(){
		  		$('#dataList').datagrid('load',{
		  			studentid: $("#studentList").combobox('getValue') == '' ? 0 : $("#studentList").combobox('getValue')
		  		});
		  	});
		    
		    //清空搜索条件
		  	$("#clear-btn").click(function(){
		    	$('#dataList').datagrid("reload",{});
		    	$("#studentList").combobox('clear');
		    });
	   
	  	   
	});
	</script>
</head>
<body>
	<!-- 数据列表 -->
	<table id="dataList" cellspacing="0" cellpadding="0"> 
	    
	</table> 
	
	<!-- 工具栏 -->
	<div id="toolbar">
		<div style="float: left;"><a id="check" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">审核</a></div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="margin-top: 3px;">
			员工：<input id="studentList" class="easyui-textbox" name="studentid" />
			<a id="search-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">搜索</a>
			<a id="clear-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">清空搜索</a>
		</div>
	</div>
		<!-- 审核数据窗口 -->
	<div id="checkDialog" style="padding: 10px">  
    	<form id="editForm" method="post">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td style="width:60px">员工:</td>
	    			<td colspan="3">
	    				<select id="check_statusList" style="width: 300px; height: 30px;" class="easyui-combobox" name="status" data-options="required:true, missingMessage:'请选择状态'" >
	    					<option value="1">审核通过</option>
	    					<option value="-1">审核不通过</option>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>批复内容:</td>
	    			<td>
	    				<textarea id="check_remark" name="remark" style="width: 300px; height: 160px;" class="easyui-textbox" data-options="multiline:true" ></textarea>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
	
	
</body>
</html>