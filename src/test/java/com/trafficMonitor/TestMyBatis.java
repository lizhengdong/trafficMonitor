package com.trafficMonitor;
import javax.annotation.Resource;

import com.trafficMonitor.model.UserTest;
import com.trafficMonitor.service.UserTestService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
/**
 * Created by lizhengdong on 11/24/15.
 * Author: lizhengdong
 * MailBox: 837718866@qq.com
 * Update by lizhengdong on 11/24/15.
 * Description: TestMyBatis
 */


@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})

public class TestMyBatis {
    private static Logger logger = Logger.getLogger(TestMyBatis.class);
//      private ApplicationContext ac = null;
    @Resource
    private UserTestService userTestService = null;

 /* @Before
  public void before() {
      ac = new ClassPathXmlApplicationContext("applicationContext.xml");
      userTestService = (UserTestService) ac.getBean("userService");
  }*/

    @Test
    public void test1() {
        UserTest user = userTestService.getUserById(1);
        // System.out.println(user.getUserName());
        // logger.info("值："+user.getUserName());
        logger.info(JSON.toJSONString(user));
    }
}