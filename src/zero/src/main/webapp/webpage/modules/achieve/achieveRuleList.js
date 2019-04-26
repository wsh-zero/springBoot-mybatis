<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function () {
    $('#achieveRuleTable').bootstrapTable({

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
        pageNumber: 1,
        //每页的记录行数（*）
        pageSize: 10,
        //可供选择的每页的行数（*）
        pageList: [10, 25, 50, 100],
        //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据
        url: "${ctx}/achieve/achieveRule/data",
        //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
        //queryParamsType:'',
        ////查询参数,每次调用是会带上这个参数，可自定义
        queryParams: function (params) {
            var searchParam = $("#searchForm").serializeJSON();
            searchParam.pageNo = params.limit === undefined ? "1" : params.offset / params.limit + 1;
            searchParam.pageSize = params.limit === undefined ? -1 : params.limit;
            searchParam.orderBy = params.sort === undefined ? "" : params.sort + " " + params.order;
            return searchParam;
        },
        //分页方式：client客户端分页，server服务端分页（*）
        sidePagination: "server",
        contextMenuTrigger: "right",//pc端 按右键弹出菜单
        contextMenuTriggerMobile: "press",//手机端 弹出菜单，click：单击， press：长按。
        // contextMenu: '#achieve_rule_context-menu',
        // onContextMenuItem: function(row, $el){
        //
        // },
        //
        onClickRow: function (row, $el) {
        },
        onShowSearch: function () {
        },
        columns: [{
            checkbox: true

        }, {
            title: '序号',
            align: 'center',
            formatter: function (value, row, index) {
                return value = index + 1;
            }
        }

            , {
                field: 'name',
                title: '评分表名称'

            }
            , {
                field: 'number',
                title: '评分表编号'

            }
            , {
                field: 'assessorCycle',
                title: '考核周期',
                formatter: function (value, row, index) {
                    var msg;
                    switch (value) {
                        case 1:
                            msg = "月"
                            break;
                        case 2:
                            msg = "季"
                            break;
                        case 3:
                            msg = "年"
                            break;
                        default:
                            msg = value
                    }
                    return msg;
                }

            }
            , {
                field: 'createBy.name',
                title: '创建人'

            }
            , {
                field: 'createDate',
                title: '创建时间'

            }
            , {
                field: 'state',
                title: '状态',
                formatter: function (value, row, index) {
                    var msg;
                    switch (value) {
                        case 0:
                            msg = "禁用"
                            break;
                        case 1:
                            msg = "启用"
                            break;
                        default:
                            msg = value
                    }
                    return msg;
                }

            }
        ]

    });


    if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {//如果是移动端


        $('#achieveRuleTable').bootstrapTable("toggleView");
    }

    $('#achieveRuleTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
        'check-all.bs.table uncheck-all.bs.table', function () {
        $('#achieve_rule_remove').prop('disabled', !$('#achieveRuleTable').bootstrapTable('getSelections').length);
        $('#achieve_rule_view,#achieve_rule_add_content,#achieve_rule_edit').prop('disabled', $('#achieveRuleTable').bootstrapTable('getSelections').length != 1);
    });

});

function achieve_rule_getIdSelections() {
    return $.map($("#achieveRuleTable").bootstrapTable('getSelections'), function (row) {
        return row.id
    });
}

function achieve_rule_getSelections() {
    return $.map($("#achieveRuleTable").bootstrapTable('getSelections'), function (row) {
        return row
    });
}

function achieve_rule_deleteAll() {

    jp.confirm('确认要删除该绩效考核规则记录吗？', function () {
        jp.loading();
        jp.get("${ctx}/achieve/achieveRule/deleteAll?ids=" + achieve_rule_getIdSelections(), function (data) {
            if (data.success) {
                $('#achieveRuleTable').bootstrapTable('refresh');
                jp.success(data.msg);
            } else {
                jp.error(data.msg);
            }
        })

    })
}

//刷新列表
function achieve_rule_refresh() {
    $('#achieveRuleTable').bootstrapTable('refresh');
}

function achieve_rule_add() {
    jp.openSaveDialog('新增绩效考核规则', "${ctx}/achieve/achieveRule/form", '800px', '500px');
}


function achieve_rule_edit(id) {//没有权限时，不显示确定按钮
    if (id == undefined) {
        id = achieve_rule_getIdSelections();
    }
    jp.openSaveDialog('编辑绩效考核规则', "${ctx}/achieve/achieveRule/form?id=" + id, '800px', '500px');
}

function achieve_rule_add_content(id) {
    if (id == undefined) {
        id = achieve_rule_getIdSelections();
    }
    var row = achieve_rule_getSelections()[0]
    var achieveJudgeId = row.achieveJudgeId
    var params = ""
    if (achieveJudgeId !== "0") {
        params += "?achieveRuleId=" + id + "&achieveJudgeId=" + achieveJudgeId + "&totalScore0=" + row.totalScore0
        jp.openSaveDialog('编辑绩效考核规则', "${ctx}/achieve/achieveRuleDetails/form" + params, '800px', '500px', achieve_rule_refresh);
    } else {
        params += "?achieveRuleId=" + id + "&achieveJudgeId=" + achieveJudgeId + "&totalScore1=" + row.totalScore1
             + "&totalScore3=" + row.totalScore3
        jp.openViewDialog('编辑绩效考核规则', "${ctx}/achieve/achieveRuleDetails/form1"
            + params, '800px', '500px');
    }

}

function achieve_rule_view(id) {//没有权限时，不显示确定按钮
    if (id == undefined) {
        id = achieve_rule_getIdSelections();
    }
    jp.openSaveDialog('编辑绩效考核规则', "${ctx}/achieve/achieveRuleDetails/form?achieveRuleId=" + id, '800px', '500px');
}

function toRuleDetails() {//没有权限时，不显示确定按钮
    var row =achieve_rule_getSelections()[0];
    var achieveRuleId=row.id;
    var ruleName=row.name;
    var ruleNumber=row.number;
    var params="?achieveRuleId="+achieveRuleId+"&ruleName="+ruleName+"&ruleNumber="+ruleNumber
    jp.go("${ctx}/achieve/achieveRuleDetails/list" + params);
}


</script>