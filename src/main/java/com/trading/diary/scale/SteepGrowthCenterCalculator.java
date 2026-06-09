package com.trading.diary.scale;

/**
 * Utility for solving the inverse sigmoid problem used by the evaluators: given a desired percentage of the asymptote at a known x value, it computes the steep-growth center needed to achieve that curve.
 */
public class SteepGrowthCenterCalculator {

    /**
     * Calculates the steep growth center of a sigmoid function based on the given percentage of asymptote.
     * Example, At x=20, the sigmoid reaches 80% of its asymptote.
     * @param smoothness the smoothness of the sigmoid function
     * @param percentageOfAsymptote the percentage of the asymptote (0-100)
     * @param at what x value the sigmoid reaches the given percentage of asymptote
     * @return the steep growth center
     */
    public static double getByPercentageOfAsymptote(double smoothness, double percentageOfAsymptote, double at) {
        percentageOfAsymptote /= 100.0;
        return at - 1/smoothness * Math.log(percentageOfAsymptote/(1-percentageOfAsymptote));
    }

}
