$(function(){
	var columns = [[
	                {field:'view',title:'操作',width:260,align:'center',formatter:operateData},
					{field:'adminId',title:'用户名',width:150,align:'center',sortable:true},
					{field:'realName',title:'真实姓名',width:150,align:'center',sortable:true},
					{field:'deptText',title:'部门名称',width:150,align:'center',sortable:true},
					{field:'employeeNo',title:'员工号',width:150,align:'center',sortable:true},
					{field:'mobile',title:'手机号',align:'center',sortable:true},
					{field:'email',title:'邮箱',align:'center',sortable:true},
					{field:'createTime',title:'注册时间',align:'center',sortable:true,formatter:function(value){
	                	var date = new Date(value);
	                	var pattern = "yyyy-MM-dd hh:mm:ss"; 
	                	return date.format(pattern);
	                }},
					{field:'statusName',title:'状态',width:50,align:'center',sortable:true},
				]];
	
	var addBtn = { 
	     	text: '添加', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	showAddAdmin();
	        } 
	      };
	var operateBtns=[];
	if ($('#user_admin_add').length>0) {
		operateBtns.push(addBtn);
	}
	if (operateBtns.length==0) {
		operateBtns=null;
	}
	
	$('#adminTable').datagrid({
		url: path + "/admin/getlist",
		columns: columns,
		rownumbers : true,
		singleSelect: true,
		sortName : 'createTime',
		sortOrder : 'desc',
		height:bodyHeight-263,
		pagination: true,
		toolbar:operateBtns
	});
	
	
	var roleColumns = [[
	                {field:'ck',checkbox:true },
					{field:'code',title:'角色编码',width:130,align:'center',sortable:true},
					{field:'name',title:'角色名称',width:130,align:'center',sortable:true},
					{field:'description',title:'描述',width:200,align:'center'},
					{field:'operat',title:'操作',width:100,align:'center'}
				]];
	$('#roleTable').datagrid({
		columns: roleColumns,
		striped : true,
		pagination: true,
		rownumbers:true,
		onLoadSuccess:function(data){
			var tableData = data;
			$.ajax({
	    		type : "POST",
	    		dataType : 'json',
	    		url : path + '/authority/user/getRole',
	    		data : {
	    			'userId' : $('#roleList input[name="userId"]').val()
	    		},
	    		success : function(data) {
	    			if(tableData){
	    				$.each(tableData.rows, function(index, item){
	    					var rowItem = item;
	    					var rowIndex = index;
	    					$.each(data,function(index,item){
	    						if (rowItem.id == item.id) {
	    							$('#roleTable').datagrid('checkRow', rowIndex);
								}
	    					});
	    				});
	    			}
	    		}
	    	});
		} 
	});
	
	//查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("adminTable", "list_search");
    });
    
    $('#resetBtn').click(function(){
    	$('#list_search').form('clear');
    });
    
    //分配岗位
    $('#saveRoleBtn').click(function(){
    	var checkedItems = $('#roleTable').datagrid('getChecked');
    	var names = [];
    	$.each(checkedItems, function(index, item){
    		names.push(item.id);
    	});
    	$.ajax({
    		type : "POST",
    		url : path + '/authority/user/addRole',
    		data : {
    			'userId' : $('#roleList input[name="userId"]').val(),
    			'roleId' :names.join(",")
    		},
    		success : function() {
    			$("#roleList").show().dialog('close');
    		}
    	});
    	
    });
    
    //添加窗口的关闭
    $('#closeBtn').click(function(){
    	$("#add").dialog('close');
    });
    
    
    $("#adminForm").validate({
    	errorPlacement : function(error, element) {
    		if (element.parent().hasClass("combo")){
    			error.appendTo(element.parent());
    		}else{
    			error.appendTo(element.parent());
    		}
    	},
		rules : {
			adminId : "required",
			password : {
				required:true,
				securityPass:true
			},
			confirmPassword : {
				required:true,
				equalTo:'#password'
			},
			realName : "required",
			idCardNo : {
				required:true,
				idcard:true
			},
			employeeNo : "required",
			dept : {
				required:true
			},
			email : {
				required:true,
				email:true
			},
			mobile : {
				required:true,
				mobile:true
			}
		},
		messages : {
			adminId : "请填写用户名",
			password : {
				required:"请填写密码",
				securityPass:"密码至少6位，且由大小写字母、数字和符号两种及以上组合"
			},
			confirmPassword : {
				required:"请输入确认密码",
				equalTo:"两次输入密码不一致"
			},
			realName : "请填写真是姓名",
			idCardNo : {
				required:"请填写身份证号"
			},
			employeeNo : "请填写员工号",
			dept : {
				required:"请填写部门"
			},
			email : {
				required:"请填写邮箱"
			},
			mobile : {
				required:"请填写手机号码"
			}
		},
        submitHandler:function(form){
        	var url=path + '/admin/save';
        	var params = {};
    		if ($('#adminForm input[name="id"]').val()!='') {
    			url=path + '/admin/modify';
    			params.adminId = $("#adminForm input[name='adminId']").val();
    		}
    		$('#adminForm').form('submit', {
    			url : url,
    			queryParams: params,
    			success : function(data) {
    				var obj = $.parseJSON(data);
    				if (obj.success) {
    					$("#add").show().dialog('close');
    					queryToGrid("adminTable", "list_search");
    				}
    				if (!obj.success) {
    					if (obj.msg) {
    						$.messager.alert('提示', obj.msg);
    					}
    				}
    			}
    		});
        }
    }); 
    
	$('#saveBtn').click(function() {
		$('#adminForm').submit();
	});
    
    //关闭权岗位分配窗口
    $('#closeRoleBtn').click(function(){
    	$("#roleList").show().dialog('close');
    });
});

