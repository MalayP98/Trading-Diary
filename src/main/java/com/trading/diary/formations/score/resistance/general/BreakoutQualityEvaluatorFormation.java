package com.trading.diary.formations.score.resistance.general;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.resistance.ResistanceBreakout;
import com.trading.diary.formations.score.AbstractFormationEvaluator;

/**
 * Scores breakout quality after the level is cleared. Weight: 10. RSI above 65 is preferred, RSI at or above 83 is treated as a do-not-buy zone, and stronger closes beyond resistance receive more credit.
 */
public class BreakoutQualityEvaluatorFormation extends AbstractFormationEvaluator<ResistanceBreakout> {

    private final double BREAKOUT_QUALITY_WEIGHTAGE = 10.0;

    private final double RSI_WEIGHT = 5.0;

    private final double BREAKOUT_PERCENTAGE_WEIGHT = 5.0;

    public BreakoutQualityEvaluatorFormation(AbstractFormationEvaluator<ResistanceBreakout> nextEvaluator) {
        super(nextEvaluator);
    }

    /**
     * Combines RSI context and breakout distance to judge whether the breakout shows healthy momentum without entering the do-not-buy zone.
     */
    @Override
    public double evaluate(ResistanceBreakout formation) {
        return scoreRsi(formation.getRsi()) + scoreBreakoutPercentage(formation.getBreakoutPercentage());
    }

    @Override
    protected double getWeightage() {
        return BREAKOUT_QUALITY_WEIGHTAGE;
    }

    @Override
    public FormationType getFormationType() {
        return null;
    }

    // Notes Session 17: "RSI should be above 65 at breakout"; "If RSI above 83–85 do not buy"
    private double scoreRsi(float rsi) {
        if (rsi >= 65 && rsi <= 80) return RSI_WEIGHT * 1.0;  // ideal: strong momentum, not overbought
        if (rsi >= 50 && rsi < 65) return RSI_WEIGHT * 0.5;   // below ideal threshold per notes
        if (rsi > 80 && rsi < 83) return RSI_WEIGHT * 0.3;    // approaching overbought territory
        return 0.0;                                             // < 50 or >= 83: too weak or do-not-buy zone
    }

    // Larger close above resistance = stronger conviction from buyers
    private double scoreBreakoutPercentage(float breakoutPercentage) {
        if (breakoutPercentage > 7) return BREAKOUT_PERCENTAGE_WEIGHT * 1.0;
        if (breakoutPercentage >= 3) return BREAKOUT_PERCENTAGE_WEIGHT * 0.8;
        if (breakoutPercentage >= 1) return BREAKOUT_PERCENTAGE_WEIGHT * 0.5;
        return BREAKOUT_PERCENTAGE_WEIGHT * 0.2;
    }
}
