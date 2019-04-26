<%@ page contentType="text/html;charset=UTF-8" %>
<script>
	    var $achieveRuleDetailsTreeTable=null;
		$(document).ready(function() {
			$achieveRuleDetailsTreeTable=$('#achieveRuleDetailsTreeTable').treeTable({
		    	   theme:'vsStyle',
					expandLevel : 2,
					column:0,
					checkbox: false,
		            url:'${ctx}/achieve/achieveRuleDetails/getChildren?parentId=',
		            callback:function(item) {
		            	 var treeTableTpl= $("#achieveRuleDetailsTreeTableTpl").html();
		            	 item.dict = {};

		            	 var result = laytpl(treeTableTpl).render({
								  row: item
							});
		                return result;
		            },
		            beforeClick: function($achieveRuleDetailsTreeTable, id) {
		                //异步获取数据 这里模拟替换处理
		                $achieveRuleDetailsTreeTable.refreshPoint(id);
		            },
		            beforeExpand : function($achieveRuleDetailsTreeTable, id) {
		            },
		            afterExpand : function($achieveRuleDetailsTreeTable, id) {
		            },
		            beforeClose : function($achieveRuleDetailsTreeTable, id) {

		            }
		        });

		        $achieveRuleDetailsTreeTable.initParents('${parentIds}', "0");//在保存编辑时定位展开当前节点

		});

		function del(con,id){
			jp.confirm('确认要删除绩效考核规则详情吗？', function(){
				jp.loading();
	       	  	$.get("${ctx}/achieve/achieveRuleDetails/delete?id="+id, function(data){
	       	  		if(data.success){
	       	  			$achieveRuleDetailsTreeTable.del(id);
                        refresh()
	       	  			jp.success(data.msg);
	       	  		}else{
	       	  			jp.error(data.msg);
	       	  		}
	       	  	})

       		});

		}

		function refreshNode(data) {//刷新节点
            var current_id = data.body.achieveRuleDetails.id;
			var target = $achieveRuleDetailsTreeTable.get(current_id);
			var old_parent_id = target.attr("pid") == undefined?'1':target.attr("pid");
			var current_parent_id = data.body.achieveRuleDetails.parentId;
			var current_parent_ids = data.body.achieveRuleDetails.parentIds;
			if(old_parent_id == current_parent_id){
				if(current_parent_id == '0'){
					$achieveRuleDetailsTreeTable.refreshPoint(-1);
				}else{
					$achieveRuleDetailsTreeTable.refreshPoint(current_parent_id);
				}
			}else{
				$achieveRuleDetailsTreeTable.del(current_id);//刷新删除旧节点
				$achieveRuleDetailsTreeTable.initParents(current_parent_ids, "0");
			}
        }
		function refresh(){//刷新
			var index = jp.loading("正在加载，请稍等...");
			$achieveRuleDetailsTreeTable.refresh();
			jp.close(index);
		}
	function diy_slice_str(str){
		if(str.length>5){
			str=str.slice(0,5)+"...";
		}
		return str
	}
	function formatter_type(val) {
		var msg
		switch (val) {
			case 1:
			msg = "常规任务";
			break;
			case 2:
			msg = "新增任务";
			break;
			case 3:
			msg = "临时任务";
			break;
			default:
			msg = val
		}
		return msg
	}
</script>
<script type="text/html" id="achieveRuleDetailsTreeTableTpl">
	<td>
		{{# if(d.row.type){ }}
		{{formatter_type(d.row.type)}}
		{{# } }}
	</td>
	<td title="{{d.row.taskContent === undefined ? '': d.row.taskContent}}">
		{{# if(d.row.taskContent){ }}
		{{diy_slice_str(d.row.taskContent)}}
		{{# } }}
	</td>
	<td title="{{d.row.planSuccAmount === undefined ? '': d.row.planSuccAmount}}">
		{{# if(d.row.planSuccAmount){ }}
		{{diy_slice_str(d.row.planSuccAmount)}}
		{{# } }}
	</td>
	<td>
		 {{d.row.planSuccTime === undefined ? "": d.row.planSuccTime}}
	</td>
	<td>
		 {{d.row.score === undefined ? "": d.row.score}}
	</td>
	<td>
		<div class="btn-group">
			<button type="button" class="btn  btn-primary btn-xs dropdown-toggle" data-toggle="dropdown">
				<i class="fa fa-cog"></i>
				<span class="fa fa-chevron-down"></span>
			</button>
		  <ul class="dropdown-menu" role="menu">
			<shiro:hasPermission name="achieve:achieveRuleDetails:edit">
				<li><a href="#" onclick="jp.openSaveDialog('修改绩效考核规则详情', '${ctx}/achieve/achieveRuleDetails/form2?id={{d.row.id}}','800px', '500px',refreshNode)"><i class="fa fa-edit"></i> 修改</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="achieve:achieveRuleDetails:del">
				<li><a  onclick="return del(this, '{{d.row.id}}')"><i class="fa fa-trash"></i> 删除</a></li>
			</shiro:hasPermission>
		  </ul>
		</div>
	</td>
 </script>