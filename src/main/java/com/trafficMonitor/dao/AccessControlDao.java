package com.trafficMonitor.dao;

import com.trafficMonitor.model.AccessControl;

public interface AccessControlDao {
    int deleteByPrimaryKey(Integer id);

    int insert(AccessControl record);

    int insertSelective(AccessControl record);

    AccessControl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccessControl record);

    int updateByPrimaryKey(AccessControl record);
}