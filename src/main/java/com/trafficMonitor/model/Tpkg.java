package com.trafficMonitor.model;

import java.util.Date;

public class Tpkg {
    private Integer id;

    private String nativeMac;

    private String remoteIp;

    private Integer remotePort;

    private String protocolType;

    private Boolean flowDirection;

    private String flowAmount;

    private Date firstVisitTime;

    private Date lastVisitTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNativeMac() {
        return nativeMac;
    }

    public void setNativeMac(String nativeMac) {
        this.nativeMac = nativeMac == null ? null : nativeMac.trim();
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp == null ? null : remoteIp.trim();
    }

    public Integer getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(Integer remotePort) {
        this.remotePort = remotePort;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType == null ? null : protocolType.trim();
    }

    public Boolean getFlowDirection() {
        return flowDirection;
    }

    public void setFlowDirection(Boolean flowDirection) {
        this.flowDirection = flowDirection;
    }

    public String getFlowAmount() {
        return flowAmount;
    }

    public void setFlowAmount(String flowAmount) {
        this.flowAmount = flowAmount == null ? null : flowAmount.trim();
    }

    public Date getFirstVisitTime() {
        return firstVisitTime;
    }

    public void setFirstVisitTime(Date firstVisitTime) {
        this.firstVisitTime = firstVisitTime;
    }

    public Date getLastVisitTime() {
        return lastVisitTime;
    }

    public void setLastVisitTime(Date lastVisitTime) {
        this.lastVisitTime = lastVisitTime;
    }
}