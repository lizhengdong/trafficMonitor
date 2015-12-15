package com.trafficMonitor.service.impl;

import com.trafficMonitor.dao.SystemUsersDao;
import com.trafficMonitor.model.SystemUsers;
import com.trafficMonitor.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lizhengdong on 12/13/15.
 * Author: lizhengdong
 * MailBox: 837718866@qq.com
 * Update by lizhengdong on 12/13/15.
 * Description: RegisterServiceImpl
 */
@Service("registerService")
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private SystemUsersDao systemUsersDao;

    @Override
    public boolean registerUser(SystemUsers user) {
        if (alreadyExistUserName(user.getUserName()) || alreadyExistMailbox(user.getMailbox())) {
            return false;
        }
        if (systemUsersDao.insert(user) > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean alreadyExistUserName(String username) {
        if (systemUsersDao.selectByName(username) > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean alreadyExistMailbox(String mailbox) {

        if (systemUsersDao.selectByMail(mailbox) > 0) {
            return true;
        } else {
            return false;
        }
    }
}
