<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#resumemanagerTable').bootstrapTable({
		 
		  //请求方法
               method: 'post',
               //类型json
               dataType: "json",
               contentType: "application/x-www-form-urlencoded",
               //显示检索按钮
	           showSearch: true,
               //显示刷新按钮
               showRefresh: true,
               //显示切换手机试图按钮
               showToggle: true,
               //显示 内容列下拉框
    	       showColumns: true,
    	       //显示到处按钮
    	       showExport: true,
    	       //显示切换分页按钮
    	       showPaginationSwitch: true,
    	       //最低显示2行
    	       minimumCountColumns: 2,
               //是否显示行间隔色
               striped: true,
               //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）     
               cache: false,    
               //是否显示分页（*）  
               pagination: true,   
                //排序方式 
               sortOrder: "asc",  
               //初始化加载第一页，默认第一页
               pageNumber:1,   
               //每页的记录行数（*）   
               pageSize: 10,  
               //可供选择的每页的行数（*）    
               pageList: [10, 25, 50, 100],
               //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
               url: "${ctx}/resume/resumemanager/data",
               //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
               //queryParamsType:'',   
               ////查询参数,每次调用是会带上这个参数，可自定义                         
               queryParams : function(params) {
               	var searchParam = $("#searchForm").serializeJSON();
               	searchParam.pageNo = params.limit === undefined? "1" :params.offset/params.limit+1;
               	searchParam.pageSize = params.limit === undefined? -1 : params.limit;
               	searchParam.orderBy = params.sort === undefined? "" : params.sort+ " "+  params.order;
                   return searchParam;
               },
               //分页方式：client客户端分页，server服务端分页（*）
               sidePagination: "server",
               contextMenuTrigger:"right",//pc端 按右键弹出菜单
               contextMenuTriggerMobile:"press",//手机端 弹出菜单，click：单击， press：长按。
               contextMenu: '#context-menu',
               onContextMenuItem: function(row, $el){
                   if($el.data("item") == "edit"){
                   		edit(row.id);
                   }else if($el.data("item") == "view"){
                       view(row.id);
                   } else if($el.data("item") == "delete"){
                        jp.confirm('确认要删除该简历管理记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/resume/resumemanager/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#resumemanagerTable').bootstrapTable('refresh');
                   	  			jp.success(data.msg);
                   	  		}else{
                   	  			jp.error(data.msg);
                   	  		}
                   	  	})
                   	   
                   	});
                      
                   } 
               },
              
               onClickRow: function(row, $el){
               },
               	onShowSearch: function () {
			$("#search-collapse").slideToggle();
		},
               columns: [{
		        checkbox: true
		       
		    }
			,{
		        field: 'resumeno',
		        title: '简历编号',
		        sortable: true,
		        sortName: 'resumeno'
		       
		    }
			,{
		        field: 'name',
		        title: '求职者姓名',
		        sortable: true,
		        sortName: 'name'
		       
		    }
			,{
		        field: 'sex',
		        title: '性别',
		        sortable: true,
		        sortName: 'sex'
		       
		    }
			,{
		        field: 'telphone',
		        title: '手机号码',
		        sortable: true,
		        sortName: 'telphone'
		       
		    }
			,{
		        field: 'deptno',
		        title: '应聘部门',
		        sortable: true,
		        sortName: 'deptno',
		    }
			,{
		        field: 'interviewer',
		        title: '面试官',
		        sortable: true,
		        sortName: 'interviewer',
		    }
			,{
		        field: 'auditiontime',
		        title: '面试时间',
		        sortable: true,
		        sortName: 'auditiontime'
		       
		    }
			,{
		        field: 'record',
		        title: '面试记录',
		        sortable: true,
		        sortName: 'record'
		       
		    }
			,{
		        field: 'result',
		        title: '面试结果',
		        sortable: true,
		        sortName: 'result'
		       
		    }
			,{
		        field: 'status',
		        title: '简历状态',
		        sortable: true,
		        sortName: 'status'
				/*,formatter:function(value, row , index){
				value = jp.unescapeHTML(value);
				if(value=='0'){
					return "待筛选";
				}else if(value=='1'){
					return "待面试";
				}else if(value=='2'){
					return "不合适";
				}
				}*/
		       
		    },{
					   field: 'remarks',
					   title: '备注信息',
					   sortable: true,
					   sortName: 'remarks'
					   ,formatter:function(value, row , index){
						   value = jp.unescapeHTML(value);
					   <c:choose>
						   <c:when test="${fns:hasPermission('resume:resumemanager:edit')}">
						   return "<a href='javascript:edit(\""+row.id+"\")'>"+value+"</a>";
					   </c:when>
						   <c:when test="${fns:hasPermission('resume:resumemanager:view')}">
						   return "<a href='javascript:view(\""+row.id+"\")'>"+value+"</a>";
					   </c:when>
						   <c:otherwise>
						   return value;
					   </c:otherwise>
						   </c:choose>
					   }

				   },
				   {
					   field: 'status',
					   title: '操作',
					   sortable: true,
					   sortName: 'status'
					   ,formatter:function(value, row , index){
						   value = jp.unescapeHTML(value);
						   if(value=='待筛选'){
							   return "<a href='javascript:editresume(\"邀约面试\",\""+row.id+"\",\"invite\")'>邀约</a>&nbsp;&nbsp;<a href='javascript:editstatus(\""+row.id+"\",\"简历不合适\")'>淘汰</a>";
						   }else if(value=='待面试'){
							   return "<a href='javascript:editresume(\"更改邀约\",\""+row.id+"\",\"change\")'>更改邀约</a>" +
								   "&nbsp;&nbsp;<a href='javascript:editresume(\"面试完成\",\""+row.id+"\",\"complete\")'>面试完成</a>"+
								   "&nbsp;&nbsp;<a href='javascript:editstatus(\""+row.id+"\",\"不合适\")'>取消邀约</a>";
						   }else if(value=='不合适' || value=='未入职'){
							   return "<a href='javascript:editstatus(\""+row.id+"\",\"人才库\")'>存入人才库</a>";
						   }else if(value=='待入职'){
						   <c:choose>
							   <c:when test="${fns:hasPermission('personnel:manager:staff:add')}">
							   return "<a href='javascript:staffForm(\"办理入职\")'>办理入职</a>" +
								   "&nbsp;&nbsp;<a href='javascript:editstatus(\""+row.id+"\",\"未入职\")'>放弃入职</a>";
						   </c:when>
							   </c:choose>
						   }


						  /* if(value==0){
							   return "<a href='javascript:edit(\""+row.id+"\")'>"邀约"</a><a href='javascript:edit(\""+row.id+"\")'>"淘汰"</a>";
						   }*/
					  /* <c:choose>
						   <c:when test="${value==0}">
						   return "<a href='javascript:edit(\""+row.id+"\")'>邀约</a><a href='javascript:edit(\""+row.id+"\")'>淘汰</a>";
					   </c:when>
						<c:when test="${value==1}">
						   return return "<a href='javascript:edit(\""+row.id+"\")'>邀约</a><a href='javascript:edit(\""+row.id+"\")'>淘汰</a>";
					   </c:when>
						   </c:choose>*/
					   }

				   }
		     ]
		
		});

		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#resumemanagerTable').bootstrapTable("toggleView");
		}
	  
	  $('#resumemanagerTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#resumemanagerTable').bootstrapTable('getSelections').length);
            $('#view,#edit').prop('disabled', $('#resumemanagerTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jp.open({
			    type: 2,
                area: [500, 200],
                auto: true,
			    title:"导入数据",
			    content: "${ctx}/tag/importExcel" ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					 jp.downloadFile('${ctx}/resume/resumemanager/import/template');
				  },
			    btn2: function(index, layero){
				        var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
						iframeWin.contentWindow.importExcel('${ctx}/resume/resumemanager/import', function (data) {
							if(data.success){
								jp.success(data.msg);
								refresh();
							}else{
								jp.error(data.msg);
							}
						   jp.close(index);
						});//调用保存事件
						return false;
				  },
				 
				  btn3: function(index){ 
					  jp.close(index);
	    	       }
			}); 
		});
		    
		
	 $("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#resumemanagerTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#resumemanagerTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/resume/resumemanager/export?'+values);
	  })

		    
	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#resumemanagerTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#resumemanagerTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#resumemanagerTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该简历管理记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/resume/resumemanager/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#resumemanagerTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function refresh(){
  	$('#resumemanagerTable').bootstrapTable('refresh');
  }
  function add(){
		jp.go("${ctx}/resume/resumemanager/form/add");
	}

  function edit(id){
	  if(id == undefined){
		  id = getIdSelections();
	  }
	  jp.go("${ctx}/resume/resumemanager/form/edit?id=" + id);
  }

  function view(id) {
      if(id == undefined){
          id = getIdSelections();
      }
      jp.go("${ctx}/resume/resumemanager/form/view?id=" + id);
  }

function editstatus(id,status) {
	if(id == undefined){
		id = getIdSelections();
	}
	jp.go("${ctx}/resume/resumemanager/editstatus?id=" + id+"&status="+status);
}

//面试完成
function editresume(title,id,flag) {
	if(id == undefined){
		id = getIdSelections();
	}
	jp.openSaveDialog(title, "${ctx}/resume/resumemanager/form/?id=" + id+"&flag="+flag, '800px', '770px');
}

//邀约面试
function staffForm(title) {

	jp.openSaveDialog(title, "${ctx}/personnel/manager/staff/form/add1", '800px', '770px');
}
  
</script>