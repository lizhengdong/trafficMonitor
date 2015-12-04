package com.trafficMonitor.utils;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.Id3;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.Utils;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.supervised.instance.Resample;
import weka.filters.unsupervised.attribute.Remove;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Created by timothy.pratama on 16-Sep-15.
 */
public class Util {

    private static String pathDataSet = "dataSet/";
    private static String pathSavedModel = "savedModel/";
    private static String pathClassifyResult = "classifiedInstance/";

    /**
     * Fungsi ini digunakan untuk membaca data set dengan format arff
     * @param namaFile Nama file data set dengan format arff
     * @return Instances dari data set
     */
    public static Instances readARFF(String namaFile)
    {
        try
        {
            Instances dataSet;
            ConverterUtils.DataSource dataSource = new ConverterUtils.DataSource(pathDataSet + namaFile);
            dataSet = dataSource.getDataSet();
            if(dataSet.classIndex() == -1)
            {
                dataSet.setClassIndex(dataSet.numAttributes()-1);
            }
            return dataSet;
        }

        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Fungsi ini digunakan untuk membaca data set dalam format csv
     * @param namaFile Nama file data set dengan format csv
     * @return Instances dari data set
     */
    public static Instances readCSV(String namaFile)
    {
        try
        {
            CSVLoader csvLoader = new CSVLoader();
            csvLoader.setSource(new File(pathDataSet + namaFile));
            Instances dataSet = csvLoader.getDataSet();
            if(dataSet.classIndex() == -1)
            {
                dataSet.setClassIndex(dataSet.numAttributes()-1);
            }
            return dataSet;
        }

        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Fungsi ini digunakan untuk membuang atribut ke X dari sebuah data set
     * @param dataSet Data set yang akan di-filter
     * @param attributeIndex Atribut yang akan dibuang dari data set (bernilai dari 1 sampai jumlah atribut)
     * @return Data set dengan atribut ke attributeIndex sudah dibuang
     */
    public static Instances removeAttribute(Instances dataSet, int attributeIndex)
    {
        try
        {
            String options[] = new String[2];
            options[0] = "-R";
            options[1] = String.valueOf(attributeIndex);
            Remove remove = new Remove();
            remove.setOptions(options);
            remove.setInputFormat(dataSet);
            Instances newDataSet = Filter.useFilter(dataSet,remove);
            return newDataSet;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Fungsi ini digunakan untuk melakukan resampling pada data set
     * @param dataSet Data set yang akan di-resampling
     * @return data set yang sudah di-resampling
     */
    public static Instances resampleDataSet(Instances dataSet)
    {
        try
        {
            Resample resample = new Resample();
            String filterOptions = "-B 0.0 -S 1 -Z 100.0";
            resample.setOptions(Utils.splitOptions(filterOptions));
            resample.setRandomSeed((int) System.currentTimeMillis());
            resample.setInputFormat(dataSet);
            Instances newDataSet = Filter.useFilter(dataSet,resample);
            return newDataSet;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Fungsi ini digunakan untuk melatih sebuah Classifier
     * @param dataSet Data yang digunakan untuk membuat Classifier
     * @param classifier Classifier yang akan dilatih
     * @return Classifier yang sudah dilatih dengan menggunakan data latih
     */
    public static Classifier buildClassifier(Instances dataSet, Classifier classifier)
    {
        try {
            classifier.buildClassifier(dataSet);
            return classifier;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fungsi ini digunakan untuk menguji model terhadap test set
     * @param classifier Classifier yang akan diuji
     * @param dataSet Data yang digunakan untuk membuat Classifier
     * @param testSet Data yang digunakan untuk menguji Classifier
     * @return Hasil evaluasi model
     */
    public static Evaluation testClassifier(Classifier classifier, Instances dataSet, Instances testSet)
    {
        try
        {
            Evaluation evaluation = new Evaluation(dataSet);
            evaluation.evaluateModel(classifier, testSet);
            return evaluation;
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fungsi untuk melakukan 10 folds cross-validation
     * @param dataSet Data latih yang akan digunakan untuk pengujian Classifier
     * @param untrainedClassifier Model Classifier yang akan diuji
     * @return Evaluasi hasil pengujian Classifier
     */
    public static Evaluation crossValidationTest(Instances dataSet, Classifier untrainedClassifier)
    {
        try
        {
            Evaluation eval = new Evaluation(dataSet);
            eval.crossValidateModel(untrainedClassifier, dataSet, 10, new Random(1));
            return eval;
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fungsi Ini digunakan untuk melakukan evaluasi berdasarkan percentage split
     * @param dataSet Data yang digunakan untuk training classifier
     * @param untrainedClassifier Classifier yang akan diuji
     * @param percentage Persen data yang akan diguanakan sebagai training set
     * @return Hasil evaluasi
     */
    public static Evaluation percentageSplit(Instances dataSet, Classifier untrainedClassifier, int percentage)
    {
        Instances data = new Instances(dataSet);
        data.randomize(new Random(1));

        int trainSize = Math.round(data.numInstances() * percentage / 100);
        int testSize = data.numInstances() - trainSize;
        Instances trainSet = new Instances(data, 0, trainSize);
        Instances testSet = new Instances(data, trainSize, testSize);

        try
        {
            untrainedClassifier.buildClassifier(trainSet);
            Evaluation eval = testClassifier(untrainedClassifier, trainSet, testSet);
            return eval;
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fungsi ini digunakan untuk menyimpan Classifier ke file eksternal
     * @param filename Nama file untuk menyimpan model
     * @param classifier Classifier yang akan disimpan
     */
    public static void saveModel(String filename, Classifier classifier)
    {
        try
        {
            SerializationHelper.write(pathSavedModel + filename, classifier);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Fungsi ini digunakan untuk Classifier data dari file
     * @param filename nama file yang menyimpan model Classifier
     * @return Classifier
     */
    public static Classifier loadModel(String filename)
    {
        try
        {
            return (Classifier) SerializationHelper.read(pathSavedModel + filename);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void classify(String filename, Classifier classifier)
    {
        try
        {
            Instances input = readARFF(filename);
            input.setClassIndex(input.numAttributes()-1);
            for(int i=0; i<input.numInstances(); i++)
            {
                double classLabel = classifier.classifyInstance(input.instance(i));
                input.instance(i).setClassValue(classLabel);
                System.out.println("Instance: " + input.instance(i));
                System.out.println("Class: " + input.classAttribute().value((int)classLabel));
            }

            BufferedWriter writer = new BufferedWriter(
            new FileWriter(pathClassifyResult + "labeled." + filename));
            writer.write(input.toString());
            writer.newLine();
            writer.flush();
            writer.close();
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String [] args)
    {
        System.out.println("========== Reading File From ARFF ==========");
        Instances dataSet = Util.readARFF("weather.nominal.arff");
        System.out.println(dataSet.toString());
        System.out.println("Class Attribute: " + dataSet.attribute(dataSet.classIndex()));

        System.out.println("\n========== Resampling Data Set ==========");
        dataSet = Util.resampleDataSet(dataSet);
        System.out.println(dataSet.toString());

        System.out.println("\n========== Reading File From CSV ==========");
        dataSet = Util.readCSV("weather.nominal.csv");
        System.out.println(dataSet.toString());
        System.out.println("Class Attribute: " + dataSet.attribute(dataSet.classIndex()));

        System.out.println("\n========== Removing Class Attributes ==========");
        dataSet = readARFF("weather.nominal.arff");
        dataSet = Util.removeAttribute(dataSet,dataSet.numAttributes());
        System.out.println(dataSet.toString());

        System.out.println("\n========== Building Naive Bayes Classifier ==========");
        dataSet = Util.readARFF("weather.nominal.arff");
        Classifier classifier = Util.buildClassifier(dataSet, new NaiveBayes());
        System.out.println(classifier.toString());

        System.out.println("\n========== Building ID3 Classifier ==========");
        dataSet = Util.readARFF("weather.nominal.arff");
        classifier = Util.buildClassifier(dataSet, new Id3());
        System.out.println(classifier.toString());

        System.out.println("\n========== Building J48 Classifier ==========");
        dataSet = Util.readARFF("weather.nominal.arff");
        classifier = Util.buildClassifier(dataSet, new J48());
        System.out.println(classifier.toString());

        System.out.println("\n========== Testing Naive Bayes Classifier ==========");
        dataSet = Util.readARFF("weather.nominal.arff");
        Instances trainSet = readARFF("weather.nominal.test.arff");
        classifier = Util.buildClassifier(dataSet, new NaiveBayes());
        Evaluation eval = Util.testClassifier(classifier, dataSet, trainSet);
        System.out.println(eval.toSummaryString("\nResults\n===========\n", false));

        System.out.println("\n========== Testing ID3 Classifier ==========");
        classifier = Util.buildClassifier(dataSet, new Id3());
        eval = Util.testClassifier(classifier, dataSet, trainSet);
        System.out.println(eval.toSummaryString("\nResults\n===========\n", false));

        System.out.println("\n========== Testing J48 Classifier ==========");
        classifier = Util.buildClassifier(dataSet, new J48());
        eval = Util.testClassifier(classifier, dataSet, trainSet);
        System.out.println(eval.toSummaryString("\nResults\n===========\n", false));

        System.out.println("\n========== Cross Validation Naive Bayes Classifier ==========");
        eval = Util.crossValidationTest(dataSet, new NaiveBayes());
        System.out.println(eval.toSummaryString("\nResults\n===========\n", false));

        System.out.println("\n========== Cross Validation ID3 Classifier ==========");
        eval = Util.crossValidationTest(dataSet, new Id3());
        System.out.println(eval.toSummaryString("\nResults\n===========\n", false));

        System.out.println("\n========== Cross Validation J48 Classifier ==========");
        eval = Util.crossValidationTest(dataSet, new J48());
        System.out.println(eval.toSummaryString("\nResults\n===========\n", false));

        System.out.println("\n========== Percentage Split Naive Bayes Classifier 80% ==========");
        eval = Util.percentageSplit(dataSet, new NaiveBayes(), 80);
        System.out.println(eval.toSummaryString("\nResults\n===========\n", false));

        System.out.println("\n========== Percentage Split ID3 Classifier 80% ==========");
        eval = Util.percentageSplit(dataSet, new Id3(), 80);
        System.out.println(eval.toSummaryString("\nResults\n===========\n", false));

        System.out.println("\n========== Percentage Split J48 Classifier 80% ==========");
        eval = Util.percentageSplit(dataSet, new J48(), 80);
        System.out.println(eval.toSummaryString("\nResults\n===========\n", false));

        System.out.println("\n========== Testing Save Model ==========");
        classifier = Util.buildClassifier(dataSet, new Id3());
        Util.saveModel("id3_weather_nominal.model", classifier);

        System.out.println("\n========== Testing Load Model ==========");
        System.out.println(Util.loadModel("id3_weather_nominal.model").toString());

        System.out.println("\n========== Classifying Model ==========");
        Util.classify("weather.nominal.classify.arff", classifier);
    }
}