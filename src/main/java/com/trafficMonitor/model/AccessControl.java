package com.trafficMonitor.model;

public class AccessControl {
    private Integer id;

    private String ip;

    private Integer port;

    private Short accessType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Short getAccessType() {
        return accessType;
    }

    public void setAccessType(Short accessType) {
        this.accessType = accessType;
    }
}