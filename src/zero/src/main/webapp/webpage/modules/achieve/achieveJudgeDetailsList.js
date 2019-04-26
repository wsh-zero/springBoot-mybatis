<%@ page contentType="text/html;charset=UTF-8" %>
<script>
	    var $achieveJudgeDetailsTreeTable=null;
		$(document).ready(function() {
			var achieveJudgeId='${achieveJudge.id}';
			if(!achieveJudgeId){
				achieveJudgeId='${achieveJudge.temporaryId}'
			}
			if(!achieveJudgeId){
				achieveJudgeId='${achieveJudgeDetails.achieveJudgeId}';
			}
			var params="?achieveJudgeId="+achieveJudgeId+"&parentId="
			$achieveJudgeDetailsTreeTable=$('#achieveJudgeDetailsTreeTable').treeTable({
		    	   theme:'vsStyle',
					expandLevel : 2,
					column:0,
					checkbox: false,
		            url:'${ctx}/achieve/achieveJudgeDetails/getChildren'+params,
		            // url:'${ctx}/achieve/achieveJudgeDetails/getChildren?parentId=',
		            callback:function(item) {
		            	 var treeTableTpl= $("#achieveJudgeDetailsTreeTableTpl").html();
		            	 item.dict = {};

		            	 var result = laytpl(treeTableTpl).render({
								  row: item
							});
		                return result;
		            },
		            beforeClick: function($achieveJudgeDetailsTreeTable, id) {
		                //异步获取数据 这里模拟替换处理
		                $achieveJudgeDetailsTreeTable.refreshPoint(id);
		            },
		            beforeExpand : function($achieveJudgeDetailsTreeTable, id) {
		            },
		            afterExpand : function($achieveJudgeDetailsTreeTable, id) {
		            },
		            beforeClose : function($achieveJudgeDetailsTreeTable, id) {

		            }
		        });
		        $achieveJudgeDetailsTreeTable.initParents('${parentIds}', "0");//在保存编辑时定位展开当前节点

		});

		function del(con,id){
			jp.confirm('确认要删除绩效评定详情吗？', function(){
				jp.loading();
	       	  	$.get("${ctx}/achieve/achieveJudgeDetails/delete?id="+id, function(data){
	       	  		if(data.success){
	       	  			$achieveJudgeDetailsTreeTable.del(id);
	       	  			jp.success(data.msg);
	       	  		}else{
	       	  			jp.error(data.msg);
	       	  		}
	       	  	})

       		});

		}

		function judge_details_refreshNode(data) {//刷新节点
            var current_id = data.body.achieveJudgeDetails.id;
			var target = $achieveJudgeDetailsTreeTable.get(current_id);
			var old_parent_id = target.attr("pid") == undefined?'1':target.attr("pid");
			var current_parent_id = data.body.achieveJudgeDetails.parentId;
			var current_parent_ids = data.body.achieveJudgeDetails.parentIds;
			if(old_parent_id == current_parent_id){
				if(current_parent_id == '0'){
					$achieveJudgeDetailsTreeTable.refreshPoint(-1);
				}else{
					$achieveJudgeDetailsTreeTable.refreshPoint(current_parent_id);
				}
			}else{
				$achieveJudgeDetailsTreeTable.del(current_id);//刷新删除旧节点
				$achieveJudgeDetailsTreeTable.initParents(current_parent_ids, "0");
			}
        }
		function judge_details_refresh(){//刷新
			var index = jp.loading("正在加载，请稍等...");
			$achieveJudgeDetailsTreeTable.refresh();
			jp.close(index);
		}

    function diy_slice_str(str){
		if('${achieveJudgeDetails.achieveJudgeId}'){

			if(str.length>10){
				str=str.slice(0,10)+"...";
			}
			return str;
		}else{
			if(str.length>5){
				str=str.slice(0,5)+"...";
			}
			return str;
		}
    }
</script>
<script type="text/html" id="achieveJudgeDetailsTreeTableTpl">
			<td title="{{d.row.name === undefined ? '': d.row.name}}">

                {{# if(d.row.name){ }}
                    {{diy_slice_str(d.row.name)}}
                {{# } }}
			</td>
			<td title="{{d.row.achieveA === undefined ? '': d.row.achieveA}}">
                {{# if(d.row.achieveA){ }}
                    {{diy_slice_str(d.row.achieveA)}}
                {{# } }}
			</td>
			<td title="{{d.row.achieveB === undefined ? '': d.row.achieveB}}">
                {{# if(d.row.achieveB){ }}
                    {{diy_slice_str(d.row.achieveB)}}
                {{# } }}
			</td>
			<td title="{{d.row.achieveC === undefined ? '': d.row.achieveC}}">
                {{# if(d.row.achieveC){ }}
                    {{diy_slice_str(d.row.achieveC)}}
                {{# } }}
			</td>
			<td title="{{d.row.achieveD === undefined ? '': d.row.achieveD}}">
                {{# if(d.row.achieveD){ }}
                    {{diy_slice_str(d.row.achieveD)}}
                {{# } }}
			</td>
			{{# if(!'${achieveJudgeDetails.achieveJudgeId}'){ }}
				<td>
					<div class="btn-group">
						<button type="button" class="btn  btn-primary btn-xs dropdown-toggle" data-toggle="dropdown">
							<i class="fa fa-cog"></i>
							<span class="fa fa-chevron-down"></span>
						</button>
					  <ul class="dropdown-menu" role="menu">
						<shiro:hasPermission name="achieve:achieveJudgeDetails:edit">
							<li><a href="#" onclick="jp.openSaveDialog('修改绩效评定详情', '${ctx}/achieve/achieveJudgeDetails/form?id={{d.row.id}}
							&achieveJudgeId={{d.row.achieveJudgeId}}','800px', '500px',judge_details_refreshNode)"><i class="fa fa-edit"></i> 修改</a></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="achieve:achieveJudgeDetails:del">
							<li><a  onclick="return del(this, '{{d.row.id}}')"><i class="fa fa-trash"></i> 删除</a></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="achieve:achieveJudgeDetails:add">
							<li><a href="#" onclick="jp.openSaveDialog('添加下级绩效评定详情', '${ctx}/achieve/achieveJudgeDetails/form?parent.id={{d.row.id}}
							&achieveJudgeId={{d.row.achieveJudgeId}}','800px', '500px',judge_details_refreshNode)"><i class="fa fa-plus"></i> 添加下级绩效评定详情</a></li>
						</shiro:hasPermission>
					  </ul>
					</div>
				</td>
			{{# } }}
	</script>