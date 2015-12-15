package com.trafficMonitor.service;

import com.trafficMonitor.model.SystemUsers;
import org.springframework.stereotype.Service;

/**
 * Created by lizhengdong on 11/20/15.
 * Author: lizhengdong
 * MailBox: 837718866@qq.com
 * Update by lizhengdong on 11/20/15.
 * Description: RegisterService
 */
public interface RegisterService {
    public boolean registerUser(SystemUsers user);

    public boolean alreadyExistUserName(String username);

    public boolean alreadyExistMailbox(String mailbox);
}
