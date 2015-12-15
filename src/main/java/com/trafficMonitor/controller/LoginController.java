package com.trafficMonitor.controller;

import com.trafficMonitor.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by lizhengdong on 12/13/15.
 * Author: lizhengdong
 * MailBox: 837718866@qq.com
 * Update by lizhengdong on 12/13/15.
 * Description: LoginController
 */
public class LoginController extends BaseController {
    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/loginUser", method = RequestMethod.POST)
    public String loginUser(@RequestParam(value = "username", required = true) String username, @RequestParam(value = "password", required = true) String password, @RequestParam(value = "loginkeeping", required = true) String loginkeeping) {
        if (loginService.login(username, password)) {
            return "index";
        } else {
            return badResult(1, "用户名或密码错误");
        }
    }
}
