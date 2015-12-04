package com.trafficMonitor.utils.MyJ48;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;

import java.util.Enumeration;

/**
 * Created by timothy.pratama on 24-Sep-15.
 */
public class J48ClassDistribution {

    /**
     * Weight each class per Subdataset
     */
    public double weightClassPerSubdataset[][];

    /**
     * Weight of each subdataset
     */
    public double weightPerSubDataset[];

    /**
     * Weight of each class
     */
    public double weightPerClass[];

    /**
     * Weight of all instances in this distribution
     */
    public double weightTotal;

    /**
     * Create distributions with one dataset (since it's the first time).
     * @param dataSet
     */
    public J48ClassDistribution(Instances dataSet)
    {
        weightTotal = 0;
        weightClassPerSubdataset = new double[1][dataSet.numClasses()];
        weightPerSubDataset = new double[1];
        weightPerClass = new double[dataSet.numClasses()];

        Enumeration instancesEnumeration = dataSet.enumerateInstances();
        while(instancesEnumeration.hasMoreElements())
        {
            Instance i = (Instance) instancesEnumeration.nextElement();
            addInstance(0, i);
        }
    }

    /**
     * Create a single subdataset from target distribution
     * @param targetDistribution
     * @return
     */
    public J48ClassDistribution(J48ClassDistribution targetDistribution)
    {
        weightTotal = targetDistribution.weightTotal;
        weightClassPerSubdataset = new double[1][targetDistribution.numClasses()];
        weightPerSubDataset = new double[1];
        weightPerClass = new double[targetDistribution.numClasses()];

        for(int i = 0; i < targetDistribution.numClasses(); i++)
        {
            weightClassPerSubdataset[0][i] = targetDistribution.weightPerClass[i];
            weightPerClass[i] = targetDistribution.weightPerClass[i];
        }
        weightPerSubDataset[0] = targetDistribution.weightTotal;
    }

    public J48ClassDistribution(double numberOfBranch, int numberOfClass) {
        weightTotal = 0;
        weightClassPerSubdataset = new double[(int) numberOfBranch][numberOfClass];
        weightPerSubDataset = new double[(int) numberOfBranch];
        weightPerClass = new double[numberOfClass];
    }

    public J48ClassDistribution(Instances dataSet, NodeType nodeType) {
        int subsetIndex;
        Instance instance;
        double [] weights;

        weightClassPerSubdataset = new double[nodeType.numOfSubsets][0];
        weightPerSubDataset = new double[nodeType.numOfSubsets];
        weightTotal = 0;
        weightPerClass = new double[dataSet.numClasses()];
        for(int i=0; i<nodeType.numOfSubsets; i++)
        {
            weightClassPerSubdataset[i] = new double[dataSet.numClasses()];
        }
        Enumeration instancesEnumeration = dataSet.enumerateInstances();
        while(instancesEnumeration.hasMoreElements())
        {
            instance = (Instance) instancesEnumeration.nextElement();
            subsetIndex = nodeType.getSubsetIndex(instance);
            if(subsetIndex  != -1)
            {
                addInstance(subsetIndex, instance);
            }
            else
            {
                weights = nodeType.getWeights(instance);
            }
        }
    }

    /**
     * return the number of subdataset in this distribution
     * @return
     */
    public int numSubDatasets()
    {
        return weightPerSubDataset.length;
    }

    /**
     * Return the total weight of the class distribution
     * @return
     */
    public double getTotalWeight()
    {
        return weightTotal;
    }

    /**
     * return the number of class in this distribution
     * @return
     */
    public int numClasses()
    {
        return weightPerClass.length;
    }

    /**
     * add single instance to subdataset
     * @param subDatasetIndex
     * @param instance
     */
    public void addInstance(int subDatasetIndex, Instance instance)
    {
        int classIndex = (int) instance.classValue();
        weightClassPerSubdataset[subDatasetIndex][classIndex] = weightClassPerSubdataset[subDatasetIndex][classIndex] + instance.weight();
        weightPerSubDataset[subDatasetIndex] = weightPerSubDataset[subDatasetIndex] + instance.weight();
        weightPerClass[classIndex] = weightPerClass[classIndex] +  instance.weight();
        weightTotal = weightTotal + instance.weight();
    }

