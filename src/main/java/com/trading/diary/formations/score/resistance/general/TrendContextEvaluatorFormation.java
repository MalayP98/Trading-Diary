package com.trading.diary.formations.score.resistance.general;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.resistance.ResistanceBreakout;
import com.trading.diary.formations.score.AbstractFormationEvaluator;

/**
 * Scores the broader context around a breakout. Weight: 10. Higher lows suggest accumulation, a prior uptrend favours continuation, and an all-time high removes overhead supply.
 */
public class TrendContextEvaluatorFormation extends AbstractFormationEvaluator<ResistanceBreakout> {

    private final double TREND_CONTEXT_WEIGHTAGE = 10.0;

    // Higher lows before breakout show accumulation and growing buying pressure
    private final double HIGHER_LOWS_SCORE = 4.0;

    // A prior uptrend means the breakout is a continuation, not a reversal against the main trend
    private final double PRIOR_UPTREND_SCORE = 4.0;

    // Breaking to a new all-time high removes all overhead supply — very bullish
    private final double ALL_TIME_HIGH_SCORE = 2.0;

    public TrendContextEvaluatorFormation(AbstractFormationEvaluator<ResistanceBreakout> nextEvaluator) {
        super(nextEvaluator);
    }

    /**
     * Rewards the contextual ingredients that make a breakout easier to trust: higher lows, prior uptrend, and absence of overhead supply at all-time highs.
     */
    @Override
    public double evaluate(ResistanceBreakout formation) {
        double score = 0.0;
        if (formation.isHigherLows()) score += HIGHER_LOWS_SCORE;
        if (formation.isPriorUptrend()) score += PRIOR_UPTREND_SCORE;
        if (formation.isAllTimeHigh()) score += ALL_TIME_HIGH_SCORE;
        return score;
    }

    @Override
    protected double getWeightage() {
        return TREND_CONTEXT_WEIGHTAGE;
    }

    @Override
    public FormationType getFormationType() {
        return null;
    }
}
