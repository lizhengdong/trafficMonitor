package com.trafficMonitor.utils.MyJ48;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;

import java.util.Enumeration;

/**
 * Created by timothy.pratama on 24-Sep-15.
 */
public class Splitable extends NodeType{
    /**
     * attribute that is going to be used for splitting
     */
    public Attribute splitAttribute;

    /**
     * minimal instances required for splitting an attribute
     */
    public double minimalInstances;

    /**
     * total weight (in this case instances) for this splitable
     */
    public double totalWeight;

    /**
     * The dataset that are going to be used to train this node
     */
    Instances dataset;

    /**
     * Split the dataset according to this value
     */
    public double splitPointValue;

    /**
     * This node information gain
     */
    public double infoGain;

    /**
     * This node gain ratio
     */
    public double gainRatio;

    /**
     * Number of branches created from this node
     */
    public int numberOfBranch;

    /**
     * Number of posible splits
     */
    public int numberOfSplitPoints;

    public Splitable(Attribute splitAttribute, double minimalInstances, double totalWeight)
    {
        this.splitAttribute = splitAttribute;
        this.minimalInstances = minimalInstances;
        this.totalWeight = totalWeight;
    }

    public void buildClassifier(Instances dataset)
    {
        this.dataset = dataset;
        numOfSubsets = 0;
        splitPointValue = Double.MAX_VALUE;
        infoGain = 0;
        gainRatio = 0;
        numberOfSplitPoints = 0;

        if(splitAttribute.isNominal())
        {
//            System.out.println("=====Nominal Attribute!");
            numberOfBranch = splitAttribute.numValues();
            numberOfSplitPoints = splitAttribute.numValues();
            processNominalAttribute();
        }
        else // attribute == numeric
        {
            numberOfBranch = 2;
            numberOfSplitPoints = 0;
            dataset.sort(splitAttribute);
            processNumericAttribute();
        }
    }

    private void processNominalAttribute()
    {
        classDistribution = new J48ClassDistribution(numberOfBranch, dataset.numClasses());
        Enumeration instanceEnumeration = dataset.enumerateInstances();
        while(instanceEnumeration.hasMoreElements())
        {
            Instance instance = (Instance) instanceEnumeration.nextElement();
            if(!instance.isMissing(splitAttribute))
            {
                classDistribution.addInstance((int) instance.value(splitAttribute), instance);
            }
        }

        if(classDistribution.isSplitable(minimalInstances))
        {
            numOfSubsets = numberOfBranch;
            infoGain = classDistribution.calculateInfoGain(totalWeight);
            gainRatio = classDistribution.calculateGainRatio(infoGain);
        }
    }

    private void processNumericAttribute()
    {
        int numInstances;
        int next = 1;
        int last = 0;
        int splitIndex = -1;
        double currentInfoGain;
        double currentGainRatio;
        double subsetMinInstances;
        Instance instance;
        int i;

        classDistribution = new J48ClassDistribution(2, dataset.numClasses());
        Enumeration instancesEnumeration = dataset.enumerateInstances();
        i=0;
        while(instancesEnumeration.hasMoreElements())
        {
            instance = (Instance) instancesEnumeration.nextElement();
            if(!instance.isMissing(splitAttribute))
            {
                classDistribution.addInstance(1, instance);
                i++;
            }
        }

        numInstances = i;

        // compute the minimal instances in each subset
        subsetMinInstances = 0.1*(classDistribution.getTotalWeight() / (double) classDistribution.numClasses());
        if(Utils.smOrEq(subsetMinInstances, minimalInstances))
        {
            subsetMinInstances = minimalInstances;
        }
        else
        {
            if(Utils.gr(subsetMinInstances,25))
            {
                subsetMinInstances = 25;
            }
        }

        /* Check if there are enough instances for splitting */
        if(Utils.sm(numInstances, subsetMinInstances*2))
        {
            return;
        }

        // find all possible split points!
        while(next < numInstances)
        {
            if(dataset.instance(next-1).value(splitAttribute) + 0.00001 < dataset.instance(next).value(splitAttribute))
            {
                classDistribution.moveInstance(1,0,dataset,last,next);
                if(Utils.grOrEq(classDistribution.weightPerSubDataset[0],subsetMinInstances) &&
                   Utils.grOrEq(classDistribution.weightPerSubDataset[1],subsetMinInstances))
                {
                    currentInfoGain = classDistribution.calculateInfoGain(totalWeight);
                    currentGainRatio = classDistribution.calculateGainRatio(currentInfoGain);
                    if(Utils.grOrEq(currentGainRatio, gainRatio))
                    {
                        infoGain = currentInfoGain;
                        gainRatio = currentGainRatio;
                        splitIndex = next-1;
                    }
                    numberOfSplitPoints++;
                }
                last = next;
            }
            next++;
        }

        if(numberOfSplitPoints > 0)
        {
            infoGain = infoGain - (log2(numberOfSplitPoints / totalWeight));
            if(Utils.gr(infoGain,0))
            {
                numOfSubsets = 2;
                splitPointValue = (dataset.instance(splitIndex+1).value(splitAttribute) +
                                   dataset.instance(splitIndex).value(splitAttribute))/2;

                if(splitPointValue == dataset.instance(splitIndex + 1).value(splitAttribute))
                {
                    splitPointValue = dataset.instance(splitIndex).value(splitAttribute);
                }

                classDistribution = new J48ClassDistribution(2, dataset.numClasses());
                classDistribution.addRange(0, dataset, 0, splitIndex+1);
                classDistribution.addRange(1, dataset, splitIndex+1, numInstances);

                gainRatio = classDistribution.calculateGainRatio(infoGain);
            }
        }
    }

