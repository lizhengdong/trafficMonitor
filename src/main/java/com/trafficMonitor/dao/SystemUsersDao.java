package com.trafficMonitor.dao;

import com.trafficMonitor.model.SystemUsers;

public interface SystemUsersDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SystemUsers record);

    int insertSelective(SystemUsers record);

    SystemUsers selectByPrimaryKey(Integer id);

    int selectByName(String username);

    int selectByMail(String mailbox);

    String selectPasswordByName(String username);

    int updateByPrimaryKeySelective(SystemUsers record);

    int updateByPrimaryKey(SystemUsers record);
}