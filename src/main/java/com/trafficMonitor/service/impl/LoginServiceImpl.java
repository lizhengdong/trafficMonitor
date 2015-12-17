package com.trafficMonitor.service.impl;

import com.trafficMonitor.dao.SystemUsersDao;
import com.trafficMonitor.service.LoginService;
import com.trafficMonitor.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lizhengdong on 12/13/15.
 * Author: lizhengdong
 * MailBox: 837718866@qq.com
 * Update by lizhengdong on 12/13/15.
 * Description: LoginServiceImpl
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {
    @Autowired
    private SystemUsersDao systemUsersDao;

    @Override
    public boolean login(String username, String password) {
        String selectedPassword = systemUsersDao.selectPasswordByName(username);
        if (selectedPassword == null || selectedPassword.length() == 0) {
            return false;
        }
        if (selectedPassword.equals(MD5.GetMD5Code(password))) {
            return true;
        } else {
            return false;
        }
    }
}
