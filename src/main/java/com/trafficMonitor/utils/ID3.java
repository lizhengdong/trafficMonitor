package com.trafficMonitor.utils;

/**
 * Created by lizhengdong on 12/3/15.
 * Author: lizhengdong
 * MailBox: 837718866@qq.com
 * Update by lizhengdong on 12/3/15.
 * Description: OlderID3
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class ID3 {
    private ArrayList<String> attribute = new ArrayList<String>(); // 存储属性的名称
    private ArrayList<ArrayList<String>> attributevalue = new ArrayList<ArrayList<String>>(); // 存储每个属性的取值
    private ArrayList<String[]> data = new ArrayList<String[]>();// 原始数据
    int decatt; // 决策变量在属性集中的索引
    public static final String patternString = "@attribute(.*)[{](.*?)[}]";

    Document xmldoc;
    Element root;

    public ID3() {
        xmldoc = DocumentHelper.createDocument();
        root = xmldoc.addElement("root");
        root.addElement("DecisionTree").addAttribute("value", "null");
    }

    public static void main(String[] args) {
        ID3 inst = new ID3();
        inst.readARFF(new File("/Users/lizhengdong/Desktop/test.traffic.analyse.arff"));
        inst.setDec("trafficType");
        LinkedList<Integer> attributeIndexList = new LinkedList<Integer>();
        for (int i = 0; i < inst.attribute.size(); i++) {
            if (i != inst.decatt)
                attributeIndexList.add(i);//存放用来进行决策的属性项的索引
        }
        ArrayList<Integer> dataIndexList = new ArrayList<Integer>();
        for (int i = 0; i < inst.data.size(); i++) {
            dataIndexList.add(i);//存放数据data的索引
        }
        System.out.println("attributeList size:" + attributeIndexList.size());
        System.out.println("dataList size:" + dataIndexList.size());
        inst.buildDT("DecisionTree", "null", dataIndexList, attributeIndexList);
        inst.writeXML("/Users/lizhengdong/Desktop/dt.xml");
        return;
    }

    //读取arff文件，给attribute、attributevalue、data赋值
    public void readARFF(File file) {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            Pattern pattern = Pattern.compile(patternString);
            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    attribute.add(matcher.group(1).trim());
                    String[] values = matcher.group(2).split(",");
                    ArrayList<String> al = new ArrayList<String>(values.length);
                    for (String value : values) {
                        al.add(value.trim());
                    }
                    attributevalue.add(al);
                } else if (line.startsWith("@data")) {
                    while ((line = br.readLine()) != null) {
                        if (line.trim().length() < 2)
                            continue;
                        String[] row = line.split(",");
                        data.add(row);
                    }
                } else {
                    continue;
                }
            }
            br.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    //设置决策变量
    public void setDec(int n) {
        if (n < 0 || n >= attribute.size()) {
            System.err.println("决策变量指定错误。");
            System.exit(2);
        }
        decatt = n;
    }

    public void setDec(String name) {
        int n = attribute.indexOf(name);
        setDec(n);
    }

    //给一个样本（数组中是各种情况的计数），计算它的熵
    public double getEntropy(int[] arr) {
        //计算信息熵
        /*double entropy = 0.0;
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];//先计算出sum
        }
        if (sum == 0) {
            System.out.println("sum为0!");
            System.exit(1);
        }
        for (int i = 0; i < arr.length; i++) {
            entropy -= (arr[i] / sum) * (Math.log((arr[i] + Double.MIN_VALUE) / sum) / Math.log(2));
            System.out.println("entropy=" + entropy);
        }
        return entropy;*/
        double entropy = 0.0;
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            entropy -= arr[i] * Math.log(arr[i] + Double.MIN_VALUE) / Math.log(2);
            sum += arr[i];
        }
        entropy += sum * Math.log(sum + Double.MIN_VALUE) / Math.log(2);
        entropy /= sum;

        return entropy;
    }

    //给一个样本数组及样本的算术和，计算它的熵
    /*public double getEntropy(int[] arr, int sum) {
        double entropy = 0.0;
        for (int i = 0; i < arr.length; i++) {
            entropy -= arr[i] * Math.log(arr[i] + Double.MIN_VALUE) / Math.log(2);
        }
        entropy += sum * Math.log(sum + Double.MIN_VALUE) / Math.log(2);
        entropy /= sum;
        return entropy;
    }*/

    public boolean infoPure(ArrayList<Integer> dataIndexList) {
        //decatt 决策变量在属性集中的索引
        String value = data.get(dataIndexList.get(0))[decatt];//第一行数据集合中的决策变量取值。trafficType的yes
        for (int i = 1; i < dataIndexList.size(); i++) {
            String next = data.get(dataIndexList.get(i))[decatt];//遍历每一行数据集合中的决策变量取值
            //equals表示对象内容相同，==表示两个对象指向的是同一片内存
            if (!value.equals(next))
                return false;//如果有不同的值，也就是说发现trafficType为no
        }
        return true;//如果决策变量取值都是一样的yes，就返回true
    }

    // 给定原始数据的子集(dataIndexList中存储数据data各行的行号),当以第index个属性为节点时计算它的信息增益Information Gain
    public double calNodeInformationGain(ArrayList<Integer> dataIndexList, int index) {
        //index里面存储着
        int sum = dataIndexList.size();//总共有多少行数据
        double informationGain = 0.0;
        //attributevalue存储着各个决策变量的取值情况，也就是文件中@attribute后面{}里的内容
        int[][] info = new int[attributevalue.get(index).size()][];//二维数组的行数为该决策变量的可能取值数量
        for (int i = 0; i < info.length; i++)
            info[i] = new int[attributevalue.get(decatt).size()];//二位数组的列数为最终决策变量的可能取值数量
        int[] count = new int[attributevalue.get(index).size()];//一位数组的长度为为该决策变量的可能取值数量
        for (int i = 0; i < sum; i++) {
            //逐行遍历所有数据
            int n = dataIndexList.get(i);//取出行的标号
            String nodevalue = data.get(n)[index];//从存储数据的数组中，取出第n行，列为指定属性列的数据。
            int nodeind = attributevalue.get(index).indexOf(nodevalue);//在属性集合中找到第index行，取值为nodevalue的序号
            /*
                例如，现在找linkNum的可能结果manyLN，fewLN,noneLN的信息熵。先找出它们对结果的影响次数。
                index指的是决策变量的序号，也就是linkNum、upDownTrafficPro等这些序号，如果是linkNum的话就是0
                nodevalue就可以是多行data数据中第n行，第0列，也就是manyLN
                nodeind就可以是LinkNum中manyLN的序号，也就是0
                attributevalue中存放着各个属性列，包括各个属性列的取值结果，是个包含着ArrayList(各个决策变量的取值情况)的ArrayList
             */
            count[nodeind]++;//统计数组中该行累加，也就是该属性对结果产生影响的次数。例如manyLN对结果产生影响的次数。
            /*
                一维数组info的例子：

                manyLN（6）   fewLN(9)    noneLN(3)

                和下面二维数组info对应。
            */
            String decvalue = data.get(n)[decatt];//这是该条data数据结果的值，如yes,或no
            int decind = attributevalue.get(decatt).indexOf(decvalue);//结果值在attributevalue中决策结果属性List中的编号
            info[nodeind][decind]++;//找到该二位数组中对结果影响的那个值进行增加。
            /*
                二维数组info的例子：

                           yes     no
                manyLN     1       5
                fewLN      6       3
                noneLN     2       1
             */
        }
        //计算该决策变量列，例如linkNum的信息增益Information Gain。
        double sumSmpvDevSmpMupEntroy = 0.0;
        for (int i = 0; i < info.length; i++) {
            double entropy = getEntropy(info[i]);//先计算该决策项的取值情况的信息熵
            System.out.println("entropy=" + entropy);
            if (Double.isNaN(entropy)) {
                entropy = 0.0;//如果返回NAN,就将其置为0
            }
            sumSmpvDevSmpMupEntroy = sumSmpvDevSmpMupEntroy + count[i] / sum * entropy;

        }
        informationGain = 1 - sumSmpvDevSmpMupEntroy;
        return informationGain;
    }

    // 构建决策树
    public void buildDT(String name, String value, ArrayList<Integer> dataIndexList,
                        LinkedList<Integer> attributeIndexList) {
        Element ele = null;//xml节点
//        @SuppressWarnings("unchecked")
        List<Element> list = root.selectNodes("//" + name);//找到名字为DecisionTree的dom节点
        Iterator<Element> iter = list.iterator();
        while (iter.hasNext()) {
            ele = iter.next();
            if (ele.attributeValue("value").equals(value))//找到值为null的节点
                break;
        }
        //如果决策变量取值都是一样的，则设置这个节点的结果就是决策变量的取值。
        if (infoPure(dataIndexList)) {
            ele.setText(data.get(dataIndexList.get(0))[decatt]);
            return;
        }
        int minIndex = -1;//设置最小索引。
        double minInformationGain = Double.MAX_VALUE;//设置最小的信息熵(Entropy)为double的最大值
        for (int i = 0; i < attributeIndexList.size(); i++) {
            //对属性列进行遍历，属性列里面包含（linkNum,upDownTrafficPro,visitFre,trafficTotal,trafficType）
            //其中decatt是trafficType的下标，也就是4
            if (i == decatt)
                continue;//不对最终的决策变量求信息熵
            //求各个决策变量的信息熵，决策变量包括linkNum，upDownTrafficPro，visitFre，trafficTotal
            double informationGain = Double.MAX_VALUE;
            informationGain = calNodeInformationGain(dataIndexList, attributeIndexList.get(i));
            if (informationGain < minInformationGain) {
                //如果小于目前最小的信息熵
                minIndex = attributeIndexList.get(i);//刷新最小的信息熵的决策变量的下标。
                minInformationGain = informationGain;//刷新最小的信息熵的值
            }
        }
        if (minIndex == -1) {
            //如果此时minIndex还是-1
            return;
        }
        String nodeName = attribute.get(minIndex);//找到当前信息熵最小的节点的名字，例如visitFre
        attributeIndexList.remove(new Integer(minIndex));//将该信息熵最小的决策变量的下标从决策变量下标集合中去除。
        ArrayList<String> attvalues = attributevalue.get(minIndex);//将信息熵最小的决策变量的值取出。
        for (String val : attvalues) {
            //对可能的取值情况进行遍历
            /*
                把决策变量的可能情况，添加到xml中的该dom节点下。
                例如linkNum的可能情况 manyLN, fewLN, noneLN 。
             */
            ele.addElement(nodeName).addAttribute("value", val);
            ArrayList<Integer> al = new ArrayList<Integer>();
            for (int i = 0; i < dataIndexList.size(); i++) {
                //遍历data列表，找到第i行data的拥有最小信息熵的决策变量那列，看是否与当前决策变量取值相同，如manyLN
                //如果相同的话，就将其下标加入到数据列
                if (data.get(dataIndexList.get(i))[minIndex].equals(val)) {
                    al.add(dataIndexList.get(i));
                }
            }
            buildDT(nodeName, val, al, attributeIndexList);//进行递归循环。
        }
    }

    // 把xml写入文件
    public void writeXML(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists())
                file.createNewFile();
            FileWriter fw = new FileWriter(file);
            OutputFormat format = OutputFormat.createPrettyPrint(); // 美化格式
            XMLWriter output = new XMLWriter(fw, format);
            output.write(xmldoc);
            output.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}