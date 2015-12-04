package com.trafficMonitor.utils.MyId3;

import com.trafficMonitor.utils.Util;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.Id3;
import weka.core.*;

import java.util.Enumeration;

/**
 * Created by timothy.pratama on 21-Sep-15.
 */
public class MyId3 extends Classifier {

    /**
     * Childs from an MyId3.MyId3 Node
     */
    private MyId3[] childs;

    /**
     * Attribute for splitting. NULL if leaf, otherwise will be the best attribute for splitting
     */
    private Attribute splitAttribute;

    /**
     * The class index for the current node if it is a leaf.
     */
    private double classValue;

    /**
     * Attribute for storing a leaf's class distribution for determining class value.
     */
    private double[] classDistribution;

    /**
     * Attribute for the instances' Class possible values
     */
    private Attribute classAttribute;

    /**
     * Attribute for storing the most common value in the given data set
     */
    private double mostCommonClassValue;


    /**
     * Build MyId3.MyId3 model with the given data set
     *
     * @param instances Data set for building the classifier
     * @throws Exception
     */
    @Override
    public void buildClassifier(Instances instances) throws Exception {
        /* Make sure that there are no continues value are entered! */
        getCapabilities().testWithFail(instances);

        /* Delete all instances with missing class */
        /* not useful for building tree */
        Instances data = new Instances(instances);
        data.deleteWithMissingClass();

        /* Find the most commmon value for the given data set */
        double mostCommonClassValue;
        double[] classDistribution = new double[data.numClasses()];
        Enumeration dataEnum = data.enumerateInstances();
        while (dataEnum.hasMoreElements()) {
            Instance instance = (Instance) dataEnum.nextElement();
            classDistribution[((int) instance.classValue())]++;
        }
        mostCommonClassValue = Utils.maxIndex(classDistribution);

        createTree(data, mostCommonClassValue);
    }

    /**
     * Create the decision tree based on the given data set
     *
     * @param dataSet
     * @param mostCommonClassValue
     */
    private void createTree(Instances dataSet, double mostCommonClassValue) {
        /* Several variables initialization */
        classAttribute = dataSet.classAttribute();
        classDistribution = new double[dataSet.numClasses()];

        /* Check if there is no instances for this node -> possibly missing examples case */
        if (dataSet.numInstances() == 0) {
            splitAttribute = null;
            classValue = mostCommonClassValue;
        } else /* dataSet.numInstances() > 0 */ {
            /* Compute each attribute information gain */
            double infoGains[] = new double[dataSet.numAttributes()];
            Enumeration attributeEnumeration = dataSet.enumerateAttributes();
            while (attributeEnumeration.hasMoreElements()) {
                Attribute attribute = (Attribute) attributeEnumeration.nextElement();
                double infoGain = computeInfoGain(dataSet, attribute);
                infoGains[attribute.index()] = infoGain;
            }

            splitAttribute = dataSet.attribute(Utils.maxIndex(infoGains));

            //This node is a leaf, the data sets only have 1 class.
            if (Utils.eq(infoGains[splitAttribute.index()], 0)) {
                splitAttribute = null;
                Enumeration instancesEnumeration = dataSet.enumerateInstances();
                while (instancesEnumeration.hasMoreElements()) {
                    Instance instance = (Instance) instancesEnumeration.nextElement();
                    classDistribution[((int) instance.classValue())]++;
                }
                Utils.normalize(classDistribution);
                classValue = Utils.maxIndex(classDistribution);

            } else /* Split the data by attribute, make new tree */ {
                Instances[] subDataSet = splitDataByAttribute(dataSet, splitAttribute);
                childs = new MyId3[splitAttribute.numValues()];
                for (int i = 0; i < splitAttribute.numValues(); i++) {
                    childs[i] = new MyId3();
                    childs[i].createTree(subDataSet[i], mostCommonClassValue);
                }
            }
        }
    }

    /**
     * Compute the infogain for the current data set for certain attribute
     *
     * @param dataSet   data set that is going to be used to compute the info gain
     * @param attribute attribute whose info gain are going to be computed
     * @return
     */
    private double computeInfoGain(Instances dataSet, Attribute attribute) {
        /* Info gain: Initial entropy - final entropy */
        /* Compute initial entropy */

        double initialEntropi = computeEntropy(dataSet);

        Instances[] subDataSet = splitDataByAttribute(dataSet, attribute);

        double[] entropies = new double[attribute.numValues()];
        for (int i = 0; i < attribute.numValues(); i++) {
            if (subDataSet[i].numInstances() > 0) {
                entropies[i] = computeEntropy(subDataSet[i]);
            } else {
                entropies[i] = 0;
            }
        }

        double infoGain = initialEntropi;
        for (int i = 0; i < attribute.numValues(); i++) {
            infoGain = infoGain - ((double) subDataSet[i].numInstances() / (double) dataSet.numInstances() * entropies[i]);
        }

        return infoGain;
    }

    /**
     * Compute the entropy from a given data set
     *
     * @param subDataSet Data set whose entropi to be computed
     * @return entropy from the data set
     */
    private double computeEntropy(Instances subDataSet) {
        /* Entropy = -(p1 log2 p1 + p2 log2 p2 + ...) */
        double totalInstances = subDataSet.numInstances();
        double totalClasses = subDataSet.numClasses();
        double[] classDistributions = new double[subDataSet.numClasses()];

        /* Compute each class number of instances */
        Enumeration instancesEnumeration = subDataSet.enumerateInstances();
        while (instancesEnumeration.hasMoreElements()) {
            Instance data = (Instance) instancesEnumeration.nextElement();
            classDistributions[((int) data.classValue())]++;
        }

        /* Compute probability for each class */
        for (int i = 0; i < totalClasses; i++) {
            classDistributions[i] = classDistributions[i] / totalInstances;
        }

        /* compute the p * log2 p for each class */
        for (int i = 0; i < totalClasses; i++) {
            classDistributions[i] = classDistributions[i] * log2(classDistributions[i]);
        }

        /* compute the sum of p * log2 p */
        double sum = 0;
        for (int i = 0; i < totalClasses; i++) {
            sum = sum + classDistributions[i];
        }

        /* return the -1 * (sum of p *log2 p) */
        /* == return -(p1 * log2 p1 + p2 * log2 p2 + ...) */
        if (sum != 0) {
            return -1 * sum;
        } else {
            return 0;
        }
    }

