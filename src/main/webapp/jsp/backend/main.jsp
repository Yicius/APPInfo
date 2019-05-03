<%--
  Created by IntelliJ IDEA.
  User: Xue
  Date: 2019/5/3
  Time: 16:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/header.jsp" %>
<div class="page-title">
    <div class="title_left">
        <h3>
            <h3>
                欢迎你：${userSession.userName }<strong> | 角色：${userSession.userTypeName }</strong>
            </h3>
        </h3>
    </div>
</div>
<div class="clearfix"></div>
<%@include file="common/footer.jsp" %>
