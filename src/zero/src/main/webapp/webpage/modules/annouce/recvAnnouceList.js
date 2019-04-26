<%@ page contentType="text/html;charset=UTF-8" %>
	<script>
	$(document).ready(function() {
		$('#annouceTable').bootstrapTable({

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
			url: "${ctx}/annouce/annouce/recvdata",
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
					jp.confirm('确认要删除该发布公告记录吗？', function(){
						jp.loading();
						jp.get("${ctx}/annouce/annouce/delete?id="+row.id, function(data){
							if(data.success){
								$('#annouceTable').bootstrapTable('refresh');
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
					field: 'number',
					title: '编号',

					sortName: 'number'
				}
				,{
					field: 'title',
					title: '标题',

					sortName: 'title',
					formatter:function(value, row , index){
						return "<a href='${ctx}/annouce/annouce/annouceinfo?id="+row.id+"'>"+value+"</a>";
					}
					/* formatter:function(value, row , index){
                         value = jp.unescapeHTML(value);
                        <c:choose>
                            <c:when test="${fns:hasPermission('annouce:annouce:edit')}">
                                return "<a href='${ctx}/annouce/annouce/form?isSelf=${isSelf}&id="+row.id+"'>"+value+"</a>";
                           </c:when>
                           <c:when test="${fns:hasPermission('annouce:annouce:view')}">
                                return "<a href='${ctx}/annouce/annouce/form?isSelf=${isSelf}&id="+row.id+"'>"+value+"</a>";
                           </c:when>
                           <c:otherwise>
                               return value;
                           </c:otherwise>
                        </c:choose>
                      }*/
				}
				,{
					field: 'publishTime',
					title: '发布时间',
					sortName: 'publishTime'
				}
				,{
					field: 'publishPerson',
					title: '发布人',
					sortName: 'publishPerson'
				}
				,{
					field: 'type',
					title: '状态',
					sortName: 'type',
					formatter:function(value, row , index){
						return jp.getDictLabel(${fns:toJson(fns:getDictList('annoucetype'))}, value, "-");
					}
				}
				,{
					field: 'startTime',
					title: '开始日期',

					sortName: 'startTime'

				}
				,{
					field: 'endTime',
					title: '结束日期',

					sortName: 'endTime'

				}
				,{
					field: 'readState',
					title: '查阅状态',
					formatter:function(value, row , index) {
						if (value == 1) return "<span style='color: forestgreen'>已读</span>";
						else if (value == 0) return "<span style='color: #ff371e'>未读</span>";
					}
				}
			]

		});


		if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端


			$('#annouceTable').bootstrapTable("toggleView");
		}

		$('#annouceTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
			'check-all.bs.table uncheck-all.bs.table', function () {
			$('#remove').prop('disabled', ! $('#annouceTable').bootstrapTable('getSelections').length);
			$('#view,#edit').prop('disabled', $('#annouceTable').bootstrapTable('getSelections').length!=1);
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
					jp.downloadFile('${ctx}/annouce/annouce/import/template');
				},
				btn2: function(index, layero){
					var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
					iframeWin.contentWindow.importExcel('${ctx}/annouce/annouce/import', function (data) {
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
			var sortName = $('#annouceTable').bootstrapTable("getOptions", "none").sortName;
			var sortOrder = $('#annouceTable').bootstrapTable("getOptions", "none").sortOrder;
			var values = "";
			for(var key in searchParam){
				values = values + key + "=" + searchParam[key] + "&";
			}
			if(sortName != undefined && sortOrder != undefined){
				values = values + "orderBy=" + sortName + " "+sortOrder;
			}

			jp.downloadFile('${ctx}/annouce/annouce/export?'+values);
		})


		$("#search").click("click", function() {// 绑定查询按扭
			$('#annouceTable').bootstrapTable('refresh');
		});

		$("#reset").click("click", function() {// 绑定查询按扭
			$("#searchForm  input").val("");
			$("#searchForm  select").val("");
			$("#searchForm  .select-item").html("");
			$('#annouceTable').bootstrapTable('refresh');
		});


	});

function getIdSelections() {
	return $.map($("#annouceTable").bootstrapTable('getSelections'), function (row) {
		return row.id
	});
}

function deleteAll(){

	jp.confirm('确认要删除该发布公告记录吗？', function(){
		jp.loading();
		jp.get("${ctx}/annouce/annouce/deleteAll?ids=" + getIdSelections(), function(data){
			if(data.success){
				$('#annouceTable').bootstrapTable('refresh');
				jp.success(data.msg);
			}else{
				jp.error(data.msg);
			}
		})

	})
}
function refresh(){
	$('#annouceTable').bootstrapTable('refresh');
}
function add(){
	jp.go("${ctx}/annouce/annouce/form");
}

function edit(id){
	if(id == undefined){
		id = getIdSelections();
	}
	jp.go("${ctx}/annouce/annouce/form?id=" + id);
}

function view(id) {
	if(id == undefined){
		id = getIdSelections();
	}
	jp.go("${ctx}/annouce/annouce/form?id=" + id);
}

</script>