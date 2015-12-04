package com.trafficMonitor.dao;

import com.trafficMonitor.model.Pkg;

public interface PkgDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Pkg record);

    int insertSelective(Pkg record);

    Pkg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Pkg record);

    int updateByPrimaryKey(Pkg record);
}