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
    <title>Android流量监测系统注册</title>
    <link rel="stylesheet" type="text/css" href="/mycss/loginRegister.css">
    <link rel="stylesheet" type="text/css" href="/mycss/loginRegisterBase.css">
    <link rel="stylesheet" type="text/css" href="/mycss/animate-custom.css">
</head>
<body>
<div class="codrops-demos">
    <span>安卓<strong>流量监测</strong>系统注册</span>
</div>
<div id="container_demo">
    <div id="wrapper">
        <div id="login" class="animate form">
            <form action="/registerUser" autocomplete="on">
                <h1>注册</h1>

                <p>
                    <label for="usernamesignup" class="uname" data-icon="u">用户名</label>
                    <input id="usernamesignup" name="usernamesignup" required="required" type="text"
                           placeholder="英文用户名，例如：admin"/>
                </p>

                <p>
                    <label for="emailsignup" class="youmail" data-icon="e">邮箱</label>
                    <input id="emailsignup" name="emailsignup" required="required" type="email"
                           placeholder="邮箱地址"/>
                </p>

                <p>
                    <label for="passwordsignup" class="youpasswd" data-icon="p">密码</label>
                    <input id="passwordsignup" name="passwordsignup" required="required" type="password"
                           placeholder="密码"/>
                </p>

                <p>
                    <label for="passwordsignup_confirm" class="youpasswd" data-icon="p">确认密码</label>
                    <input id="passwordsignup_confirm" name="passwordsignup_confirm" required="required" type="password"
                           placeholder="确认密码"/>
                </p>

                <p class="signin button">
                    <input type="submit" value="注册"/>
                </p>

                <p class="change_link">
                    已有账号?<a href="login" class="to_register"> 去登录 </a>
                </p>
            </form>
        </div>

    </div>

</div>
</body>
</html>
