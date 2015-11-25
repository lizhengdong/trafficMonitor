package com.trafficMonitor.service.impl;

import com.trafficMonitor.model.User;
import com.trafficMonitor.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lizhengdong on 11/20/15.
 * Author: lizhengdong
 * MailBox: 837718866@qq.com
 * Update by lizhengdong on 11/20/15.
 * Description: RegisterServiceImpl
 */
/*

@Service(value="RegisterService")
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private RegisterDao registerDAO;

    @Override
    public boolean registerUser(User user) {
        if (registerDAO.insertUser(user) > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean alreadyExistUserName(String username) {
        if (registerDAO.selectUserNameCount(username) > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean alreadyExistMailbox(String mailbox) {
        if (registerDAO.selectMailboxCount(mailbox) > 0) {
            return true;
        } else {
            return false;
        }
    }

}
*/
