package com.trading.diary.scale;

/**
 * Small value object for the logistic curve used to convert support or resistance age into a bounded score. The asymptote is the maximum score, the steep-growth center controls where the curve accelerates, and the smoothness controls how abrupt that transition feels.
 */
public class Sigmoid {

    private final double smoothness;
    private final double asymptote;
    private final double steepGrowthCenter;

    public Sigmoid(double smoothness, double asymptote, double steepGrowthCenter) {
        this.smoothness = smoothness;
        this.asymptote = asymptote;
        this.steepGrowthCenter = steepGrowthCenter;
    }

    public double smoothness() { return smoothness; }
    public double asymptote() { return asymptote; }
    public double steepGrowthCenter() { return steepGrowthCenter; }

    /**
     * Evaluates the logistic curve at the supplied x value and returns a score bounded by the configured asymptote.
     */
    public double compute(double x) {
        return asymptote / (1 + Math.exp(-smoothness * (x - steepGrowthCenter)));
    }
}
