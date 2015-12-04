package com.trafficMonitor.model;

public class TrafficArff {
    private Integer id;

    private String targetIp;

    private Integer linkCount;

    private Integer visitFrequency;

    private Integer upTraffic;

    private Integer downTraffic;

    private String beginType;

    private String endType;

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
        this.targetIp = targetIp;
    }

    public Integer getLinkCount() {
        return linkCount;
    }

    public void setLinkCount(Integer linkCount) {
        this.linkCount = linkCount;
    }

    public Integer getVisitFrequency() {
        return visitFrequency;
    }

    public void setVisitFrequency(Integer visitFrequency) {
        this.visitFrequency = visitFrequency;
    }

    public Integer getUpTraffic() {
        return upTraffic;
    }

    public void setUpTraffic(Integer upTraffic) {
        this.upTraffic = upTraffic;
    }

    public Integer getDownTraffic() {
        return downTraffic;
    }

    public void setDownTraffic(Integer downTraffic) {
        this.downTraffic = downTraffic;
    }

    public String getBeginType() {
        return beginType;
    }

    public void setBeginType(String beginType) {
        this.beginType = beginType == null ? null : beginType.trim();
    }

    public String getEndType() {
        return endType;
    }

    public void setEndType(String endType) {
        this.endType = endType == null ? null : endType.trim();
    }

    @Override
    public String toString() {
        return "TrafficArff{" +
                "id=" + id +
                ", targetIp='" + targetIp + '\'' +
                ", linkCount=" + linkCount +
                ", visitFrequency=" + visitFrequency +
                ", upTraffic='" + upTraffic + '\'' +
                ", downTraffic='" + downTraffic + '\'' +
                ", beginType='" + beginType + '\'' +
                ", endType='" + endType + '\'' +
                '}';
    }
}