package com.trading.diary.scale;

public record Sigmoid(double smoothness, double asymptote, double steepGrowthCenter) {

    public double compute(double x) {
        return asymptote / (1 + Math.exp(-smoothness * (x - steepGrowthCenter)));
    }
}
