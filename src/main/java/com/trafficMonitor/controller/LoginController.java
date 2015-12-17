package com.trafficMonitor.controller;

import com.trafficMonitor.model.SystemUsers;
import com.trafficMonitor.service.LoginService;
import com.trafficMonitor.utils.cookie.CookieTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lizhengdong on 12/13/15.
 * Author: lizhengdong
 * MailBox: 837718866@qq.com
 * Update by lizhengdong on 12/13/15.
 * Description: LoginController
 */
@Controller
@RequestMapping("/")
public class LoginController extends BaseController {
    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/loginUser", method = RequestMethod.POST)
    public String loginUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestParam(value = "username", required = true) String username, @RequestParam(value = "password", required = true) String password, @RequestParam(value = "loginkeeping", required = false) String loginkeeping) {
        if (loginService.login(username, password)) {
            if (loginkeeping != null) {
                //写入cookie
                int loginMaxAge = 24 * 60 * 60;//定义账户密码的生命周期，单位是秒。此为1天。
                CookieTool.addCookie(httpServletResponse, "loginName", username, loginMaxAge);//姓名加到cookie中
                CookieTool.addCookie(httpServletResponse, "loginPwd", password, loginMaxAge);//密码加到cookie中
                SystemUsers systemUsers = new SystemUsers();
                systemUsers.setUserName(username);
                systemUsers.setPassword(password);
                httpServletRequest.getSession().setAttribute("systemUsers", systemUsers);
            }
            return "home";
        } else {
            return badResult(1, "用户名或密码错误");
        }
    }
}
