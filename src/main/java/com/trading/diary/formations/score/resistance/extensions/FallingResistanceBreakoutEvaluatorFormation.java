package com.trading.diary.formations.score.resistance.extensions;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.resistance.extension.FallingResistanceBreakout;
import com.trading.diary.formations.score.AbstractFormationEvaluator;
import com.trading.diary.scale.Sigmoid;
import com.trading.diary.scale.SteepGrowthCenterCalculator;

/**
 * Scores breakout rules unique to falling-resistance breakouts. Weight: 50. The setup requires a prior uptrend, prefers a moderate 5-20° falling line, and rewards larger price compression into the breakout.
 */
public class FallingResistanceBreakoutEvaluatorFormation extends AbstractFormationEvaluator<FallingResistanceBreakout> {

    // Prior uptrend is mandatory for FATHER (Session 10: "For FATHER uptrend is mandatory but for MOTHER it is not")
    private final double PRIOR_UPTREND_WEIGHTAGE = 30.0;

    private final double ANGLE_WEIGHTAGE = 10.0;

    private final double PRICE_DIFF_WEIGHTAGE = 10.0;

    // At 15% price drop from resistance start to breakout, score reaches 60% of max
    private final double PRICE_DIFF_SIGMOID_SMOOTHNESS = 0.3;
    private final Sigmoid PRICE_DIFF_SIGMOID = new Sigmoid(
            PRICE_DIFF_SIGMOID_SMOOTHNESS,
            PRICE_DIFF_WEIGHTAGE,
            SteepGrowthCenterCalculator.getByPercentageOfAsymptote(PRICE_DIFF_SIGMOID_SMOOTHNESS, 60.0, 15.0)
    );

    public FallingResistanceBreakoutEvaluatorFormation(AbstractFormationEvaluator<? super FallingResistanceBreakout> nextEvaluator) {
        super(nextEvaluator);
    }

    /**
     * Applies the FATHER-specific rules: prior uptrend is mandatory, moderate trendline angles score best, and deeper compression into the breakout earns more credit.
     */
    @Override
    public double evaluate(FallingResistanceBreakout formation) {
        return scorePriorUptrend(formation.isPriorUptrend())
                + scoreAngle(formation.getAngle())
                + scorePriceDiff(formation.getPriceDiffPercentage());
    }

    @Override
    protected double getWeightage() {
        return PRIOR_UPTREND_WEIGHTAGE + ANGLE_WEIGHTAGE + PRICE_DIFF_WEIGHTAGE;
    }

    @Override
    public FormationType getFormationType() {
        return FormationType.FALLING_RESISTANCE_BREAKOUT;
    }

    // Mandatory for FATHER: without prior uptrend the setup lacks the required context
    private double scorePriorUptrend(boolean priorUptrend) {
        return priorUptrend ? PRIOR_UPTREND_WEIGHTAGE : 0.0;
    }

    // Notes: "not very steep, max 4 o'clock (~30°)" — moderate slope is ideal; too flat or too steep are weaker
    private double scoreAngle(float angle) {
        if (angle < 5) return ANGLE_WEIGHTAGE * 0.2;   // too flat, barely a falling line
        if (angle <= 20) return ANGLE_WEIGHTAGE * 1.0; // ideal moderate slope
        return ANGLE_WEIGHTAGE * 0.5;                   // 20–30°: steeper, still acceptable but not ideal
    }

    // Larger price compression from resistance start to breakout = deeper reversal potential
    private double scorePriceDiff(float priceDiffPercentage) {
        return PRICE_DIFF_SIGMOID.compute(priceDiffPercentage);
    }
}
