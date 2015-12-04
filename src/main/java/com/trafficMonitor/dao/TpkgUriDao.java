package com.trafficMonitor.dao;

import com.trafficMonitor.model.TpkgUri;

public interface TpkgUriDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TpkgUri record);

    int insertSelective(TpkgUri record);

    TpkgUri selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TpkgUri record);

    int updateByPrimaryKey(TpkgUri record);
}