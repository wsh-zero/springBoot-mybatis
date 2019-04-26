<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<a href="#" class="dropdown-toggle" data-toggle="dropdown">
    <i class="fa fa-bell-o"></i>
    <span class="label label-info">${count }</span>
</a>
<ul class="dropdown-menu animated fadeIn">
    <li class="messages-top text-center">
        你有 ${count } 条新通知.
    </li>
    <c:forEach items="${page.list}" var="oaNotify">
        <li class="dropdown-notifications">
            <a class="J_menuItem" href="javascript:void(0)" thref="${ctx}/oa/oaNotify/form?id=${oaNotify.id}&isSelf=true">
                <div class="notification">
                    <i class="fa fa-bell-o"></i> ${fns:abbr(oaNotify.title,50)}
                    <span class="pull-right text-muted small">${fns:getTime(oaNotify.updateDate)}前</span>
                </div>
            </a>
        </li>
    </c:forEach>
</ul>