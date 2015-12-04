package com.trafficMonitor.dao;

import com.trafficMonitor.model.Uri;

public interface UriDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Uri record);

    int insertSelective(Uri record);

    Uri selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Uri record);

    int updateByPrimaryKey(Uri record);
}