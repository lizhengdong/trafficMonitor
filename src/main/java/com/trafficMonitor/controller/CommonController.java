package com.trafficMonitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by lizhengdong on 10/16/15.
 * Author: lizhengdong
 * MailBox: 837718866@qq.com
 * Update by lizhengdong on 10/16/15.
 * Description: CommonController
 */
@Controller
@RequestMapping("/")
public class CommonController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "register";
    }

    @RequestMapping(value = "/registerSuccess", method = RequestMethod.GET)
    public String registerSuccess() {
        return "registerSuccess";
    }

    @RequestMapping(value = "/registerFail", method = RequestMethod.GET)
    public String registerFail() {
        return "registerFail";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        return "home";
    }

    @RequestMapping(value = "*")
    public String fourZeroFour() {
        return "404";
    }
}