    /**
     * Check whether this node has enough instances for splitting
     * @param minimalInstances
     * @return
     */
    public boolean isSplitable(double minimalInstances)
    {
        int counter = 0;
        for(int i=0; i<weightPerSubDataset.length; i++)
        {
            if(Utils.grOrEq(weightPerSubDataset[i], minimalInstances))
            {
                counter ++;
            }
        }
        return (counter > 1);
    }

    /**
     * compute the initial entropy
     * @return
     */
    private double calculateInitialEntropy()
    {
        double initEntropy = 0;
        for(int i=0; i<numClasses(); i++)
        {
            double p = weightPerClass[i]/weightTotal;
            initEntropy = initEntropy + (p * log2(p));
        }
        initEntropy = initEntropy * -1;
        return initEntropy;
    }

    /**
     * Calculate the information gain
     * @param instancesTotalWeight
     * @return
     */
    public double calculateInfoGain(double instancesTotalWeight)
    {
        /* initial entropy */
        /* entropy = -(p1 * log2 p1 + p2 * log2 p2 + ...) */
        double initialEntropy = 0;
        double unknownValues = 0;
        double unknownRate = 0;

        initialEntropy = calculateInitialEntropy();

        for (int i=0; i<numSubDatasets(); i++)
        {
            double finalEntropy = 0;
            for(int j=0; j<numClasses(); j++)
            {
                double p = 0;
                if(weightPerSubDataset[i] > 0)
                {
                    p = weightClassPerSubdataset[i][j] / weightPerSubDataset[i];
                }
                finalEntropy = finalEntropy + (p * log2(p));
            }
            finalEntropy = finalEntropy * -1;
            initialEntropy = initialEntropy - (weightPerSubDataset[i]/weightTotal*finalEntropy);
        }

        unknownValues = instancesTotalWeight-weightTotal;
        unknownRate = unknownValues/instancesTotalWeight;
        return ((1-unknownRate)*initialEntropy);
    }

    /**
     * Calculate the gain ratio
     * @param infoGain
     * @return
     */
    public double calculateGainRatio(double infoGain) {
        double splitInformation = 0;
        for(int i=0; i<numSubDatasets(); i++)
        {
            double p = weightPerSubDataset[i]/weightTotal;
            splitInformation = splitInformation - (p * log2(p));
        }
        return infoGain / splitInformation;
    }

    /**
     * return the result of log2
     * @param a
     * @return
     */
    private double log2(double a) {
        if(a < 1e-6)
        {
            return 0;
        }
        else
        {
            return Math.log(a) / Math.log(2);
        }
    }

    /**
     * Move instance from 1 subdataset to other subdataset
     * @param src
     * @param des
     * @param dataSet
     * @param startIndex
     * @param lastIndex
     */
    public void moveInstance(int src, int des, Instances dataSet, int startIndex, int lastIndex)
    {
        int classIndex;
        double weight;
        Instance data;

        for(int i=startIndex; i<lastIndex; i++)
        {
            data = dataSet.instance(i);
            classIndex = (int) data.classValue();
            weight = data.weight();
            weightClassPerSubdataset[src][classIndex] = weightClassPerSubdataset[src][classIndex] - weight;
            weightClassPerSubdataset[des][classIndex] = weightClassPerSubdataset[des][classIndex] + weight;
            weightPerSubDataset[src] = weightPerSubDataset[src] - weight;
            weightPerSubDataset[des] = weightPerSubDataset[des] + weight;
        }
    }

    /**
     * Add more than 1 instance to a subdataset
     * @param subDatasetIndex
     * @param dataSet
     * @param startIndex
     * @param lastIndex
     */
    public void addRange(int subDatasetIndex, Instances dataSet, int startIndex, int lastIndex)
    {
        double totalWeight = 0;
        int classIndex;
        Instance instance;

        for(int i=startIndex; i<lastIndex; i++)
        {
            instance = dataSet.instance(i);
            classIndex = (int) instance.classValue();
            totalWeight = totalWeight + instance.weight();
            weightClassPerSubdataset[subDatasetIndex][classIndex] += instance.weight();
            weightPerClass[classIndex] += instance.weight();
        }
        weightPerSubDataset[subDatasetIndex] += totalWeight;
        weightTotal += totalWeight;
    }

