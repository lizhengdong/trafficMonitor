package com.trafficMonitor.service;

import com.alibaba.fastjson.JSON;
import com.trafficMonitor.model.SystemUsers;
import com.trafficMonitor.model.TrafficArff;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by lizhengdong on 12/13/15.
 * Author: lizhengdong
 * MailBox: 837718866@qq.com
 * Update by lizhengdong on 12/13/15.
 * Description: TestRegisterService
 */
@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})

public class TestRegisterService {
    private static Logger logger = Logger.getLogger(TestTrafficArffService.class);
    @Resource
    private RegisterService registerService;

   /* @Test
    public void testRegisterUser() {
        SystemUsers user = new SystemUsers();
        user.setUserName("a");
        user.setMailbox("a@qq.com");
        user.setPassword("a");
        boolean temp = registerService.registerUser(user);
        logger.info("testRegisterUser结果" + temp);
    }*/

    @Test
    public void testAlreadyExistUserName() {
        boolean temp = registerService.alreadyExistUserName("a");
        logger.info("testAlreadyExistUserName结果" + temp);
    }

    @Test
    public void testAlreadyExistMailbox() {
        boolean temp = registerService.alreadyExistMailbox("a@qq.com");
        logger.info("testAlreadyExistMailbox结果" + temp);
    }
}
