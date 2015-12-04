package com.trafficMonitor.utils.MyJ48;

import weka.core.Statistics;
import weka.core.Utils;

/**
 * Created by timothy.pratama on 27-Sep-15.
 */
public class ErrorCalculator {
    public static double calculateError(double totalWeight, double numIncorect, double confidenceLevel) {
        if(numIncorect < 1)
        {
            double base = totalWeight * (1 - Math.pow(confidenceLevel, 1 / totalWeight));
            if (numIncorect == 0)
            {
                return base;
            }
            else
            {
                return base + numIncorect * (calculateError(totalWeight, 1, confidenceLevel) - base);
            }
        }
        else
        {
            if (Utils.grOrEq(numIncorect + 0.5, totalWeight))
            {
                return Math.max(totalWeight - numIncorect, 0);
            }
            else
            {
                double z = Statistics.normalInverse(1 - confidenceLevel);

                double f = (numIncorect + 0.5) / totalWeight;
                double r = (f + (z*z) / (2 * totalWeight) + z * Math.sqrt((f / totalWeight) - (f * f / totalWeight) + (z * z / (4 * totalWeight * totalWeight)))) / (1 + (z * z) / totalWeight);

                return (r * totalWeight) - numIncorect;
            }
        }
    }
}
