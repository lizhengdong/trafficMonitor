package com.trafficMonitor.dao;

import com.trafficMonitor.model.ClientList;

public interface ClientListDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ClientList record);

    int insertSelective(ClientList record);

    ClientList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ClientList record);

    int updateByPrimaryKey(ClientList record);
}