    /**
     * Print this distribution for debugging
     */
    public void print() {
        System.out.print("=====Weight per subdataset: ");
        for(double d : weightPerSubDataset)
        {
            System.out.print(d + " ");
        }
        System.out.println();

        System.out.print("=====Weight per class: ");
        for(double d : weightPerClass)
        {
            System.out.print(d + " ");
        }
        System.out.println();

        System.out.println("=====WeightClassPerSubdataset:");
        for(int i=0; i<numSubDatasets(); i++)
        {
            System.out.printf("Dataset[%d]: %f %f\n",i,weightClassPerSubdataset[i][0], weightClassPerSubdataset[i][1]);
        }

        System.out.println("=====Total Weight: " + weightTotal);
    }

    /**
     * Add instance with missing value using probability (weight)
     * @param dataset
     * @param attribute
     */
    public void addInstanceWithMissingValue(Instances dataset, Attribute attribute) {
        double[] valueProbabilities;
        double weight, newWeight;
        int classIndex;
        Instance instance;

        valueProbabilities = new double[numSubDatasets()];
        for (int i=0; i<numSubDatasets(); i++)
        {
            if(Utils.eq(weightTotal,0))
            {
                valueProbabilities[i] = 1.0 / weightTotal;
            }
            else
            {
                valueProbabilities[i] = weightPerSubDataset[i] / weightTotal;
            }
        }

        Enumeration instanceEnumeration = dataset.enumerateInstances();
        while(instanceEnumeration.hasMoreElements())
        {
            instance = (Instance) instanceEnumeration.nextElement();
            if(instance.isMissing(attribute))
            {
                classIndex = (int) instance.classValue();
                weight = instance.weight();
                weightPerClass[classIndex] += weight;
                weightTotal += weight;
                for(int i=0; i<numSubDatasets(); i++)
                {
                    newWeight = valueProbabilities[i] * weight;
                    weightClassPerSubdataset[i][classIndex] += newWeight;
                    weightPerSubDataset[i] += newWeight;
                }
            }
        }
    }

    /**
     * Return the number of incorrectly classified instance from dataSet
     * @param subdatasetIndex
     * @return
     */
    public double numIncorrect(int subdatasetIndex)
    {
        return weightPerSubDataset[subdatasetIndex]-numCorrect(subdatasetIndex);
    }

    /**
     * Return the number of correctly classified instances from subdataset
     * @param subdatasetIndex
     * @return
     */
    public final double numCorrect(int subdatasetIndex)
    {
        return weightClassPerSubdataset[subdatasetIndex][maxClass(subdatasetIndex)];
    }

    /**
     * return the maximum class in the subdataset
     * @param subdasetIndex
     * @return
     */
    public final int maxClass(int subdasetIndex) {

        double maxCount = 0;
        int maxIndex = 0;
        int i;

        if (Utils.gr(weightPerSubDataset[subdasetIndex],0)) {
            for (i=0;i<weightPerClass.length;i++)
                if (Utils.gr(weightClassPerSubdataset[subdasetIndex][i],maxCount)) {
                    maxCount = weightClassPerSubdataset[subdasetIndex][i];
                    maxIndex = i;
                }
            return maxIndex;
        }else
            return maxClass();
    }

    /**
     * return the maximum class in this distribution
     * @return
     */
    public final int maxClass() {

        double maxValue = 0;
        int maxIndex = 0;
        int i;

        for (i=0;i<weightPerClass.length;i++)
            if (Utils.gr(weightPerClass[i],maxValue)) {
                maxValue = weightPerClass[i];
                maxIndex = i;
            }

        return maxIndex;
    }

    /**
     * get the probability for a class in a subdataset
     * @param classIndex
     * @param subsetIndex
     * @return
     */
    public double prob(int classIndex, int subsetIndex) {
        if(Utils.gr(weightPerSubDataset[subsetIndex],0))
        {
            return weightClassPerSubdataset[subsetIndex][classIndex]/weightPerSubDataset[subsetIndex];
        }
        else
        {
            return prob(classIndex);
        }
    }

    /**
     * get the probability for a class
     * @param classIndex
     * @return
     */
    public double prob(int classIndex) {
        if(!Utils.eq(weightTotal,0))
        {
            return weightPerClass[classIndex]/weightTotal;
        }
        else
        {
            return 0;
        }
    }

    public double numIncorrect() {
        return weightTotal - numCorrect();
    }

    private double numCorrect() {
        return weightPerClass[maxClass()];
    }
}
