package com.trafficMonitor.utils.MyJ48;

import weka.core.Instance;
import weka.core.Instances;

/**
 * Created by timothy.pratama on 24-Sep-15.
 */
public class NotSplitable extends NodeType{

    public NotSplitable(J48ClassDistribution distribution)
    {
        classDistribution = new J48ClassDistribution(distribution);
        numOfSubsets = 1;
    }

    @Override
    public int getSubsetIndex(Instance instance) {
        return 0;
    }

    @Override
    public double[] getWeights(Instance instance) {
        return null;
    }

    @Override
    public final String leftSide(Instances instances){

        return "";
    }

    @Override
    public final String rightSide(int index, Instances instances){

        return "";
    }
}
