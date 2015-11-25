package com.trafficMonitor.service;

import com.trafficMonitor.model.User;
import org.springframework.stereotype.Service;

/**
 * Created by lizhengdong on 11/20/15.
 * Author: lizhengdong
 * MailBox: 837718866@qq.com
 * Update by lizhengdong on 11/20/15.
 * Description: RegisterService
 */
@Service("registerService")
public interface RegisterService {
    public boolean registerUser(User user);

    public boolean alreadyExistUserName(String username);

    public boolean alreadyExistMailbox(String mailbox);
}
