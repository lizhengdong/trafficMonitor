package com.trafficMonitor.model;

import java.util.Date;

public class Pkg {
    private Integer id;

    private String nativeMac;

    private String remoteIp;

    private Integer remotePort;

    private Short protocolType;

    private Boolean flowDirectioin;

    private String flowAmount;

    private Date firstVisittime;

    private Date lastVisittime;

    private Date dateVisit;

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

    public Short getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(Short protocolType) {
        this.protocolType = protocolType;
    }

    public Boolean getFlowDirectioin() {
        return flowDirectioin;
    }

    public void setFlowDirectioin(Boolean flowDirectioin) {
        this.flowDirectioin = flowDirectioin;
    }

    public String getFlowAmount() {
        return flowAmount;
    }

    public void setFlowAmount(String flowAmount) {
        this.flowAmount = flowAmount == null ? null : flowAmount.trim();
    }

    public Date getFirstVisittime() {
        return firstVisittime;
    }

    public void setFirstVisittime(Date firstVisittime) {
        this.firstVisittime = firstVisittime;
    }

    public Date getLastVisittime() {
        return lastVisittime;
    }

    public void setLastVisittime(Date lastVisittime) {
        this.lastVisittime = lastVisittime;
    }

    public Date getDateVisit() {
        return dateVisit;
    }

    public void setDateVisit(Date dateVisit) {
        this.dateVisit = dateVisit;
    }
}