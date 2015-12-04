package com.trafficMonitor.model;

public class ClientList {
    private Integer id;

    private String cmac;

    private String showPkgs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCmac() {
        return cmac;
    }

    public void setCmac(String cmac) {
        this.cmac = cmac == null ? null : cmac.trim();
    }

    public String getShowPkgs() {
        return showPkgs;
    }

    public void setShowPkgs(String showPkgs) {
        this.showPkgs = showPkgs == null ? null : showPkgs.trim();
    }
}