    private double log2(double a) {
        if(a != 0)
        {
            return Math.log(a) / Math.log(2);
        }
        else
        {
            return 0;
        }
    }

    public void setSplitPoint() {
        double newSplitPoint = Double.MAX_VALUE * -1;
        double temp;
        Instance instance;

        if(splitAttribute.isNumeric() && numOfSubsets > 1)
        {
            Enumeration instancesEnumeration = dataset.enumerateInstances();
            while (instancesEnumeration.hasMoreElements())
            {
                instance = (Instance) instancesEnumeration.nextElement();
                if(!instance.isMissing(splitAttribute))
                {
                    temp = instance.value(splitAttribute);
                    if(Utils.gr(temp,newSplitPoint) && Utils.smOrEq(temp, splitPointValue))
                    {
                        newSplitPoint = temp;
                    }
                }
            }
        }
        splitPointValue = newSplitPoint;
    }

    public void addInstanceWithMissingvalue() {
        addInstanceWithMissingValue(dataset, splitAttribute);
    }

    private void addInstanceWithMissingValue(Instances dataset, Attribute attribute)
    {
        classDistribution.addInstanceWithMissingValue(dataset, attribute);
    }

    @Override
    public int getSubsetIndex(Instance instance) {
        if(instance.isMissing(splitAttribute))
        {
            return -1;
        }
        else
        {
            if(splitAttribute.isNominal())
            {
                return (int) instance.value(splitAttribute);
            }
            else
            {
                if(Utils.smOrEq(instance.value(splitAttribute), splitPointValue))
                {
                    return 0;
                }
                else
                {
                    return 1;
                }
            }
        }
    }

    @Override
    public double[] getWeights(Instance instance) {
        double [] weights;
//        System.out.println("\nnum subset: " + numOfSubsets);
//        classDistribution.print();
        if(instance.isMissing(splitAttribute))
        {
            weights = new double [numOfSubsets];
            for(int i=0; i<numOfSubsets; i++)
            {
                weights[i] = classDistribution.weightPerSubDataset[i]/classDistribution.weightTotal;
            }
            return weights;
        }
        else
        {
            return null;
        }
    }

    @Override
    public final String leftSide(Instances data) {

        return splitAttribute.name();
    }

    public final String rightSide(int index,Instances data) {

        StringBuffer text;

        text = new StringBuffer();
        if (splitAttribute.isNominal())
            text.append(" = "+
                    splitAttribute.value(index));
        else
        if (index == 0)
            text.append(" <= "+
                    Utils.doubleToString(splitPointValue,6));
        else
            text.append(" > "+
                    Utils.doubleToString(splitPointValue,6));
        return text.toString();
    }
}
