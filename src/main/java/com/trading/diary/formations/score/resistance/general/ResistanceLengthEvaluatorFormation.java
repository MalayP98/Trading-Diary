package com.trading.diary.formations.score.resistance.general;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.resistance.ResistanceBreakout;
import com.trading.diary.formations.score.AbstractFormationEvaluator;
import com.trading.diary.scale.Sigmoid;
import com.trading.diary.scale.SteepGrowthCenterCalculator;
import com.trading.diary.utils.emums.TimeFrame;

import java.util.Map;

/**
 * Scores how long a resistance level has been respected before the breakout. Weight: 15. A timeframe-aware sigmoid rewards older levels, with daily setups needing about 30 days, weekly setups about 1 year, and monthly setups about 6 years before the steep part of the curve.
 */
public class ResistanceLengthEvaluatorFormation extends AbstractFormationEvaluator<ResistanceBreakout> {

    private final double SIGMOID_SMOOTHNESS = 0.08;

    private final double RESISTANCE_LENGTH_WEIGHTAGE = 15.0;

    private final double PERCENTAGE_WEIGHTAGE_AT_MIN = 60.0;

    private final Map<TimeFrame, Sigmoid> TIMEFRAME_TO_RANGE = Map.of(
            TimeFrame.DAILY, getSigmoid(30),    // notes: 1M–1.5Y range; 30 days = 1M minimum
            TimeFrame.WEEKLY, getSigmoid(365),  // notes: 1Y–5Y range; 365 days = 1Y minimum
            TimeFrame.MONTHLY, getSigmoid(2190) // notes: 6Y+ range; 2190 days = 6Y minimum
    );

    public ResistanceLengthEvaluatorFormation(AbstractFormationEvaluator<ResistanceBreakout> nextEvaluator) {
        super(nextEvaluator);
    }

    /**
     * Uses a timeframe-aware sigmoid so older resistance levels earn progressively higher scores without creating hard cutoffs.
     */
    @Override
    public double evaluate(ResistanceBreakout formation) {
        Sigmoid sigmoid = TIMEFRAME_TO_RANGE.get(formation.getTimeFrame());
        return sigmoid.compute(formation.getResistanceLength());
    }

    @Override
    protected double getWeightage() {
        return RESISTANCE_LENGTH_WEIGHTAGE;
    }

    @Override
    public FormationType getFormationType() {
        return null;
    }

    private Sigmoid getSigmoid(double minDays) {
        return new Sigmoid(
                SIGMOID_SMOOTHNESS,
                RESISTANCE_LENGTH_WEIGHTAGE,
                SteepGrowthCenterCalculator.getByPercentageOfAsymptote(SIGMOID_SMOOTHNESS, PERCENTAGE_WEIGHTAGE_AT_MIN, minDays)
        );
    }
}
