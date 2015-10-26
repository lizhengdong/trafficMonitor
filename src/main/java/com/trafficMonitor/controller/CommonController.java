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
public class CommonController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String indexGet() { return "index"; }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public String indexPost() {
        return "index";
    }

    @RequestMapping(value="*")
    public String fourZeroFour(){
        return "404";
    }
}
