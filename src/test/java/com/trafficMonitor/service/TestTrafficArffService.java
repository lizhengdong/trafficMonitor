package com.trafficMonitor.service;

import com.alibaba.fastjson.JSON;
import com.trafficMonitor.model.RemoteIpAndInt;
import com.trafficMonitor.model.TrafficArff;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lizhengdong on 12/1/15.
 * Author: lizhengdong
 * MailBox: 837718866@qq.com
 * Update by lizhengdong on 12/1/15.
 * Description: TestTrafficArffService
 */

@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})

public class TestTrafficArffService {
    private static Logger logger = Logger.getLogger(TestTrafficArffService.class);
    @Resource
    private TrafficArffService trafficArffService = null;

    //@Test
    public void testGetTrafficArffById() {
        TrafficArff temp = trafficArffService.getTrafficArffById(1);
        logger.info("结果" + JSON.toJSONString(temp));
    }

    //@Test
    public void testSelectRemoteIpAndLinkCount() {
        List<RemoteIpAndInt> resultList = trafficArffService.selectRemoteIpAndLinkCount();
        for (RemoteIpAndInt temp : resultList) {
            logger.info(temp.toString());
        }
    }

    //@Test
    public void testSelectRemoteIpAndVisitFrequency() {
        List<RemoteIpAndInt> resultList = trafficArffService.selectRemoteIpAndVisitFrequency();
        for (RemoteIpAndInt temp : resultList) {
            logger.info(temp.toString());
        }
    }

    //    @Test
    public void testSelectRemoteIpAndUpTraffic() {
        List<RemoteIpAndInt> resultList = trafficArffService.selectRemoteIpAndUpTraffic();
        for (RemoteIpAndInt temp : resultList) {
            logger.info(temp.toString());
        }
    }

    //    @Test
    public void testSelectRemoteIpAndDownTraffic() {
        List<RemoteIpAndInt> resultList = trafficArffService.selectRemoteIpAndDownTraffic();
        for (RemoteIpAndInt temp : resultList) {
            logger.info(temp.toString());
        }
    }

    //    @Test
    public void testInsertRemoteIpAndLinkCount() {
        trafficArffService.insertRemoteIpAndLinkCount();
    }

    //    @Test
    public void testInsertRemoteIpAndVisitFrequency() {
        trafficArffService.insertRemoteIpAndVisitFrequency();
    }

    //    @Test
    public void testInsertRemoteIpAndUpTraffic() {
        trafficArffService.insertRemoteIpAndUpTraffic();
    }

    //    @Test
    public void testInsertRemoteIpAndDownTraffic() {
        trafficArffService.insertRemoteIpAndDownTraffic();
    }

    //    @Test
    public void testGetAllTrafficArff() {
        List<TrafficArff> resultList = trafficArffService.getAllTrafficArff();
        for (int i = 0; i < resultList.size(); i++) {
            logger.info(i + " -- " + resultList.get(i).toString());
        }
    }

    @Test
    public void testGenerateAnalyseData() {
        trafficArffService.generateAnalyseData(false);
    }
}
