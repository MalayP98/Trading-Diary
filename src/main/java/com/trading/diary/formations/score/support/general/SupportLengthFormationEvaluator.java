package com.trading.diary.formations.score.support.general;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.support.SupportReversal;
import com.trading.diary.formations.score.AbstractFormationEvaluator;
import com.trading.diary.scale.Sigmoid;
import com.trading.diary.scale.SteepGrowthCenterCalculator;
import com.trading.diary.utils.emums.TimeFrame;

import java.util.Map;

/**
 * Scores how long support has been in place before the reversal. Weight: 50. A timeframe-aware sigmoid rewards older levels, with daily setups needing about 30 days and weekly or monthly setups needing about five years before the score ramps up sharply.
 */
public class SupportLengthFormationEvaluator extends AbstractFormationEvaluator<SupportReversal> {

    private final double SIGMOID_SMOOTHNESS = 0.08;

    private final double SUPPORT_LENGTH_WEIGHTAGE = 50.0;

    private final double PERCENTAGE_WEIGHTAGE_AT_MIN = 60.0;

    private final Map<TimeFrame, Sigmoid> TIMEFRAME_TO_RANGE = Map.of(
            TimeFrame.DAILY, getSigmoid(30),    // notes: 1M–1.5Y range; 30 days = 1M minimum
            TimeFrame.WEEKLY, getSigmoid(1825), // notes: 5Y–6Y range; 1825 days = 5Y minimum
            TimeFrame.MONTHLY, getSigmoid(1825) // notes: 5Y+ range; 1825 days = 5Y minimum
    );

    public SupportLengthFormationEvaluator(AbstractFormationEvaluator<SupportReversal> nextEvaluator) {
        super(nextEvaluator);
    }

    /**
     * Uses a timeframe-aware sigmoid so older support zones score higher while still avoiding a cliff-edge threshold at the minimum viable age.
     */
    @Override
    public double evaluate(SupportReversal formation) {
        TimeFrame timeFrame = formation.getTimeFrame();
        Sigmoid sigmoid = TIMEFRAME_TO_RANGE.get(timeFrame);
        return sigmoid.compute(formation.getSupportLength());
    }

    @Override
    protected double getWeightage() {
        return SUPPORT_LENGTH_WEIGHTAGE;
    }

    @Override
    public FormationType getFormationType() {
        return null;
    }

    private Sigmoid getSigmoid(double minDays) {
        return new Sigmoid(
                SIGMOID_SMOOTHNESS,
                SUPPORT_LENGTH_WEIGHTAGE,
                SteepGrowthCenterCalculator.getByPercentageOfAsymptote(SIGMOID_SMOOTHNESS, PERCENTAGE_WEIGHTAGE_AT_MIN, minDays)
        );
    }
}
