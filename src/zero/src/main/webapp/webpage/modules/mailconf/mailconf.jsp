<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>邮箱客户端配置</title>
    <meta name="decorator" content="ani"/>
    <script>
        function showTips() {
            jQuery('#tips').slideToggle(200);
        }
    </script>
    <style>
        .btn-text {
            display: inline-block;
            line-height: 2;
            width: 22px;
        }
        .btn-text:hover {
            color: #29b8d2;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        服务状态：<c:if test="${isRunning}"><span style="color:#38ff73">正在运行</span></c:if>
                        <c:if test="${!isRunning}"><span style="color:#ff6633">已停止</span></c:if>
                        &nbsp;&nbsp;
                        <c:if test="${isRunning}">
                            运行时间：<span id="running-time"></span>
                        </c:if>
                    </h3>
                </div>
                <div class="panel-body">
                    <div>
                        <c:if test="${!isRunning}"><a class="btn btn-primary" href="${ctx}/mailconf/start">启动</a></c:if>
                        <c:if test="${isRunning}"><a class="btn btn-success" href="${ctx}/mailconf/stop">停止运行</a>
                            <a class="btn btn-primary" href="${ctx}/mailconf/restart">重新启动</a></c:if>
                        <button id="btn-config" class="btn btn-success">数据规则配置</button>&nbsp;
                        <span class="glyphicon glyphicon-question-sign" aria-hidden="true" onclick="showTips()"></span>
                    </div>
                    <div id="tips" style="display: none;">
                        <br/>
                        <p>
                            首先请在你使用的邮箱设置里开启IMAP，保存表单后需重启生效。
                        </p>
                    </div>
                    <form:form id="inputForm" modelAttribute="config" action="${ctx}/mailconf/save"
                               method="post" class="form-horizontal" >
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>服务地址：</label>
                            <div class="col-sm-10">
                                <form:input path="host" htmlEscape="false" class="form-control required"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>端口：</label>
                            <div class="col-sm-10">
                                <form:input path="port" htmlEscape="false" class="form-control required"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>邮箱地址：</label>
                            <div class="col-sm-10">
                                <form:input path="address" htmlEscape="false" class="form-control required"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>密码：</label>
                            <div class="col-sm-10">
                                <form:input path="password" htmlEscape="false" class="form-control required"/>
                            </div>
                        </div>
                        <div class="col-lg-3"></div>
                        <div class="col-lg-6">
                            <div class="form-group text-center">
                                <div>
                                    <button class="btn btn-primary btn-block btn-lg btn-parsley"
                                            data-loading-text="正在保存...">保存
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
<!--配置页模板-->
<script id="tpl-config" type="text/x-mustache">
    <div id="config-container" style="margin: 6px;">
        <button id="btn-add-rules" class="btn btn-primary">添加规则组</button>
        <button id="btn-save-rules" class="btn btn-primary">修改保存</button>
        <hr/>
        <form id="form-rules">
        <table class="table">
            <thead>
                <tr>
                <td>名称</td>
                <td>邮箱域名</td>
                </tr>
            <thead>
            <tbody>
            {{#data}}
                <tr uuid="{{id}}">
                <td><input type="text" name="name" value="{{name}}" class="form-control name" placeholder="规则组名称" /></td>
                <td><input type="text" name="host" value="{{host}}" class="form-control host" placeholder="邮箱域名" /></td>
                <td><span class="glyphicon glyphicon-pencil btn-text" aria-hidden="true"></span>
                <span class="glyphicon glyphicon-remove btn-text" id="btn-remove" aria-hidden="true"></span></td></tr>
            {{/data}}
            </tbody>
        </table>
        </form>
    </div>
</script>
<!--配置规则模板-->
<script id="tpl-config-rules" type="text/x-mustache">
    <div id="config-rules-container" style="margin: 6px;">
    <form id="rules-form">
        <table class="table">
            <thead>
                <tr>
                <td>参数名称</td>
                <td>规则表达式</td>
                </tr>
            <thead>
            <tbody>
            {{#rules}}
                <tr>
                <td>{{name}}</td>
                <td><input name="{{name}}" value="{{rule}}" type="text" class="form-control"/></td>
                </tr>
            {{/rules}}
            </tbody>
        </table>
    </form>
    </div>
</script>
<script>

    jQuery(document).ready(function () {

        let configData = {};
        let M = Mustache;
        let configTpl = jQuery('#tpl-config').html();
        let rulesTpl = jQuery('#tpl-config-rules').html();
        // 配置按钮事件绑定
        let configBtn = jQuery('#btn-config');
        let dataUrl = '${ctx}/mailconf/getrules';
        let saveDataUrl = '${ctx}/mailconf/saverules';

        let uuid = function () {
            var s = [];
            var hexDigits = "0123456789abcdef";
            for (var i = 0; i < 36; i++) {
                s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
            }
            s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
            s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
            s[8] = s[13] = s[18] = s[23] = "-";
            var uuid = s.join("");
            return uuid;
        }

        let validateRuleConfig = function () {
            $('#form-rules').validate({
                rules:{
                    name:{
                        required: true
                    },
                    host:{
                        required: true
                    }
                }
            });
        };

        let openConfigLayer = function () {

            let isRunning = ${isRunning};
            if (isRunning == true) {
                jp.alert("配置数据规则前请先停止服务！");
                return;
            }
            jQuery.ajax({
               url: dataUrl,
               dataType: 'json',
               success: function(response) {

                   for (var i = 0; i < response.data.length ; i++) {
                       let item = response.data[i];
                       configData[item.id] = item;
                   }
                   layer.open({
                       type: 1,
                       title: '数据规则配置',
                       content: M.render(configTpl, response),
                       area: ['1536px', '596px'],
                       success: function(layero, index) {

                           jQuery('#config-container #btn-add-rules').click(function() {

                               let tpl = '<tr uuid="@{id}"><td><input type="text" name="name" class="form-control name" placeholder="规则组名称" /></td>'
                                   + '<td><input type="text" host="host" class="form-control host" placeholder="邮箱域名" /></td>'
                                   + '<td><span class="glyphicon glyphicon-pencil btn-text" aria-hidden="true"></span>'
                                   + '<span class="glyphicon glyphicon-remove btn-text" id="btn-remove" aria-hidden="true"></span></td></tr>';
                               let id = uuid();
                               jQuery('#config-container table tbody').append(tpl.replace('@{id}', id));
                               let rules = [{
                                   name: 'name',
                                   rule: ''
                               },{
                                   name: 'sex',
                                   rule: ''
                               },{
                                   name: 'birth',
                                   rule: ''
                               }];
                               configData[id] = {id: id, name: '', host: '', rules: rules};
                               validateRuleConfig();
                           });
                           validateRuleConfig();
                           jQuery('#config-container #btn-save-rules').click(function() {
                               let trs = jQuery('#config-container tbody tr');
                               for (let i = 0; i < trs.length; i ++) {
                                   let item = jQuery(trs[i])
                                   let id = item.attr('uuid');
                                   let name = item.find('.name').val();
                                   let host = item.find('.host').val();
                                   configData[id].name = name;
                                   configData[id].host = host;
                               }
                               let realData = [];
                               for (let i in configData) {
                                   realData.push(configData[i]);
                               }
                               // 保存配置信息
                               jQuery.ajax({
                                   url: saveDataUrl,
                                   data: JSON.stringify(realData),
                                   contentType: "application/json; charset=utf-8",
                                   dataType: 'json',
                                   type: 'post',
                                   success: function(data) {
                                       if (data == 1) {
                                           jp.alert("保存成功");
                                       }
                                   }
                               });
                           });
                           jQuery('#config-container').on('click', '.glyphicon-remove', function() {
                               let cthis = this;
                               jp.confirm('确认删除该规则组吗？这样会丢失其中所有的规则配置', function() {
                                   let elmt = $(cthis).parent().parent();
                                   let id = elmt.attr('uuid');
                                   delete configData[id];
                                   elmt.remove();
                               });
                           });
                           jQuery('#config-container').on('click', '.glyphicon-pencil', function() {
                               let elmt = $(this).parent().parent();
                               openRuleConfigLayer(elmt.attr('uuid'));
                           });
                       }
                   });
               }
            });

        };

        let openRuleConfigLayer = function(id) {

            let data = configData[id];
            layer.open({
                title: '配置规则',
                type: 1,
                content: M.render(rulesTpl, data),
                area: ['1200px', '480px'],
                cancel: function(index, layero){

                    let form = jQuery('#rules-form');
                    let inputList = form.find("input");
                    let rules = [];
                    for (let i = 0; i < inputList.length; i ++) {
                        let item = jQuery(inputList[i]);
                        let name = item.attr('name');
                        let value = item.val();
                        rules.push({name: name, rule: value});
                    }
                    configData[id].rules = rules;
                    return true;
                }
            })
        };
        configBtn.click(function () {
            openConfigLayer();
            return;
        });
    });

    $('#inputForm').validate({
        rules:{
            host:{
                required: true
            },
            port:{
                required: true,
                digits:true
            },
            address:{
                required: true,
                email: true
            },
            password: {
                required: true
            }
        }
    });

</script>
<c:if test="${isRunning}">
    <script>
        var convertTimeToStr = function (times) {
            var days = parseInt(times / 1000 / 60 / 60 / 24); // 天数
            var hours = parseInt((times / 1000 / 60 / 60) % 24); // 小时
            var minutes = parseInt((times / 1000 / 60) % 60); // 分钟数
            var seconds = parseInt((times / 1000) % 60); // 秒数
            return days + '天' + hours + '时' + minutes + '分' + seconds + '秒';
        };
        var runningTime = ${runningTime};
        var elmt = jQuery('#running-time');
        // elmt.html(convertTimeToStr(runningTime));
        window.setInterval(function () {
            runningTime += 1000;
            elmt.html(convertTimeToStr(runningTime));
        }, 1000);
    </script>
</c:if>
<c:if test="${saveSuccess}">
    <script>
        window.setTimeout(function() {
            jp.alert('保存成功');
        }, 200);
    </script>
</c:if>
</body>
</html>