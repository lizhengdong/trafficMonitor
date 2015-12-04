package com.trafficMonitor.model;

/**
 * Created by lizhengdong on 12/1/15.
 * Author: lizhengdong
 * MailBox: 837718866@qq.com
 * Update by lizhengdong on 12/1/15.
 * Description: RemoteIpAndInt
 */
public class RemoteIpAndInt {
    String remoteIp;
    Integer intVarible;

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public Integer getIntVarible() {
        return intVarible;
    }

    public void setIntVarible(Integer intVarible) {
        this.intVarible = intVarible;
    }

    @Override
    public String toString() {
        return "RemoteIpAndInt{" +
                "remoteIp='" + remoteIp + '\'' +
                ", intVarible=" + intVarible +
                '}';
    }
}
