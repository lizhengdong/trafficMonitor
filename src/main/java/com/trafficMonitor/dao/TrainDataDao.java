package com.trafficMonitor.dao;

import com.trafficMonitor.model.TrainData;

public interface TrainDataDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TrainData record);

    int insertSelective(TrainData record);

    TrainData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TrainData record);

    int updateByPrimaryKey(TrainData record);
}