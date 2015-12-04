package com.trafficMonitor.controller;

import com.trafficMonitor.model.UserTest;
import com.trafficMonitor.service.UserTestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * Created by lizhengdong on 11/26/15.
 * Author: lizhengdong
 * MailBox: 837718866@qq.com
 * Update by lizhengdong on 11/26/15.
 * Description: UserTestController
 */
@Controller
@RequestMapping("/user")
public class UserTestController {
    @Resource
    private UserTestService userTestService;

    @RequestMapping("/showUser")
    public String toIndex(@RequestParam(value = "id", required = true) Integer userId, Model model) {
        UserTest userTest = this.userTestService.getUserById(userId);
        model.addAttribute("user", userTest);
        return "showUser";
    }
}
