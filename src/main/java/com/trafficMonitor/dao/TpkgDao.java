package com.trafficMonitor.dao;

import com.trafficMonitor.model.Tpkg;

public interface TpkgDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Tpkg record);

    int insertSelective(Tpkg record);

    Tpkg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Tpkg record);

    int updateByPrimaryKey(Tpkg record);
}