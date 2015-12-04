package com.trafficMonitor.dao;

import com.trafficMonitor.model.PkgUri;

public interface PkgUriDao {
    int deleteByPrimaryKey(Integer id);

    int insert(PkgUri record);

    int insertSelective(PkgUri record);

    PkgUri selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PkgUri record);

    int updateByPrimaryKey(PkgUri record);
}