package com.trafficMonitor.controller;

import com.trafficMonitor.model.User;
import com.trafficMonitor.service.RegisterService;
import com.trafficMonitor.utils.MD5;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
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
//    @Autowired
//    private RegisterService registerService;

    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public String registerUser(@RequestParam(value = "usernamesignup", required = true) String username, @RequestParam(value = "emailsignup", required = true) String mailbox, @RequestParam(value = "youpasswd", required = true) String password, @RequestParam(value = "passwordsignup_confirm", required = true) String passwordConfirm) {
        if (StringUtils.isBlank(username)) {
            return badResult(1, "用户名不能为空");
        }
        if (StringUtils.isBlank(mailbox)) {
            return badResult(3, "邮箱不能为空");
        }
        if (StringUtils.isBlank(password)) {
            return badResult(4, "密码不能为空");
        }
        if (StringUtils.isBlank(passwordConfirm)) {
            return badResult(5, "确认密码不能为空");
        }
        if (!password.equals(passwordConfirm)) {
            return badResult(5, "两次密码不一致");
        }
       /* if (registerService.alreadyExistUserName(username)) {
            return badResult(6, "该用户名已被注册");
        }
        if (registerService.alreadyExistUserName(mailbox)) {
            return badResult(7, "该邮箱已被注册");
        }
        password = MD5.GetMD5Code(password);//密码设置成MD5
        User user = new User();
        user.setUserName(username);
        user.setPassWord(password);
        user.setMailbox(mailbox);
        if (registerService.registerUser(user)) {
            return succResult("注册成功");
        } else {
            return badResult("注册失败");
        }*/
        return null;
    }
}