function modifySecurity(userId,status){
	var msg ='';
	if (status=='disable') {
		msg='您确定要禁用&nbsp;'+userId+'&nbsp;吗?';
	}else{
		msg='您确定要启用&nbsp;'+userId+'&nbsp;吗?';
	}
	$.messager.confirm('提示', msg, function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/user/modifySecurity',
				data : {
					'userId' : userId,
					'userType' :'A',
					'status' : status
				},
				success : function() {
					queryToGrid("adminTable", "list_search");
				}
			});
		}
	});
}

function showAddAdmin() {
	$("#adminForm input[name='adminId']").removeAttr('disabled');
	$("#add").show().dialog({
		title: "添加管理员",
		height: 450,
		width: 550,
		modal : true,
//		buttons : [{  
//            text : '保存',  
//            handler : function(){
//            	
//            }  
//        }, {  
//            text : '取消',  
//            handler : function(){
//            	
//            }    
//        }],
		onClose: function () {
           $('#adminForm').form('clear').form('reset');
        }
	});
}

//显示修改字典窗口
function editWin(id) {
	$('#password').parent().parent().remove();
	$('#confirmPassword').parent().parent().remove();
	$("#adminForm input[name='adminId']").attr('disabled','disabled');
	$('#adminForm').form('load', path + '/admin/getById?id=' + id);
	$("#add").show().dialog({
		title: "修改",
		height: 400,
		width: 550,
		modal : true,
		onClose: function () {
			$('#adminForm').form('clear').form('reset');
        }
	});
}

function deleteAdmin(id,adminId){
	$.messager.confirm('确认','将删除该管理员及相关所有数据，<br/>是否确定？',function(r){
	    if (r){
	    	$.ajax({
				type : "POST",
				url : path + '/admin/deleteAdmin',
				data : {'id' : id,'adminId':adminId},
				success : function(data) {
					if(data.success) {
						queryToGrid("adminTable", "list_search");
						$.messager.show({
							title:'提示',
							msg:'管理员已被删除!',
							timeout:2000,
							showType:'slide'
						});
					}
				}
			});
	    	
	    }
	});
}

//重置密码
function resetPassword(id){
	// 删除
	$.messager.confirm('提示', '初始化密码为本人身份证号码的后6位，<br/>您确定要重置吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				dataType : 'json',
				url : path + '/admin/initPassword',
				data : {
					'id' : id
				},
				success : function(data) {
					if (data.success) {
						$.messager.alert('提示', '重置成功！');
					}else{
						$.messager.alert('提示', '重置失败！');
					}
				}
			});
		}
	});
}


function showRoleList(userId) {
	$('#roleTable').datagrid('options').url=path + "/authority/role/getList";
	$('#roleTable').datagrid('reload');
	
	$('#roleList input[name="userId"]').val(userId);
	
	$("#roleList").show().dialog({
		title: "岗位分配",
		height: 500,
		width: 650,
		modal : true,
		onClose: function () {
//            $("#dicForm").form('clear');
        }
	});
}

//用户详情
function showAdminDetail(id) {
	$.ajax({
		type : "POST",
		url : path + '/admin/getAdminDetail',
//		url : path + '/third/queryAcct/queryBalance',
		data : {
			'id' : id
		},
		success : function(data) {
			$("#adminDetail label").each(function(){
				$(this).text(data[this.id]);
			});
		}
	});
	
	$("#adminDetail").show().dialog({
		title: "用户详情",
		height: 350,
		width: 680,
		modal : true,
		onClose: function () {
			$("#adminDetail label").text('');
        }
	});
}