    private double log2(double a) {
        if (a != 0) {
            return Math.log(a) / Math.log(2);
        } else {
            return 0;
        }
    }

    /**
     * this method is use to divide a data set base on each value from an attribute
     *
     * @param dataSet   data Set to be splitted
     * @param attribute Attribute that is used to split
     * @return Instances that has been filtered based on the attribute's value
     */
    private Instances[] splitDataByAttribute(Instances dataSet, Attribute attribute) {
        /* Create several sub data set based on the possible values of an attribute */
        Instances[] subDataSet = new Instances[attribute.numValues()];
        for (int i = 0; i < attribute.numValues(); i++) {
            subDataSet[i] = new Instances(dataSet, dataSet.numInstances());
        }

        /* Cluster each data set with the same attribute value */
        Enumeration instancesEnumeration = dataSet.enumerateInstances();
        while (instancesEnumeration.hasMoreElements()) {
            Instance instance = (Instance) instancesEnumeration.nextElement();
            subDataSet[((int) instance.value(attribute))].add(instance);
        }

        /* Return the empty array from each data set */
        for (int i = 0; i < attribute.numValues(); i++) {
            subDataSet[i].compactify();
        }
        return subDataSet;
    }

    /**
     * Classify given instance
     *
     * @param instance Data that are going to be classified
     * @return Class Value for the instance
     * @throws Exception
     */
    @Override
    public double classifyInstance(Instance instance) throws Exception {
        if (instance.hasMissingValue()) {
            throw new NoSupportForMissingValuesException("MyId3.MyId3 can't support missing values!");
        }
        if (splitAttribute == null) {
            return classValue;
        } else {
            return childs[(int) instance.value(splitAttribute)].
                    classifyInstance(instance);
        }
    }


    /**
     * Return the class distribution for the current instance
     *
     * @param instance the instance that is going to be computed it's class distribution
     * @return the class distribution for this instance
     * @throws Exception
     */
    @Override
    public double[] distributionForInstance(Instance instance) throws Exception {
        if (instance.hasMissingValue()) {
            throw new NoSupportForMissingValuesException("MyId3.MyId3 can't support missing values!");
        }
        if (splitAttribute == null) {
            return classDistribution;
        } else {
            return childs[(int) instance.value(splitAttribute)].
                    distributionForInstance(instance);
        }
    }

    /**
     * Return the capabilty of MyId3.MyId3
     *
     * @return
     */
    @Override
    public Capabilities getCapabilities() {
        Capabilities result = super.getCapabilities();
        result.disableAll();

        // only available for nominal attributes (discrete, not continuous)
        result.enable(Capabilities.Capability.NOMINAL_ATTRIBUTES);

        // class
        result.enable(Capabilities.Capability.NOMINAL_CLASS);
        result.enable(Capabilities.Capability.MISSING_CLASS_VALUES);

        // instances
        result.setMinimumNumberInstances(0);

        return result;
    }

    @Override
    public String getRevision() {
        return super.getRevision();
    }

    /**
     * Return the string representation for the current level
     *
     * @param level Level that are going to be printed
     * @return
     */
    private String toString(int level) {

        StringBuffer text = new StringBuffer();

        if (splitAttribute == null) {
            if (Instance.isMissingValue(classValue)) {
                text.append(": null");
            } else {
                text.append(": " + classAttribute.value((int) classValue));
            }
        } else {
            for (int j = 0; j < splitAttribute.numValues(); j++) {
                text.append("\n");
                for (int i = 0; i < level; i++) {
                    text.append("|  ");
                }
                text.append(splitAttribute.name() + " = " + splitAttribute.value(j));
                text.append(childs[j].toString(level + 1));
            }
        }
        return text.toString();
    }

    /**
     * Return the string representation for MyId3.MyId3
     *
     * @return
     */
    @Override
    public String toString() {

        if ((classDistribution == null) && (childs == null)) {
            return "MyId3: No model built yet.";
        }
        return "MyId3\n\n" + toString(0);
    }

    public static void main(String[] args) throws Exception {
//        Instances dataSet = Util.readARFF("weather.nominal.arff");
//        Instances dataSet = Util.readARFF("weather.nominal.missing.examples.arff");
        Instances dataSet = Util.readARFF("traffic.analyse.arff");

        Evaluation myid3Evaluation = Util.crossValidationTest(dataSet, new MyId3());
        System.out.println(myid3Evaluation.toSummaryString("===== MyId3 Result =====", false));
        System.out.println(myid3Evaluation.toMatrixString());

        Evaluation id3Evaluation = Util.crossValidationTest(dataSet, new Id3());
        System.out.println(id3Evaluation.toSummaryString("===== Id3 Result =====", false));
        System.out.println(id3Evaluation.toMatrixString());

        Classifier id3 = new Id3();
        Classifier myid3 = new MyId3();

        id3.buildClassifier(dataSet);
        myid3.buildClassifier(dataSet);

        System.out.println("\n===== MyId3 Model =====\n" + myid3.toString());
        System.out.println("\n===== Id3 Model =====\n" + id3.toString());
    }
}
