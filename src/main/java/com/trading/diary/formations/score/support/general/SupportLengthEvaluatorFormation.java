package com.trading.diary.formations.score.support.general;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.support.SupportReversal;
import com.trading.diary.formations.score.AbstractFormationEvaluator;
import com.trading.diary.scale.Sigmoid;
import com.trading.diary.scale.SteepGrowthCenterCalculator;
import com.trading.diary.utils.TimeFrame;

import java.util.Map;

public class SupportLengthEvaluatorFormation extends AbstractFormationEvaluator<SupportReversal> {

    private final double SIGMOID_SMOOTHNESS = 0.08;

    private final double SUPPORT_LENGTH_WEIGHTAGE = 50.0;

    private final double PERCENTAGE_WEIGHTAGE_AT_MIN = 60.0;

    private final Map<TimeFrame, Sigmoid> TIMEFRAME_TO_RANGE = Map.of(
            TimeFrame.DAILY, getSigmoid(30),
            TimeFrame.WEEKLY, getSigmoid(1500),
            TimeFrame.MONTHLY, getSigmoid(19000)
    );

    public SupportLengthEvaluatorFormation(AbstractFormationEvaluator<SupportReversal> nextEvaluator) {
        super(nextEvaluator);
    }

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
