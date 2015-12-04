package com.trafficMonitor.service.impl;

import com.trafficMonitor.dao.TrafficArffDao;
import com.trafficMonitor.dao.TrainDataDao;
import com.trafficMonitor.model.RemoteIpAndInt;
import com.trafficMonitor.model.TrafficArff;
import com.trafficMonitor.model.TrainData;
import com.trafficMonitor.service.TrafficArffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhengdong on 12/1/15.
 * Author: lizhengdong
 * MailBox: 837718866@qq.com
 * Update by lizhengdong on 12/1/15.
 * Description: TrafficArffServiceImpl
 */
@Service("trafficArffService")
public class TrafficArffServiceImpl implements TrafficArffService {
    @Autowired
    private TrafficArffDao trafficArffDao;

    @Autowired
    private TrainDataDao trainDataDao;

    @Override
    public List<TrafficArff> getAllTrafficArff() {
        return trafficArffDao.selectAll();
    }

    @Override
    public TrafficArff getTrafficArffById(Integer id) {
        return trafficArffDao.selectByPrimaryKey(id);
    }

    @Override
    public List<RemoteIpAndInt> selectRemoteIpAndLinkCount() {
        return trafficArffDao.selectRemoteIpAndLinkCount();
    }

    @Override
    public List<RemoteIpAndInt> selectRemoteIpAndVisitFrequency() {
        return trafficArffDao.selectRemoteIpAndVisitFrequency();
    }

    @Override
    public List<RemoteIpAndInt> selectRemoteIpAndUpTraffic() {
        return trafficArffDao.selectRemoteIpAndUpTraffic();
    }

    @Override
    public List<RemoteIpAndInt> selectRemoteIpAndDownTraffic() {
        return trafficArffDao.selectRemoteIpAndDownTraffic();
    }

    @Override
    public void insertRemoteIpAndLinkCount() {
        List<RemoteIpAndInt> resultList = selectRemoteIpAndLinkCount();
        for (RemoteIpAndInt temp : resultList) {
            TrafficArff trafficArff = new TrafficArff();
            trafficArff.setTargetIp(temp.getRemoteIp());
            trafficArff.setLinkCount(temp.getIntVarible());
            trafficArffDao.insert(trafficArff);
        }
    }

    @Override
    public void insertRemoteIpAndVisitFrequency() {
        List<RemoteIpAndInt> resultList = selectRemoteIpAndVisitFrequency();
        for (RemoteIpAndInt temp : resultList) {
            trafficArffDao.updateVisitFrequencyByRemoteIp(temp);
        }
    }

    @Override
    public void insertRemoteIpAndUpTraffic() {
        List<RemoteIpAndInt> resultList = selectRemoteIpAndUpTraffic();
        for (RemoteIpAndInt temp : resultList) {
            trafficArffDao.updateUpTrafficByRemoteIp(temp);
        }
    }

    @Override
    public void insertRemoteIpAndDownTraffic() {
        List<RemoteIpAndInt> resultList = selectRemoteIpAndDownTraffic();
        for (RemoteIpAndInt temp : resultList) {
            trafficArffDao.updateDownTrafficByRemoteIp(temp);
        }
    }

