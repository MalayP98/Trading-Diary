package com.trading.diary.scale;

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

    public double compute(double x) {
        return asymptote / (1 + Math.exp(-smoothness * (x - steepGrowthCenter)));
    }
}
