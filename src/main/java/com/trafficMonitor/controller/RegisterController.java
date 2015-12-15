package com.trafficMonitor.controller;

import com.trafficMonitor.model.SystemUsers;
import com.trafficMonitor.service.RegisterService;
import com.trafficMonitor.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by lizhengdong on 11/20/15.
 * Author: lizhengdong
 * MailBox: 837718866@qq.com
 * Update by lizhengdong on 11/20/15.
 * Description: RegisterController
 */
@Controller
public class RegisterController extends BaseController {
    @Autowired
    private RegisterService registerService;

    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public String registerUser(@RequestParam(value = "usernamesignup", required = true) String username, @RequestParam(value = "emailsignup", required = true) String mailbox, @RequestParam(value = "passwordsignup", required = true) String password, @RequestParam(value = "passwordsignup_confirm", required = true) String passwordConfirm) {

        if (!password.equals(passwordConfirm) || alreadyExistUserName(username) || alreadyExistMailbox(mailbox)) {
            return "registerFail";
        }
        password = MD5.GetMD5Code(password);//密码设置成MD5
        SystemUsers user = new SystemUsers();
        user.setUserName(username);
        user.setPassword(password);
        user.setMailbox(mailbox);
        if (registerService.registerUser(user)) {
            return "registerSuccess";
        } else {
            return "registerFail";
        }
    }

    @RequestMapping(value = "/judgeUserName", method = RequestMethod.POST)
    public String judgeUserName(@RequestParam(value = "username", required = true) String username) {
        if (alreadyExistUserName(username)) {
            return badResult(1, "该用户名已被注册");
        } else {
            return succResult();
        }
    }

    public boolean alreadyExistUserName(String username) {
        if (registerService.alreadyExistUserName(username)) {
            //该用户名已被注册
            return true;
        } else {
            return false;
        }
    }

    @RequestMapping(value = "/judgeMailbox", method = RequestMethod.POST)
    public String judgeMailbox(@RequestParam(value = "mailbox", required = true) String mailbox) {
        if (alreadyExistMailbox(mailbox)) {
            return badResult(1, "该邮箱已被注册");
        } else {
            return succResult();
        }
    }

    public boolean alreadyExistMailbox(String mailbox) {
        if (registerService.alreadyExistMailbox(mailbox)) {
            //该邮箱已被注册
            return true;
        } else {
            return false;
        }
    }
}