    @Override
    public void generateAnalyseData(boolean writeToDataBase) {

        List<TrainData> resultTrainData = new ArrayList<TrainData>();
        List<TrafficArff> result = getAllTrafficArff();

        //计算样本的平均总流量
        TrafficArff firstTrafficArff = result.get(0);
        int avgTrafficAmount = firstTrafficArff.getUpTraffic() + firstTrafficArff.getDownTraffic();
        for (TrafficArff tempTraffArff : result) {
            int tempAmount = (tempTraffArff.getUpTraffic() == null ? 0 : tempTraffArff.getUpTraffic()) + (tempTraffArff.getDownTraffic() == null ? 0 : tempTraffArff.getDownTraffic());
            avgTrafficAmount = (avgTrafficAmount + tempAmount) / 2;
        }
        avgTrafficAmount = (int) (avgTrafficAmount * 1.5);//乘以一点5倍作为标准
        for (TrafficArff temp : result) {
            TrainData tempTrainData = new TrainData();
            tempTrainData.setTargetIp(temp.getTargetIp());
            if (temp.getLinkCount() > 4) {
                tempTrainData.setLinkNum("manyLN");
            } else if (temp.getLinkCount() <= 1) {
                tempTrainData.setLinkNum("noneLN");
            } else {
                tempTrainData.setLinkNum("fewLN");
            }

            if (temp.getVisitFrequency() > 4) {
                tempTrainData.setVisitFre("highVF");
            } else {
                tempTrainData.setVisitFre("lowVF");
            }

            if (temp.getDownTraffic() != 0) {
                double upDownTrafficProResult = temp.getUpTraffic() / temp.getDownTraffic();
                if (upDownTrafficProResult > 0.7097) {
                    tempTrainData.setUpDownTrafficPro("highUDTP");
                } else {
                    tempTrainData.setUpDownTrafficPro("lowUDTP");
                }
            } else {
                tempTrainData.setUpDownTrafficPro("maxUDTP");
            }
            int trafficTotal = temp.getUpTraffic() + temp.getDownTraffic();
            if (trafficTotal > avgTrafficAmount) {
                tempTrainData.setTrafficTotal("manyTT");
            } else {
                tempTrainData.setTrafficTotal("fewTT");
            }
            //对result做一个调整
            if (temp.getDownTraffic() == 0) {
                tempTrainData.setTrafficType("danger");
            } else if (temp.getDownTraffic() < temp.getUpTraffic() && temp.getLinkCount() < 5) {
                //上行>下行，链接数少
                if (temp.getVisitFrequency() < 5) {
                    tempTrainData.setTrafficType("danger");
                } else {
                    tempTrainData.setTrafficType("safe");
                }
            } else if (temp.getDownTraffic() > temp.getUpTraffic()) {
                if (tempTrainData.getTrafficTotal().equals("manyTT")){
                    if(temp.getVisitFrequency()<3){
                        tempTrainData.setTrafficType("danger");
                    }else{
                        tempTrainData.setTrafficType("safe");
                    }
                }else{
                    tempTrainData.setTrafficType("safe");
                }
            } else {
                tempTrainData.setTrafficType("safe");
            }


            //加入到集合中
            resultTrainData.add(tempTrainData);
        }
        //写到数据库中
        if (writeToDataBase == true) {
            for (TrainData temp : resultTrainData) {
                trainDataDao.insert(temp);
            }
        }

        //输出到文件
        File outputFile = new File("dataSet/traffic.analyse.arff");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            //写入文件头部分数据
            bufferedOutputStream.write("@relation traffic.analyse\n\n".getBytes());
            bufferedOutputStream.write("@attribute linkNum {manyLN, fewLN, noneLN}\n".getBytes());
            bufferedOutputStream.write("@attribute upDownTrafficPro {highUDTP, lowUDTP, maxUDTP}\n".getBytes());
            bufferedOutputStream.write("@attribute visitFre {highVF, lowVF}\n".getBytes());
            bufferedOutputStream.write("@attribute trafficTotal {manyTT, fewTT}\n".getBytes());
            bufferedOutputStream.write("@attribute trafficType {safe, danger}\n\n".getBytes());
            bufferedOutputStream.write("@data\n".getBytes());

            for (int i = 0; i < resultTrainData.size(); i++) {
                TrainData temp = resultTrainData.get(i);
                String line = temp.getLinkNum() + "," + temp.getUpDownTrafficPro() + "," + temp.getVisitFre() + "," + temp.getTrafficTotal() + "," + temp.getTrafficType() + "\r\n";
                bufferedOutputStream.write(line.getBytes());
            }
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
