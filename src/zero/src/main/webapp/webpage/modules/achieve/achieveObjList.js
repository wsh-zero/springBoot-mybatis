<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#achieveObjTable').bootstrapTable({

		  //请求方法
               method: 'post',
               //类型json
               dataType: "json",
               contentType: "application/x-www-form-urlencoded",
               //显示检索按钮
	           showSearch: false,
               //显示刷新按钮
               showRefresh: false,
               //显示切换手机试图按钮
               showToggle: false,
               //显示 内容列下拉框
    	       showColumns: false,
    	       //显示到处按钮
    	       showExport: false,
    	       //显示切换分页按钮
    	       showPaginationSwitch: false,
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
               url: "${ctx}/achieve/achieveObj/data",
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
               contextMenu: '#obj-context-menu',
               onContextMenuItem: function(row, $el){
                   if($el.data("item") == "obj_edit"){
                   		obj_edit(row.id);
                   }else if($el.data("item") == "obj_delete"){
                        jp.confirm('确认要删除该绩效对象配置记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/achieve/achieveObj/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#achieveObjTable').bootstrapTable('refresh');
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

		    },{
                   title:'序号',
                   align:'center',
                   formatter:function(value, row , index){
                       return value = index +1;
                   }
               }
			,{
		        field: 'rankName',
		        title: '岗位职称'

		    }
			,{
		        field: 'name',
		        title: '分组名称'

		    }
			,{
		        field: 'dept',
		        title: '部门名称',
                formatter:function(value, row , index){
		            var deptName="";
		            for (var item in value){
		                deptName+=value[item].value + ","
                    }
                   return deptName;
                }

		    }
		     ]

		});


	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端


		  $('#achieveObjTable').bootstrapTable("toggleView");
		}

	  $('#achieveObjTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#obj_remove').prop('disabled', ! $('#achieveObjTable').bootstrapTable('getSelections').length);
            $('#obj_edit').prop('disabled', $('#achieveObjTable').bootstrapTable('getSelections').length!=1);
        });

	});

    //刷新列表
    function obj_refresh(){
    $('#achieveObjTable').bootstrapTable('refresh');
}
  function obj_getIdSelections() {
        return $.map($("#achieveObjTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

  function obj_deleteAll(){

		jp.confirm('确认要删除该绩效对象配置记录吗？', function(){
			jp.loading();
			jp.get("${ctx}/achieve/achieveObj/deleteAll?ids=" + obj_getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#achieveObjTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})

		})
  }

   function obj_add(){
	  jp.openSaveDialog('新增绩效对象配置', "${ctx}/achieve/achieveObj/form",'800px', '500px',$('#achieveObjTable'));
  }



   function obj_edit(id){//没有权限时，不显示确定按钮
       if(id == undefined){
	      id = obj_getIdSelections();
	}
	jp.openSaveDialog('编辑绩效对象配置', "${ctx}/achieve/achieveObj/form?id=" + id, '800px', '500px',$('#achieveObjTable'));
  }

 {/*function obj_view(id){//没有权限时，不显示确定按钮*/}
      {/*if(id == undefined){*/}
             {/*id = obj_getIdSelections();*/}
      {/*}*/}
        {/*jp.openViewDialog('查看绩效对象配置', "${ctx}/achieve/achieveObj/form?id=" + id, '800px', '500px');*/}
 {/*}*/}



</script>