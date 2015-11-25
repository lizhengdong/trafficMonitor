<%--
  Created by IntelliJ IDEA.
  User: lizhengdong
  Date: 10/16/15
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf_8">
    <title>Android流量监测系统登录</title>
    <link rel="stylesheet" type="text/css" href="/static/mycss/loginRegister.css">
    <link rel="stylesheet" type="text/css" href="/static/mycss/loginRegisterBase.css">
    <link rel="stylesheet" type="text/css" href="/static/mycss/animate-custom.css">
</head>
<body>
<div class="codrops-demos">
    <span>安卓<strong>流量监测</strong>系统登录</span>
</div>
<div id="container_demo">
    <div id="wrapper">
        <div id="login" class="animate form">
            <form action="/index" autocomplete="on">
                <h1>登录</h1>
                <p>
                    <label for="username" class="uname" data-icon="u">您的邮箱或用户名</label>
                    <input id="username" name="username" required="required" type="text"
                           placeholder="用户名 或者 邮箱地址"/>
                </p>

                <p>
                    <label for="password" class="youpasswd" data-icon="p">你的密码</label>
                    <input id="password" name="password" required="required" type="password"
                           placeholder="填写密码"/>
                </p>

                <p class="keeplogin">
                    <input type="checkbox" name="loginkeeping" id="loginkeeping" value="loginkeeping"/>
                    <label for="loginkeeping">保持登录状态</label>
                </p>

                <p class="login button">
                    <input type="submit" value="登录"/>
                </p>

                <p class="change_link">
                    没有账号?<a href="register" class="to_register">注册</a>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>
