package com.trafficMonitor.model;

public class TrainData {
    private Integer id;

    private String targetIp;

    private String linkNum;

    private String visitFre;

    private String upDownTrafficPro;

    private String trafficTotal;

    private String trafficType;

    private String classifiedType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTargetIp() {
        return targetIp;
    }

    public void setTargetIp(String targetIp) {
        this.targetIp = targetIp == null ? null : targetIp.trim();
    }

    public String getLinkNum() {
        return linkNum;
    }

    public void setLinkNum(String linkNum) {
        this.linkNum = linkNum == null ? null : linkNum.trim();
    }

    public String getVisitFre() {
        return visitFre;
    }

    public void setVisitFre(String visitFre) {
        this.visitFre = visitFre == null ? null : visitFre.trim();
    }

    public String getUpDownTrafficPro() {
        return upDownTrafficPro;
    }

    public void setUpDownTrafficPro(String upDownTrafficPro) {
        this.upDownTrafficPro = upDownTrafficPro == null ? null : upDownTrafficPro.trim();
    }

    public String getTrafficTotal() {
        return trafficTotal;
    }

    public void setTrafficTotal(String trafficTotal) {
        this.trafficTotal = trafficTotal == null ? null : trafficTotal.trim();
    }

    public String getTrafficType() {
        return trafficType;
    }

    public void setTrafficType(String trafficType) {
        this.trafficType = trafficType == null ? null : trafficType.trim();
    }

    public String getClassifiedType() {
        return classifiedType;
    }

    public void setClassifiedType(String classifiedType) {
        this.classifiedType = classifiedType == null ? null : classifiedType.trim();
    }
}