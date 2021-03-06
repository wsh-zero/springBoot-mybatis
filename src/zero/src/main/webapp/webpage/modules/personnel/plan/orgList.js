<%@ page contentType="text/html;charset=UTF-8" %>
<script>
	    var $orgTreeTable=null;  
		$(document).ready(function() {
			$orgTreeTable=$('#orgTreeTable').treeTable({  
		    	   theme:'vsStyle',	           
					expandLevel : 2,
					column:0,
					checkbox: false,
		            url:'${ctx}/personnel/plan/org/getChildren?parentId=',  
		            callback:function(item) { 
		            	 var treeTableTpl= $("#orgTreeTableTpl").html();
		            	 item.dict = {};

		            	 var result = laytpl(treeTableTpl).render({
								  row: item
							});
		                return result;                   
		            },  
		            beforeClick: function($orgTreeTable, id) { 
		                //异步获取数据 这里模拟替换处理  
		                $orgTreeTable.refreshPoint(id);  
		            },  
		            beforeExpand : function($orgTreeTable, id) {   
		            },  
		            afterExpand : function($orgTreeTable, id) {  
		            },  
		            beforeClose : function($orgTreeTable, id) {    
		            	
		            }  
		        });
		        
		        $orgTreeTable.initParents('${parentIds}', "0");//在保存编辑时定位展开当前节点
		       
		});
		
		function del(con,id){  
			jp.confirm('确认要删除组织吗？', function(){
				jp.loading();
	       	  	$.get("${ctx}/personnel/plan/org/delete?id="+id, function(data){
	       	  		if(data.success){
	       	  			$orgTreeTable.del(id);
	       	  			jp.success(data.msg);
	       	  		}else{
	       	  			jp.error(data.msg);
	       	  		}
	       	  	})
	       	   
       		});
	
		} 
		
		function refreshNode(data) {//刷新节点
            var current_id = data.body.org.id;
			var target = $orgTreeTable.get(current_id);
			var old_parent_id = target.attr("pid") == undefined?'1':target.attr("pid");
			var current_parent_id = data.body.org.parentId;
			var current_parent_ids = data.body.org.parentIds;
			if(old_parent_id == current_parent_id){
				if(current_parent_id == '0'){
					$orgTreeTable.refreshPoint(-1);
				}else{
					$orgTreeTable.refreshPoint(current_parent_id);
				}
			}else{
				$orgTreeTable.del(current_id);//刷新删除旧节点
				$orgTreeTable.initParents(current_parent_ids, "0");
			}
        }
		function refresh(){//刷新
			var index = jp.loading("正在加载，请稍等...");
			$orgTreeTable.refresh();
			jp.close(index);
		}
</script>
<script type="text/html" id="orgTreeTableTpl">
			<td>
			<c:choose>
			      <c:when test="${fns:hasPermission('personnel:plan:org:edit')}">
				    <a  href="#" onclick="jp.openSaveDialog('编辑组织', '${ctx}/personnel/plan/org/form?id={{d.row.id}}','800px', '500px')">
							{{d.row.name === undefined ? "": d.row.name}}
					</a>
			      </c:when>
			      <c:when test="${fns:hasPermission('personnel:plan:org:view')}">
				    <a  href="#" onclick="jp.openViewDialog('查看组织', '${ctx}/personnel/plan/org/form?id={{d.row.id}}','800px', '500px')">
							{{d.row.name === undefined ? "": d.row.name}}
					</a>
			      </c:when>
			      <c:otherwise>
							{{d.row.name === undefined ? "": d.row.name}}
			      </c:otherwise>
			</c:choose>
			</td>
			<td>
							{{d.row.departCode === undefined ? "": d.row.departCode}}
			</td>
			<td>
							{{d.row.orgType.name === undefined ? "": d.row.orgType.name}}
			</td>
			<td>
				<div class="btn-group">
			 		<button type="button" class="btn  btn-primary btn-xs dropdown-toggle" data-toggle="dropdown">
						<i class="fa fa-cog"></i>
						<span class="fa fa-chevron-down"></span>
					</button>
				  <ul class="dropdown-menu" role="menu">
					<shiro:hasPermission name="personnel:plan:org:view">
						<li><a href="#" onclick="jp.openViewDialog('查看组织', '${ctx}/personnel/plan/org/form?id={{d.row.id}}','800px', '500px')"><i class="fa fa-search-plus"></i> 查看</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="personnel:plan:org:edit">
						<li><a href="#" onclick="jp.openSaveDialog('修改组织', '${ctx}/personnel/plan/org/form?id={{d.row.id}}','800px', '500px')"><i class="fa fa-edit"></i> 修改</a></li>
		   			</shiro:hasPermission>
		   			<shiro:hasPermission name="personnel:plan:org:del">
		   				<li><a  onclick="return del(this, '{{d.row.id}}')"><i class="fa fa-trash"></i> 删除</a></li>
					</shiro:hasPermission>
		   			<shiro:hasPermission name="personnel:plan:org:add">
						<li><a href="#" onclick="jp.openSaveDialog('添加下级组织', '${ctx}/personnel/plan/org/form?parent.id={{d.row.id}}','800px', '500px')"><i class="fa fa-plus"></i> 添加下级组织</a></li>
					</shiro:hasPermission>
				  </ul>
				</div>
			</td>
	</script>