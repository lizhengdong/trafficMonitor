package com.trafficMonitor.dao;

import com.trafficMonitor.model.RemoteIpAndInt;
import com.trafficMonitor.model.TrafficArff;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TrafficArffDao {

    List<TrafficArff> selectAll();

    int deleteByPrimaryKey(Integer id);

    int insert(TrafficArff record);

    int insertSelective(TrafficArff record);

    TrafficArff selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TrafficArff record);

    int updateByPrimaryKey(TrafficArff record);

    List<RemoteIpAndInt> selectRemoteIpAndLinkCount();

    List<RemoteIpAndInt> selectRemoteIpAndVisitFrequency();

    List<RemoteIpAndInt> selectRemoteIpAndUpTraffic();

    List<RemoteIpAndInt> selectRemoteIpAndDownTraffic();

    int updateVisitFrequencyByRemoteIp(RemoteIpAndInt remoteIpAndInt);

    int updateUpTrafficByRemoteIp(RemoteIpAndInt remoteIpAndInt);

    int updateDownTrafficByRemoteIp(RemoteIpAndInt remoteIpAndInt);
}