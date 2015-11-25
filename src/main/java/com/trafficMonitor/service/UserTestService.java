package com.trafficMonitor.service;

import com.trafficMonitor.model.UserTest;
import org.springframework.stereotype.Service;

/**
 * Created by lizhengdong on 11/24/15.
 * Author: lizhengdong
 * MailBox: 837718866@qq.com
 * Update by lizhengdong on 11/24/15.
 * Description: UserTestService
 */
public interface UserTestService {
    public UserTest getUserById(int userId);
}
