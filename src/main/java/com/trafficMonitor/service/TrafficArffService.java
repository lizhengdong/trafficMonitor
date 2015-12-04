package com.trafficMonitor.service;

import com.trafficMonitor.model.RemoteIpAndInt;
import com.trafficMonitor.model.TrafficArff;

import java.util.List;

/**
 * Created by lizhengdong on 12/1/15.
 * Author: lizhengdong
 * MailBox: 837718866@qq.com
 * Update by lizhengdong on 12/1/15.
 * Description: TrafficArffService
 */

public interface TrafficArffService {

    public List<TrafficArff> getAllTrafficArff();

    public TrafficArff getTrafficArffById(Integer id);

    public List<RemoteIpAndInt> selectRemoteIpAndLinkCount();

    public List<RemoteIpAndInt> selectRemoteIpAndVisitFrequency();

    public List<RemoteIpAndInt> selectRemoteIpAndUpTraffic();

    public List<RemoteIpAndInt> selectRemoteIpAndDownTraffic();

    public void insertRemoteIpAndLinkCount();

    public void insertRemoteIpAndVisitFrequency();

    public void insertRemoteIpAndUpTraffic();

    public void insertRemoteIpAndDownTraffic();

    public void generateAnalyseData(boolean writeToDataBase);
}
