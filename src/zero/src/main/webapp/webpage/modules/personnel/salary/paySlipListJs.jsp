<%@ page contentType="text/html;charset=UTF-8" %>
<script>
    $(document).ready(function () {
        $('#paySlipTable').bootstrapTable({

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
            pageNumber: 1,
            //每页的记录行数（*）
            pageSize: 10,
            //可供选择的每页的行数（*）
            pageList: [10, 25, 50, 100],
            //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据
            url: "${ctx}/personnel/salary/paySlip/data?isSelf=${isSelf}",
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
            contextMenu: '#context-menu',
            onContextMenuItem: function (row, $el) {
                if ($el.data("item") == "edit") {
                    edit(row.id);
                } else if ($el.data("item") == "view") {
                    view(row.id);
                } else if ($el.data("item") == "delete") {
                    jp.confirm('确认要删除该工资条记录吗？', function () {
                        jp.loading();
                        jp.get("${ctx}/personnel/salary/paySlip/delete?id=" + row.id, function (data) {
                            if (data.success) {
                                $('#paySlipTable').bootstrapTable('refresh');
                                jp.success(data.msg);
                            } else {
                                jp.error(data.msg);
                            }
                        })

                    });

                }
            },

            onClickRow: function (row, $el) {
            },
            onShowSearch: function () {
                $("#search-collapse").slideToggle();
            },
            columns: [
                {
                checkbox: true,

            }
                ,{
                    title: '序号',
                    align: 'center',
                    formatter: function (value, row, index) {

                        return index + 1;
                    }
                }
                , {
                    field: 'releaseTime',
                    title: '发放月',
                    sortable: true,
                    align: 'center',
                    sortName: 'releaseTime'

                }
                , {
                    field: 'code.code',
                    title: '员工编号',
                    sortable: true,
                    align: 'center',
                    sortName: 'code.code'
                    , formatter: function (value, row, index) {

                        if (value == null || value == "") {
                            value = "-";
                        }
                        <c:choose>
                        <c:when test="${fns:hasPermission('personnel:salary:paySlip:edit')}">
                        return "<a href='javascript:edit(\"" + row.id + "\")'>" + value + "</a>";
                        </c:when>
                        <c:when test="${fns:hasPermission('personnel:salary:paySlip:view')}">
                        return "<a href='javascript:view(\"" + row.id + "\")'>" + value + "</a>";
                        </c:when>
                        <c:otherwise>
                        return value;
                        </c:otherwise>
                        </c:choose>

                    }

                }
                , {
                    field: 'name.name',
                    title: '姓名',
                    sortable: true,
                    align: 'center',
                    sortName: 'name.name'

                }
                , {
                    field: 'depart.name',
                    title: '部门',
                    sortable: true,
                    align: 'center',
                    sortName: 'depart.name'

                }
                , {
                    field: 'station.name',
                    title: '岗位',
                    sortable: true,
                    align: 'center',
                    sortName: 'station.name'

                }


                , {
                    field: 'preWage',
                    title: '税前工资',
                    sortable: true,
                    align: 'center',
                    sortName: 'preWage'

                }
                , {
                    field: 'socialSecurity',
                    title: '社保',
                    align: 'center',
                    sortable: true,
                    sortName: 'socialSecurity'

                }
                , {
                    field: 'accumulation',
                    title: '公积金',
                    align: 'center',
                    sortable: true,
                    sortName: 'accumulation'

                }
                , {
                    field: 'attendance',
                    title: '考勤',
                    align: 'center',
                    sortable: true,
                    sortName: 'attendance'

                }
                , {
                    field: 'punish',
                    title: '行政处罚',
                    sortable: true,
                    align: 'center',
                    sortName: 'punish'

                }
                , {
                    field: 'tax',
                    title: '个税',
                    sortable: true,
                    align: 'center',
                    sortName: 'tax'

                }
                , {
                    field: 'wage',
                    title: '实得月工资',
                    sortable: true,
                    align: 'center',
                    sortName: 'wage'

                }
                , {
                    field: 'titleBonus',
                    title: '职称奖金',
                    sortable: true,
                    align: 'center',
                    sortName: 'titleBonus'

                }
                , {
                    field: 'gradeBonus',
                    title: '等级奖金',
                    sortable: true,
                    sortName: 'gradeBonus'

                }
                , {
                    field: 'qualityBonus',
                    title: '资质奖金',
                    sortable: true,
                    align: 'center',
                    sortName: 'qualityBonus'

                }
                , {
                    field: 'educationBonus',
                    title: '学历奖金',
                    sortable: true,
                    align: 'center',
                    sortName: 'educationBonus'

                }
                , {
                    field: 'displayBonus',
                    title: '基础绩效奖金',
                    sortable: true,
                    align: 'center',
                    sortName: 'displayBonus'

                }

                , {
                    field: 'achPoint',
                    title: '绩效得分',
                    sortable: true,
                    sortName: 'achPoint'

                }
                , {
                    field: 'achBonus',
                    title: '实得绩效奖金',
                    sortable: true,
                    align: 'center',
                    sortName: 'achBonus'

                }
                , {
                    field: 'total',
                    title: '合计',
                    sortable: true,
                    align: 'center',
                    sortName: 'total'

                }
                , {
                field: 'status',
                title: '工资状态',
                sortable: true,
                align: 'center',
                sortName: 'status',
                formatter: function (value, row, index) {
                return jp.getDictLabel(${fns:toJson(fns:getDictList('salary_status'))}, value, "-");
                }
                }
                , {
                    field: 'updateDate',
                    title: '发放时间',
                    sortable: true,
                    align: 'center',
                    sortName: 'updateDate'

                }
                <c:if test="${!isSelf}">
                , {
                    field: 'status',
                    title: '操作',
                    sortable: true,
                    align: 'center',
                    sortName: 'status',

                    formatter: function (value, row, index) {
                        <%--value =  jp.getDictLabel(${fns:toJson(fns:getDictList('salary_status'))}, value, "-");--%>
                        if (value == 0){
                            return "<a href='javascript:editStatus(\""+row.id+"\",\"1\")'>发放</a>";
                        }else if(value == 1){
                            return value = '-'
                        }
                    }
                }
                </c:if>
            ]
        });


        if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {//如果是移动端


            $('#paySlipTable').bootstrapTable("toggleView");
        }

        $('#paySlipTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
            'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', !$('#paySlipTable').bootstrapTable('getSelections').length);
            $('#view,#edit').prop('disabled', $('#paySlipTable').bootstrapTable('getSelections').length != 1);
        });

        $("#btnImport").click(function () {
            jp.open({
                type: 2,
                area: [500, 200],
                auto: true,
                title: "导入数据",
                content: "${ctx}/tag/importExcel",
                btn: ['下载模板', '确定', '关闭'],
                btn1: function (index, layero) {
                    jp.downloadFile('${ctx}/personnel/salary/paySlip/import/template');
                },
                btn2: function (index, layero) {
                    var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                    iframeWin.contentWindow.importExcel('${ctx}/personnel/salary/paySlip/import', function (data) {
                        if (data.success) {
                            jp.success(data.msg);
                            refresh();
                        } else {
                            jp.error(data.msg);
                        }
                        jp.close(index);
                    });//调用保存事件
                    return false;
                },

                btn3: function (index) {
                    jp.close(index);
                }
            });
        });


        $("#export").click(function () {//导出Excel文件
            var searchParam = $("#searchForm").serializeJSON();
            searchParam.pageNo = 1;
            searchParam.pageSize = -1;
            var sortName = $('#paySlipTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#paySlipTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for (var key in searchParam) {
                values = values + key + "=" + searchParam[key] + "&";
            }
            if (sortName != undefined && sortOrder != undefined) {
                values = values + "orderBy=" + sortName + " " + sortOrder;
            }

            jp.downloadFile('${ctx}/personnel/salary/paySlip/export?' + values);
        })


        $("#search").click("click", function () {// 绑定查询按扭
            $('#paySlipTable').bootstrapTable('refresh');
        });

        $("#reset").click("click", function () {// 绑定查询按扭
            $("#searchForm  input").val("");
            $("#searchForm  select").val("");
            $("#searchForm  .select-item").html("");
            $('#paySlipTable').bootstrapTable('refresh');
        });

        $('#releaseTime').datetimepicker({
            format: "YYYY-MM"
        });

    });

    function getIdSelections() {
        return $.map($("#paySlipTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

    function deleteAll() {

        jp.confirm('确认要删除该工资条记录吗？', function () {
            jp.loading();
            jp.get("${ctx}/personnel/salary/paySlip/deleteAll?ids=" + getIdSelections(), function (data) {
                if (data.success) {
                    $('#paySlipTable').bootstrapTable('refresh');
                    jp.success(data.msg);
                } else {
                    jp.error(data.msg);
                }
            })

        })
    }

    function refresh() {
        $('#paySlipTable').bootstrapTable('refresh');
    }

    function add() {
        jp.go("${ctx}/personnel/salary/paySlip/form/add");
    }

    function edit(id) {
        if (id == undefined) {
            id = getIdSelections();
        }
        jp.go("${ctx}/personnel/salary/paySlip/form/edit?id=" + id);
    }
    function editStatus(id,status) {
        if(id == undefined){
            id = getIdSelections();
        }
        jp.go("${ctx}/personnel/salary/paySlip/editStatus?id=" + id+"&status="+status);
    }
    function view(id) {
        if (id == undefined) {
            id = getIdSelections();
        }
        jp.go("${ctx}/personnel/salary/paySlip/form/view?id=" + id);
    }

</script>