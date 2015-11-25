package com.trafficMonitor.service.impl;

import com.trafficMonitor.dao.UserTestDao;
import com.trafficMonitor.model.UserTest;
import com.trafficMonitor.service.UserTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lizhengdong on 11/24/15.
 * Author: lizhengdong
 * MailBox: 837718866@qq.com
 * Update by lizhengdong on 11/24/15.
 * Description: UserTestServiceImpl
 */
@Service("userTestService")
public class UserTestServiceImpl implements UserTestService{
    @Autowired
    private UserTestDao userTestDao;

    public UserTest getUserById(int userId) {
        // TODO Auto-generated method stub
        return this.userTestDao.selectByPrimaryKey(userId);
    }
}
