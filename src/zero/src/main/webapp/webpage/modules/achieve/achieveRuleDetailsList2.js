<%@ page contentType="text/html;charset=UTF-8" %>
<script>
	    var $achieveRuleDetailsTreeTable=null;
		$(document).ready(function() {
			$achieveRuleDetailsTreeTable=$('#achieveRuleDetailsTreeTable').treeTable({
		    	   theme:'vsStyle',
					expandLevel : 2,
					column:0,
					checkbox: false,
		            url:'${ctx}/achieve/achieveRuleDetails/getChildren2?achieveRuleId='+ '${achieveRuleDetails.achieveRuleId}'+'&parentId=',
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
	function diy_slice_str(str){
		if(str.length>10){
			str=str.slice(0,10)+"...";
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
	<td title="{{d.row.achieveA === undefined ? '': d.row.achieveA}}">
	</td>
	<td title="{{d.row.achieveB === undefined ? '': d.row.achieveB}}">
	</td>
	<td title="{{d.row.achieveC === undefined ? '': d.row.achieveC}}">
	</td>
	<td title="{{d.row.achieveD === undefined ? '': d.row.achieveD}}">
	</td>
 </script>