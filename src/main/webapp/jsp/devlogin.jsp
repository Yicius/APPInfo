<%--
  Created by IntelliJ IDEA.
  User: Xue
  Date: 2019/4/26
  Time: 14:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/statics/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="${pageContext.request.contextPath}/statics/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="${pageContext.request.contextPath}/statics/css/nprogress.css" rel="stylesheet">
    <!-- Animate.css -->
    <link href="https://colorlib.com/polygon/gentelella/css/animate.min.css" rel="stylesheet">
    <!-- Custom Theme Style -->
    <link href="${pageContext.request.contextPath}/statics/css/custom.min.css" rel="stylesheet">
</head>
<body class="login">
<div>
    <a class="hiddenanchor" id="signup"></a>
    <a class="hiddenanchor" id="signin"></a>

    <div class="login_wrapper">
        <div class="animate form login_form">
            <section class="login_content">
                <form action="dologin" method="post">
                    <h1>Login Form</h1>
                    <div>
                        <input type="text" class="form-control" name="devCode" placeholder="Username" required="" />
                    </div>
                    <div>
                        <input type="password" class="form-control" name="devPassword" placeholder="Password" required="" />
                    </div>
                    <span>${error}</span>
                    <div>
                        <button type="submit" class="btn btn-default btn-lg" >登录</button>
                        <button type="reset" class="btn btn-default btn-lg">重填</button>
                    </div>
                        <div>
                            <p>©2016 All Rights Reserved</p>
                        </div>
                    </div>
                </form>
            </section>
        </div>
    </div>
</div>
</body>
</html>
