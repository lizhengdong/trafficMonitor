package com.trafficMonitor.utils.ManualDT;

/**
 * Created by lizhengdong on 15/10/19.
 * Author: lizhengdong
 * MailBox: 837718866@qq.com
 * Update by lizhengdong on 15/10/19.
 * Description: ManualDecisionTree，用来进行分类的决策树文件
 */
public class ManualDecisionTree {
   /* private static final double MUCH_URL_ENTROPY = 0.2442;//E(URL二级链接数多)
    private static final double LESS_URL_ENTROPY = 0.4515;//E(URL二级链接数少)
    private static final double LARGE_TRAFFIC_PROPORTION_ENTROPY = 0.2764;//E(上下行流量比大)
    private static final double SMALL_TRAFFIC_PROPORTION_ENTROPY = 0.2922;//E(上下行流量比小)
    private static final double LARGE_TRAFFIC_TOTAL_ENTROPY = 0.2764;//E(总数据流量大)
    private static final double SMALL_TRAFFIC_ENTROPY = 0.2922;//E(总数据流量小)

    private static final double TRAFFIC_PROPORTION_GAIN = 0.7137;//Gain(上下行流量比)
    private static final double TRAFFIC_TOTAL_GAIN = 0.6756;//Gain(总数据流量)
    private static final double URL_GAIN = 0.6521;//Gain(URL二级链接数)*/

    //定义内部节点
    private static class TreeNode<T>{
        private T data=null;//数据部分
        private TreeNode lchild;//左节点的引用
        private TreeNode rchild;//右节点的引用
        protected TreeNode(){

        }
        protected TreeNode(T data,TreeNode lchild,TreeNode rchild){
            this.data=data;
            this.lchild=lchild;
            this.rchild=rchild;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public TreeNode getLchild() {
            return lchild;
        }

        public void setLchild(TreeNode lchild) {
            this.lchild = lchild;
        }

        public TreeNode getRchild() {
            return rchild;
        }

        public void setRchild(TreeNode rchild) {
            this.rchild = rchild;
        }
    }

    private static TreeNode initDecisionTree(){
        TreeNode root =null;//树根
        TreeNode tpNode=null;//上下行流量比
        TreeNode zcapp=null;//正常app
        TreeNode mmapp=null;//木马app
        TreeNode urlAmount=null;//url链接数
        TreeNode trafficTotal=null;//总数据流量
        TreeNode trafficProportion=null;//上下行流量比

        TreeNode tpnode=new TreeNode();
        tpnode.setData("TRAFFIC_PROPORTION_GAIN");
        root=tpnode;

        //通过中序遍历的顺序建立二叉树
        zcapp=new TreeNode("正常APP",null,null);
        mmapp=new TreeNode("木马APP",null,null);
        urlAmount=new TreeNode("二级链接数",zcapp,mmapp);

        return root;

    }
}
